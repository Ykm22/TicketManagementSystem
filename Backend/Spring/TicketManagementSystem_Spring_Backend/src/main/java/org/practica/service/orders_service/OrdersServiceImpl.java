package org.practica.service.orders_service;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.OptimisticLockException;
import org.hibernate.StaleObjectStateException;
import org.practica.exceptions.ErrorCreatingOrderException;
import org.practica.exceptions.InsufficientTicketsException;
import org.practica.exceptions.TicketCategoryNotFoundException;
import org.practica.exceptions.UserNotFoundException;
import org.practica.model.*;
import org.practica.model.dto.EventDto;
import org.practica.model.dto.OrderDto;
import org.practica.model.dto.OrderDto2;
import org.practica.model.dto.TicketCategoryDto;
import org.practica.model.httpMethodsInput.OrderPOST;
import org.practica.repository.EventsRepository;
import org.practica.repository.OrdersRepository;
import org.practica.repository.TicketCategoriesRepository;
import org.practica.repository.UsersRepository;
import org.practica.repository.mocks.MockOrdersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrdersServiceImpl implements OrdersService {
    private final OrdersRepository ordersRepository;
    private final TicketCategoriesRepository ticketCategoriesRepository;
    private final UsersRepository usersRepository;
    //private MockOrdersRepository ordersRepository;
    private final Logger logger;

    public OrdersServiceImpl(
            OrdersRepository ordersRepository,
//            MockOrdersRepository ordersRepository
            TicketCategoriesRepository ticketCategoriesRepository,
            UsersRepository usersRepository
    ) {
        this.ordersRepository = ordersRepository;
        this.ticketCategoriesRepository = ticketCategoriesRepository;
        this.usersRepository = usersRepository;
        this.logger = LoggerFactory.getLogger(ordersRepository.getClass());
    }
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public OrderDto2 createOrder(OrderPOST orderPOST, UUID customerId) throws
            ObjectOptimisticLockingFailureException,
            ErrorCreatingOrderException,
            InsufficientTicketsException, TicketCategoryNotFoundException, UserNotFoundException {
        try {
            TicketCategory ticketCategory = ticketCategoriesRepository.findById(orderPOST.getTicketCategoryId()).orElse(null);
            User user = usersRepository.findById(customerId).orElse(null);
            if (ticketCategory == null) {
                throw new TicketCategoryNotFoundException("Ticket category not found.");
            }
            if (user == null){
                throw new UserNotFoundException("User not found");
            }
            Order order = getFromDto(orderPOST, ticketCategory, user);
            System.out.println("createOrder() - fetching available tickets");
            int availableTickets = getAvailableTickets(order);
            System.out.println("createOrder() - finished fetching available tickets");
            if (availableTickets >= order.getNumberOfTickets()) {
                System.out.println("createOrder() - had available tickets: " + availableTickets);
//                Thread.sleep(5000);
                ordersRepository.save(order);
                System.out.println("createOrder() - after save: " + (availableTickets - order.getNumberOfTickets()));
                return getDto(order);
            } else {
                throw new InsufficientTicketsException("Insufficient tickets available for this event");
            }
        } catch (OptimisticLockException ex) {
            throw ex;
        }
//        catch(InterruptedException ex){
//            ;// thread.sleep exception
//        }
        catch(Exception ex){
            //cant do catch StaleObjectStateException
            System.out.println("StaleObjectStateException: " + ex.getMessage());
            throw ex;
        }
//        throw new ErrorCreatingOrderException("Error creating order");
    }

    private OrderDto2 getDto(Order order) {
        OrderDto2 orderDto = new OrderDto2();
        orderDto.setOrderId(order.getId());
        orderDto.setEventId(order.getTicketCategory().getEvent().getId());
        orderDto.setOrderedAt(order.getOrderedAt());
        orderDto.setTicketCategoryId(order.getTicketCategory().getId());
        orderDto.setNumberOfTickets(order.getNumberOfTickets());
        orderDto.setTotalPrice(order.getTotalPrice());
        orderDto.setDescription(order.getTicketCategory().getDescription());
        return orderDto;
    }
//    private EventDto getEventDto(UUID eventId){
//        RestTemplate restTemplate = new RestTemplate();
//        String getEventUrl = "http://localhost:8080/tms/api/spring/events/" + eventId;
//        return restTemplate
//                .getForEntity(getEventUrl, EventDto.class)
//                .getBody();
//    }

    private Order getFromDto(OrderPOST orderPOST, TicketCategory ticketCategory, User user) {
        Order order = new Order();
        order.setOrderedAt(Calendar.getInstance().getTime());
        order.setUser(user);
        order.setId(UUID.randomUUID());
        order.setTicketCategory(ticketCategory);
        order.setNumberOfTickets(orderPOST.getNumberOfTickets());
        order.setTotalPrice(
                ticketCategory.getPrice().multiply(BigDecimal.valueOf(order.getNumberOfTickets()))
        );
        return order;
    }

    private int getAvailableTickets(Order order) {
        Event event = order.getTicketCategory().getEvent();
        List<TicketCategory> ticketCategories = event.getTicketCategoryList();
        System.out.println("getAvailableTickets() - Fetching available tickets..");
        int availableTickets = getAvailableTickets(ticketCategories, event.getVenue());
        System.out.println("getAvailableTickets() - Got: " + availableTickets);
        return Math.max(0, availableTickets);
    }
    private int getAvailableTickets(List<TicketCategory> ticketCategories, Venue venue) {
        List<Order> orders = ordersRepository.findAllByTicketCategories(ticketCategories);
        int boughtTickets = orders.stream()
                .mapToInt(Order::getNumberOfTickets)
                .sum();
        return venue.getCapacity() - boughtTickets;
    }
    @Override
    public Order saveOrder(Order order) {
        logger.info("OrdersRepository - save(order)");
        return ordersRepository.save(order);
    }

    @Override
    public Order getOrderById(UUID orderId) {
        logger.info("OrdersRepository - findById(orderId)");
        return ordersRepository.findById(orderId).orElse(null);
    }

    @Override
    public Iterable<Order> getOrdersByUserId(UUID userId) {
        logger.info("OrdersRepository - findAllByUserId(userId)");
        return ordersRepository.findAllByUserId(userId).orElse(null);
    }
    @Override
    public List<Order> getOrdersByTicketCategoryId(UUID ticketCategoryId) {
        System.out.println(ticketCategoryId);
        logger.info("OrdersRepository - findAllByTicketCategoryId(ticketCategoryId)");
//        Pageable pageable = PageRequest.of(0, 10).toOptional().get();
//        return ordersRepository.findTop10ByTicketCategoryId(ticketCategoryId, pageable);
        return ordersRepository.findAllByTicketCategoryId(ticketCategoryId);
    }
}
