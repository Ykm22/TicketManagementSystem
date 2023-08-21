package org.practica.service.events_service;

import jakarta.transaction.Transactional;
import org.practica.model.Event;
import org.practica.model.Order;
import org.practica.model.TicketCategory;
import org.practica.model.Venue;
import org.practica.model.dto.EventDto;
import org.practica.model.dto.TicketCategoryDto;
import org.practica.repository.EventsRepository;
import org.practica.repository.OrdersRepository;
import org.practica.repository.mocks.MockEventsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
public class EventsServiceImpl implements EventsService{
    private final EventsRepository eventsRepository;
//    private MockEventsRepository eventsRepository;
    private final Logger logger;
    private final OrdersRepository ordersRepository;

    public EventsServiceImpl(
            EventsRepository eventsRepository,
//            MockEventsRepository eventsRepository
            OrdersRepository ordersRepository
    ){
        this.eventsRepository = eventsRepository;
        this.ordersRepository = ordersRepository;
        this.logger = LoggerFactory.getLogger(eventsRepository.getClass());
    }

    @Override
    public Iterable<Event> getAllEvents() {
        logger.info("EventsRepository - findAll()");
        Iterable<Event> events = eventsRepository.findAll();
        Stream<Event> eventStream = StreamSupport.stream(events.spliterator(), false)
                .sorted(Comparator.comparing(Event::getStartDate));
//        events = eventStream.limit(20).toList();
        long count = StreamSupport.stream(events.spliterator(), false).count();
        return events;
    }
    @Override
    public Page<Event> getAllEvents(int page, int size) {
        logger.info("EventsRepository - findAll(page, size)");
        Pageable pageable = PageRequest.of(page, size);
        Page<Event> events = eventsRepository.findAll(pageable);
        Stream<Event> eventStream = StreamSupport.stream(events.spliterator(), false)
                .sorted(Comparator.comparing(Event::getStartDate));
//        events = eventStream.limit(20).toList();
//        long count = StreamSupport.stream(events.spliterator(), false).count();
        return events;
    }

    @Override
    public Iterable<Event> getEventsByVenueIdAndEventType(UUID venueId, String eventType) {
        logger.info("EventsRepository - getEventsByVenueIdAndEventType(venueId, eventType)");
        return eventsRepository.findByVenueIdAndEventTypeName(venueId, eventType);
    }

    @Override
    public Iterable<Event> getEventsByLocationAndType(String location, String eventType) {
        logger.info("EventsRepository - getEventsByLocationAndType(location, eventType)");
        return eventsRepository.findByVenueLocationAndEventTypeName(location, eventType);
    }

    @Override
    public Event getEventById(UUID eventId) {
        logger.info("EventsRepository - getEventById(eventId");
        return eventsRepository.findById(eventId).orElse(null);
    }

    @Override
    public Iterable<EventDto> getDto(Iterable<Event> events) {
        List<EventDto> eventsDto = new ArrayList<>();
        for(Event event : events){
            eventsDto.add(getDto(event));
        }
        return eventsDto;
    }
    public EventDto getDto(Event event){
        List<TicketCategory> ticketCategories = event.getTicketCategoryList();
        return new EventDto(
                event.getId(),
                event.getVenue(),
                event.getEventType().getName(),
                event.getDescription(),
                event.getName(),
                event.getStartDate(),
                event.getEndDate(),
                getDto(ticketCategories),
                getAvailableTickets(ticketCategories, event.getVenue())
        );
    }

    @Override
    public Event getEventByOrderId(UUID orderId) {
        logger.info("EventsRepository - getEventByOrderId(orderId)");
        Order order = ordersRepository.findById(orderId).orElse(null);
        Iterable<Event> events = eventsRepository.findAll();
        for (Event event : events){
            for(TicketCategory ticketCategory : event.getTicketCategoryList()){
                if(order.getTicketCategory().getId().equals(ticketCategory.getId())){
                    return event;
                }
            }
        }

        return null;
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
    @Transactional
    public int getAvailableTickets(List<TicketCategory> ticketCategories, Venue venue) {
        System.out.println("OrdersRepository - findAllByTicketCategories(ticketCategories[])");
        System.out.printf("For event with id: %s\n", ticketCategories.get(0).getEvent().getId());
        List<Order> orders = ordersRepository.findAllByTicketCategories(ticketCategories);
        int boughtTickets = orders.stream()
                .mapToInt(Order::getNumberOfTickets)
                .sum();
        return venue.getCapacity() - boughtTickets;
    }

}
