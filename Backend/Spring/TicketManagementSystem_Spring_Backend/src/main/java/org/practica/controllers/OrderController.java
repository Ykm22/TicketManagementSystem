package org.practica.controllers;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import jakarta.servlet.http.HttpServletRequest;
import org.hibernate.StaleObjectStateException;
import org.practica.exceptions.*;
import org.practica.model.Event;
import org.practica.model.Order;
import org.practica.model.TicketCategory;
import org.practica.model.User;
import org.practica.model.dto.*;
import org.practica.model.httpMethodsInput.OrderPOST;
import org.practica.security.MyCustomSecurity;
import org.practica.security.UserRoles;
import org.practica.service.orders_service.OrdersService;
import org.practica.service.orders_service.OrdersServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@CrossOrigin
@RestController
@RequestMapping("/tms/api/spring/orders")
public class OrderController {
    private final OrdersService ordersService;

    public OrderController(OrdersServiceImpl ordersService){
        this.ordersService = ordersService;
    }
    @GetMapping()
    public ResponseEntity<?> getOrdersByUserId(
            HttpServletRequest request,
            @RequestParam UUID userId
    ){
        try {
            MyCustomSecurity.validateRequest(request, UserRoles.CUSTOMER);
        } catch(NoAuthorizationException ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch(NoBearerInAuthorizationException ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch(ResourceForbiddenException ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
        }
        Iterable<Order> orders = ordersService.getOrdersByUserId(userId);
        if(!orders.iterator().hasNext()){
            String message = "No orders found for the specified userId";
            return new ResponseEntity<>(new CustomMessage(message), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(getFetchDto(orders), HttpStatus.OK);
    }

    private List<OrderFetchDto> getFetchDto(Iterable<Order> orders) {
        List<OrderFetchDto> ordersFetchDto = new ArrayList<>();
        for (Order order : orders){
            ordersFetchDto.add(getFetchDto(order));
        }
        return ordersFetchDto;
    }

    private OrderFetchDto getFetchDto(Order order) {
        TicketCategory ticketCategory = order.getTicketCategory();
        TicketCategoryOrderDto ticketCategoryOrderDto = new TicketCategoryOrderDto(
                ticketCategory.getDescription(),
                ticketCategory.getPrice().doubleValue(),
                ticketCategory.getId().toString()
        );
        Event event = ticketCategory.getEvent();
        EventOrderDto eventOrderDto = new EventOrderDto(
                event.getId().toString(),
                event.getEventType().getName(),
                event.getVenue().getLocation(),
                event.getStartDate(),
                event.getEndDate(),
                event.getName(),
                event.getVenue().getType()
        );
        List<TicketCategoryOrderDto> tcList = ticketCategory.getEvent().getTicketCategoryList().stream()
                .map(tc ->
                    new TicketCategoryOrderDto(
                            tc.getDescription(),
                            tc.getPrice().doubleValue(),
                            tc.getId().toString()
                    )
                ).toList();
        return new OrderFetchDto(
                order.getId().toString(),
                order.getNumberOfTickets(),
                order.getTotalPrice().doubleValue(),
                order.getOrderedAt(),
                ticketCategoryOrderDto,
                eventOrderDto,
                tcList
        );
    }

    @GetMapping(value = "/ticketCategoryId={ticketCategoryId}")
    public ResponseEntity<?> getOrdersByTicketCategoryId(@PathVariable UUID ticketCategoryId){
        List<Order> orders = ordersService.getOrdersByTicketCategoryId(ticketCategoryId);
        if(!orders.iterator().hasNext()){
            String message = "No orders found for the specified userId";
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(getDto(orders), HttpStatus.OK);
    }
    @PostMapping()
    public ResponseEntity<?> saveNewOrder(
            HttpServletRequest request,
            @RequestBody OrderPOST orderPOST,
            @RequestParam UUID customerId
    ){
        try {
            MyCustomSecurity.validateRequest(request, UserRoles.CUSTOMER);
        } catch(NoAuthorizationException ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch(NoBearerInAuthorizationException ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch(ResourceForbiddenException ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
        }
        int maxRetries = 10;
        int retryCount = 1;
        while(retryCount <= maxRetries){
            try {
                OrderDto2 order = ordersService.createOrder(orderPOST, customerId);
                return new ResponseEntity<>(order, HttpStatus.OK);
            } catch (InsufficientTicketsException ex){
                return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
            } catch (TicketCategoryNotFoundException ex){
                return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
            } catch(UserNotFoundException ex){
                return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
            } catch(ErrorCreatingOrderException ex){
                return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
            } catch (ObjectOptimisticLockingFailureException ex){
                System.out.printf("Retry %d / %d%n", retryCount, maxRetries);
                retryCount++;
            }
        }
        return new ResponseEntity<>("Maximum number of retries of transaction reached", HttpStatus.NOT_FOUND);
    }

    private Iterable<OrderDto> getDto(Iterable<Order> orders){
        List<OrderDto> ordersDto = new ArrayList<>();
        for (Order order : orders){
            ordersDto.add(getDto(order));
        }
        return ordersDto;
    }
    private OrderDto getDto(Order order){
        return new OrderDto(
                order.getId(),
                order.getTicketCategory().getEvent().getId(),
                order.getOrderedAt(),
                order.getTicketCategory().getId(),
                order.getNumberOfTickets(),
                order.getTotalPrice()
        );
    }
    private List<Order> getListFromIterable(Iterable<Order> orders){
        List<Order> ordersList = new ArrayList<>();
        for (Order order : orders){
            ordersList.add(order);
        }
        return ordersList;
    }
}