package org.practica.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.practica.exceptions.NoAuthorizationException;
import org.practica.exceptions.NoBearerInAuthorizationException;
import org.practica.exceptions.ResourceForbiddenException;
import org.practica.model.Event;
import org.practica.model.Order;
import org.practica.model.TicketCategory;
import org.practica.model.Venue;
import org.practica.model.dto.EventDto;
import org.practica.model.dto.GetAllResponse;
import org.practica.model.dto.TicketCategoryDto;
import org.practica.security.MyCustomSecurity;
import org.practica.security.UserRoles;
import org.practica.service.events_service.EventsService;
import org.practica.service.events_service.EventsServiceImpl;
import org.practica.utils.JwtUtil;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/tms/api/spring/events")
public class EventController {
    private final EventsService eventsService;

    public EventController(EventsServiceImpl eventsService){
        this.eventsService = eventsService;
    }
    @GetMapping(params = {"location", "eventType"})
    public ResponseEntity<?> getEventsByLocationAndEventType(
            HttpServletRequest request,
            @RequestParam(name = "location") String location,
            @RequestParam(name = "eventType") String eventType
    ) {
        try{
            MyCustomSecurity.validateRequest(request, UserRoles.CUSTOMER);
        } catch(NoAuthorizationException ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch(NoBearerInAuthorizationException ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch(ResourceForbiddenException ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
        }
        Iterable<Event> events = eventsService.getEventsByLocationAndType(location, eventType);
        if(!events.iterator().hasNext()){
            String message = "No events found for the specified venueId and eventType";
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
        Iterable<EventDto> eventsDto = getDto(events);
        return new ResponseEntity<>(eventsDto, HttpStatus.OK);
    }
    @GetMapping()
    @Transactional
    public ResponseEntity<?> getAllEvents(HttpServletRequest request){
        try{
            MyCustomSecurity.validateRequest(request, UserRoles.CUSTOMER);
            Iterable<Event> events = eventsService.getAllEvents();
            return new ResponseEntity<>(eventsService.getDto(events), HttpStatus.OK);
        } catch(NoAuthorizationException ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch(NoBearerInAuthorizationException ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch(ResourceForbiddenException ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
        } catch(Exception ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/paged")
    @Transactional
    public ResponseEntity<?> getAllEventsPaged(
            HttpServletRequest request,
            @RequestParam(name = "page") int page,
            @RequestParam(name = "size") int size
    ){
        try{
            MyCustomSecurity.validateRequest(request, UserRoles.CUSTOMER);
            Iterable<Event> events = eventsService.getAllEvents();
            //pages start at 0, not 1 => substracting 1
            Page<Event> page_events = eventsService.getAllEvents(page - 1, size);
            GetAllResponse response = createGetAllPagedResponse(page_events);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch(NoAuthorizationException ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch(NoBearerInAuthorizationException ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch(ResourceForbiddenException ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
        } catch(Exception ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    private GetAllResponse createGetAllPagedResponse(Page<Event> page_events) {
        Iterable<Event> events = page_events.getContent();
        Iterable<EventDto> eventsDto = eventsService.getDto(events);
        return new GetAllResponse(
                eventsDto,
                page_events.getTotalPages(),
                page_events.isFirst(),
                page_events.isLast()
        );
    }

    @GetMapping(params = "orderId")
    @Transactional
    public EventDto getEventByOrderId(
            HttpServletRequest request,
            @RequestParam(name = "orderId") String orderId
    ){
        Event event = eventsService.getEventByOrderId(UUID.fromString(orderId));
        return eventsService.getDto(event);
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<?> getEventById(@PathVariable String eventId){
        Event event = eventsService.getEventById(UUID.fromString(eventId));
        if(event == null){
            return new ResponseEntity<>("Event not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(getDto(event), HttpStatus.OK);
    }
    @GetMapping(params = {"venueId", "eventType"})
    public ResponseEntity<?> getEventsByVenueIdAndEventType(
        @RequestParam(name = "venueId") UUID venueId,
        @RequestParam(name = "eventType") String eventType
    ) {
        Iterable<Event> events = eventsService.getEventsByVenueIdAndEventType(venueId, eventType);
        if(!events.iterator().hasNext()){
            String message = "No events found for the specified venueId and eventType";
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
        Iterable<EventDto> eventsDto = getDto(events);
        return new ResponseEntity<>(eventsDto, HttpStatus.OK);
    }

    private List<EventDto> getDto(Iterable<Event> events){
        var eventsList = getListFromIterable(events);
        return eventsList.stream()
                .map(this::getDto)
                .toList();
    }
    private EventDto getDto(Event event){
        List<TicketCategoryDto> ticketCategories = getTicketCategoriesByEventId(event.getId());
        return new EventDto(
                event.getId(),
                event.getVenue(),
                event.getEventType().getName(),
                event.getDescription(),
                event.getName(),
                event.getStartDate(),
                event.getEndDate(),
                ticketCategories,
                getAvailableTickets(ticketCategories, event.getVenue())
        );
    }
    private int getAvailableTickets(List<TicketCategoryDto> ticketCategories, Venue venue) {
//        List<Order> orders = getOrdersByTicketCategories(ticketCategories);
        List<Order> orders = getOrdersByTicketCategories(ticketCategories);
        int boughtTickets = orders.stream()
                .mapToInt(Order::getNumberOfTickets)
                .sum();
        return venue.getCapacity() - boughtTickets;
    }
    private List<Order> getOrdersByTicketCategories(List<TicketCategoryDto> ticketCategories) {
        List<Order> orders = new ArrayList<>();
        for(TicketCategoryDto ticketCategory : ticketCategories){
            orders.addAll(getOrdersByTicketCategoryId(ticketCategory.getId()));
        }
        return orders;
    }
    private List<Order> getOrdersByTicketCategoryId(UUID ticketCategoryId) {
        RestTemplate restTemplate = new RestTemplate();
        String getOrdersUrl = "http://localhost:8080/tms/api/spring/orders/ticketCategoryId=" + ticketCategoryId;
        ResponseEntity<List<Order>> response = restTemplate.exchange(
                getOrdersUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Order>>() {}
        );
        if(response.getBody() == null){
            return new ArrayList<Order>();
        }
        return response.getBody();
    }

    private List<Event> getListFromIterable(Iterable<Event> events){
        List<Event> eventsList = new ArrayList<>();
        for (Event event : events){
            eventsList.add(event);
        }
        return eventsList;
    }
    private List<TicketCategoryDto> getTicketCategoriesByEventId(UUID eventId){
        RestTemplate restTemplate = new RestTemplate();
        String getTicketCategoriesUrl = "http://localhost:8080/tms/api/spring/ticketcategories?eventId=" + eventId;
        ResponseEntity<List<TicketCategory>> response = restTemplate.exchange(
                getTicketCategoriesUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<TicketCategory>>() {}
        );
        return getDto(Objects.requireNonNull(response.getBody()));
    }

    private List<TicketCategoryDto> getDto(List<TicketCategory> ticketCategories){
        return ticketCategories.stream()
                .map(ticketCategory -> new TicketCategoryDto(
                        ticketCategory.getId(),
                        ticketCategory.getDescription(),
                        ticketCategory.getPrice()
                ))
                .toList();
    }

}
