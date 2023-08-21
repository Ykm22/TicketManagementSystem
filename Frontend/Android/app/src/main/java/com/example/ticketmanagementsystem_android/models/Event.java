package com.example.ticketmanagementsystem_android.models;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Event {
    private UUID eventId;
    private String name;
    private String startDate;
    private String endDate;
    private String availableTickets;
    private String imagePath;
    private List<TicketCategory> ticketCategories;

    public Event(String name, String startDate, String endDate, String availableTickets, String imagePath) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.availableTickets = availableTickets;
        this.imagePath = imagePath;
        eventId = UUID.randomUUID();
    }

    public List<TicketCategory> getTicketCategories() {
        return ticketCategories;
    }

    public void setTicketCategories(List<TicketCategory> ticketCategories) {
        this.ticketCategories = ticketCategories;
    }

    public UUID getId() {
        return eventId;
    }

    public void setId(UUID eventId) {
        this.eventId = eventId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getAvailableTickets() {
        return availableTickets;
    }

    public void setAvailableTickets(String availableTickets) {
        this.availableTickets = availableTickets;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
