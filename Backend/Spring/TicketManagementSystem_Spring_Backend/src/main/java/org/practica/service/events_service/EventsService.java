package org.practica.service.events_service;

import org.practica.model.Event;
import org.practica.model.dto.EventDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.UUID;
@Service
public interface EventsService {
    Iterable<Event> getAllEvents();
    Page<Event> getAllEvents(int page, int size);

    Iterable<Event> getEventsByVenueIdAndEventType(UUID venueId, String eventType);

    Iterable<Event> getEventsByLocationAndType(String location, String eventType);

    Event getEventById(UUID eventId);

    Iterable<EventDto> getDto(Iterable<Event> events);
    EventDto getDto(Event event);

    Event getEventByOrderId(UUID orderId);
}
