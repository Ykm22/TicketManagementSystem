package com.example.ticketmanagementsystem_android.models.dtos;

public class EventUpdateDto {
    private String eventId;
    private int removedTickets;

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public int getRemovedTickets() {
        return removedTickets;
    }

    public void setRemovedTickets(int removedTickets) {
        this.removedTickets = removedTickets;
    }

    public EventUpdateDto(String eventId, int removedTickets) {
        this.eventId = eventId;
        this.removedTickets = removedTickets;
    }
}
