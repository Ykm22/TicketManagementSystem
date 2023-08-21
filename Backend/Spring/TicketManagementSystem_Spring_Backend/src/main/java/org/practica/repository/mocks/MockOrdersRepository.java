package org.practica.repository.mocks;

import org.practica.model.Order;
import org.practica.model.TicketCategory;
import org.practica.model.User;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;

@Repository
public class MockOrdersRepository {
    private ArrayList<Order> orders;

    public MockOrdersRepository() {
        AddMockOrders();
    }

    private void AddMockOrders() {
        Order o1 = GetOrder1();
        Order o2 = GetOrder2();
        orders = new ArrayList<>(Arrays.asList(o1, o2));
    }

    private Order GetOrder1() {
        User u1 = MockUsersRepository.GetUser1();

        TicketCategory tc1 = MockTicketCategoriesRepository.GetTicketCategory1();

        Order o1 = new Order();
        o1.setId(UUID.fromString("E5D3C736-2CF1-43F6-B9B9-57281B9A01E3"));
        o1.setOrderedAt(Date.from(LocalDateTime.now().toInstant(ZoneOffset.UTC)));
        o1.setNumberOfTickets(5);
        o1.setTotalPrice(BigDecimal.valueOf(1234));
        o1.setUser(u1);
        o1.setTicketCategory(tc1);
        return o1;
    }
    private Order GetOrder2() {
        User u2 = MockUsersRepository.GetUser2();

        TicketCategory tc2 = MockTicketCategoriesRepository.GetTicketCategory2();

        Order o2 = new Order();
        o2.setId(UUID.fromString("30DF7581-A992-46AD-A5CD-582AE89325BB"));
        o2.setOrderedAt(Date.from(LocalDateTime.now().toInstant(ZoneOffset.UTC)));
        o2.setNumberOfTickets(5);
        o2.setTotalPrice(BigDecimal.valueOf(1234));
        o2.setUser(u2);
        o2.setTicketCategory(tc2);
        return o2;
    }

    public Optional<List<Order>> findAllByUserId(UUID userId) {
//        System.out.println("OrdersRepository - findAllByUserId(userId)");
        List<Order> filteredOrders = orders.stream()
                .filter(o -> o.getUser().getId().equals(userId))
                .toList();
//        System.out.println("For userId: " + userId);
//        filteredOrders.forEach(System.out::println);
//        System.out.println();
        return Optional.of(filteredOrders);
    }

    public Optional<Order> findById(UUID orderId) {
        return orders.stream()
                .filter(o -> o.getId().equals(orderId))
                .findFirst();
    }

    public Order save(Order order) {
//        System.out.println("OrdersRepository - save(order)");
        orders.add(order);
//        System.out.println("Saved: " + order);
//        System.out.println();
        return order;
    }

    public List<Order> findAllByTicketCategoryId(UUID ticketCategoryId) {
//        System.out.println("OrdersRepository - findAllByTicketCategoryId(ticketCategoryId");
        List<Order> filteredOrders = orders.stream()
                .filter(o -> o.getTicketCategory().getId().equals(ticketCategoryId))
                .toList();
//        System.out.println("For ticketCategoryId: " + ticketCategoryId);
//        filteredOrders.forEach(System.out::println);
//        System.out.println();
        return filteredOrders;
    }
}
