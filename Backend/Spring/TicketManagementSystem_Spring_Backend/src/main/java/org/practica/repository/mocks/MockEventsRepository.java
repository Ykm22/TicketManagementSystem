package org.practica.repository.mocks;

import org.practica.model.Event;
import org.practica.model.EventType;
import org.practica.model.Venue;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;

@Repository
public class MockEventsRepository {
    private ArrayList<Event> events;

    public MockEventsRepository() {
        AddMockEvents();
    }

    private void AddMockEvents() {
        Event e1 = GetEvent1();
        Event e2 = GetEvent2();
        events = new ArrayList<>(Arrays.asList(e1, e2));
    }
    public static Event GetEvent1(){
        EventType et1 = new EventType();
        et1.setId(UUID.fromString("1809AB63-EB9D-4F98-9FE4-74DF53D779D8"));
        et1.setName("Play");

        Venue v1 = new Venue();
        v1.setId(UUID.fromString("F7FFA9E4-E463-4E25-91D0-3F59DEF1FCDE"));
        v1.setLocation("Paris");
        v1.setType("Theater");
        v1.setCapacity(1573);
        v1.setPricePerHour(BigDecimal.valueOf(50.99));

        Event e1 = new Event();
        e1.setId(UUID.fromString("56EEF823-B277-4011-9C7E-91F133CC0695"));
        e1.setDescription("Play");
        e1.setName("Play");
        e1.setEventType(et1);
        e1.setVenue(v1);
        e1.setStartDate(Date.from(LocalDateTime.now().toInstant(ZoneOffset.UTC)));
        e1.setEndDate(Date.from(LocalDateTime.now().toInstant(ZoneOffset.UTC)));

        return e1;
    }
    public static Event GetEvent2(){
        EventType et2 = new EventType();
        et2.setId(UUID.fromString("868FEFF2-C88D-4A52-807D-9F170E912A98"));
        et2.setName("Presentation");

        Venue v2 = new Venue();
        v2.setId(UUID.fromString("4FE5065E-AA8A-4CCE-A7E2-2191AFD90657"));
        v2.setLocation("Liverpool");
        v2.setType("Conference Center");
        v2.setCapacity(1254);
        v2.setPricePerHour(BigDecimal.valueOf(28.00));

        Event e2 = new Event();
        e2.setId(UUID.fromString("07CA176-89FA-4F40-BE2C-29DE9E9A94E4"));
        e2.setDescription("Presentation");
        e2.setName("Presentation");
        e2.setEventType(et2);
        e2.setVenue(v2);
        e2.setStartDate(Date.from(LocalDateTime.now().toInstant(ZoneOffset.UTC)));
        e2.setEndDate(Date.from(LocalDateTime.now().toInstant(ZoneOffset.UTC)));

        return e2;
    }

    public Iterable<Event> findAll() {
//        System.out.println("EventsRepository - findAll()");
//        events.forEach(System.out::println);
//        System.out.println();
        return events;
    }

    public Iterable<Event> findByVenueIdAndEventTypeName(UUID venueId, String eventType) {

        Iterable<Event> filteredEvents =  events.stream()
                .filter(e -> e.getVenue().getId().equals(venueId) && e.getEventType().getName().equals(eventType))
                .toList();
//        System.out.println("EventsRepository - findByVenueIdAndEventTypeName(venueId, eventType)");
//        System.out.println("Filtered events by venueId: " + venueId + " and eventType:" + eventType);
//        filteredEvents.forEach(System.out::println);
//        System.out.println();
        return filteredEvents;
    }

    public Iterable<Event> findByVenueLocationAndEventTypeName(String location, String eventType) {
        Iterable<Event> filteredEvents =  events.stream()
                .filter(e -> e.getVenue().getLocation().equals(location) && e.getEventType().getName().equals(eventType))
                .toList();
        return filteredEvents;
    }
}
