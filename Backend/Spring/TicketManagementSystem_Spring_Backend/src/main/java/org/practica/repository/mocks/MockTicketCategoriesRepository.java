package org.practica.repository.mocks;

import org.practica.model.TicketCategory;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class MockTicketCategoriesRepository {
    private List<TicketCategory> ticketCategories;

    public MockTicketCategoriesRepository() {
        AddMockTicketCategories();
    }

    private void AddMockTicketCategories() {
        TicketCategory tc1 = GetTicketCategory1();
        TicketCategory tc2 = GetTicketCategory2();
        ticketCategories = Arrays.asList(tc1, tc2);
    }
    public static TicketCategory GetTicketCategory2(){
        TicketCategory tc2 = new TicketCategory();
        tc2.setId(UUID.fromString("D82BFCE9-2681-4B4A-83BB-09026393F811"));
        tc2.setEvent(MockEventsRepository.GetEvent2());
        tc2.setDescription("Standard");
        tc2.setPrice(BigDecimal.valueOf(30.25));
        return tc2;
    }

    public static TicketCategory GetTicketCategory1(){
        TicketCategory tc1 = new TicketCategory();
        tc1.setId(UUID.fromString("AA7D3D39-9D3E-4C44-96AC-25284729C86B"));
        tc1.setEvent(MockEventsRepository.GetEvent1());
        tc1.setDescription("VIP");
        tc1.setPrice(BigDecimal.valueOf(123.99));
        return tc1;
    }

    public Optional<TicketCategory> findById(UUID ticketCategoryId) {
//        System.out.println("TicketCategoriesRepository - findById(ticketCategoryId)");

        Optional<TicketCategory> ticketCategory = ticketCategories.stream()
                .filter(tc -> tc.getId().equals(ticketCategoryId))
                .findFirst();
//        System.out.println("For ticketCategoryId:" + ticketCategoryId + " found: " + ticketCategory.get());
//        System.out.println();
        return ticketCategory;
    }

    public Iterable<TicketCategory> findAllByEventId(UUID eventId) {
//        System.out.println("TicketCategoriesRepository - findAllByEventId(eventId)");
        Iterable<TicketCategory> filteredTicketCategories = ticketCategories.stream()
                .filter(tc -> tc.getEvent().getId().equals(eventId))
                .toList();
//        System.out.println("For eventId:" + eventId);
//        filteredTicketCategories.forEach(System.out::println);
//        System.out.println();
        return filteredTicketCategories;
    }
}
