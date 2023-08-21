package com.example.ticketmanagementsystem_android.models.dtos;

import java.util.List;

public class EventDto {
    private String eventId;
    private VenueDto venue;
    private String type;
    private String description;
    private String name;
    private String startDate;
    private String endDate;
    private List<TicketCategoryDto> ticketCategories;
    private int availableTickets;

    public EventDto(String eventId, VenueDto venue, String type, String description, String name, String startDate, String endDate, List<TicketCategoryDto> ticketCategories, int availableTickets) {
        this.eventId = eventId;
        this.venue = venue;
        this.type = type;
        this.description = description;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.ticketCategories = ticketCategories;
        this.availableTickets = availableTickets;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public VenueDto getVenue() {
        return venue;
    }

    public void setVenue(VenueDto venue) {
        this.venue = venue;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public List<TicketCategoryDto> getTicketCategories() {
        return ticketCategories;
    }

    public void setTicketCategories(List<TicketCategoryDto> ticketCategories) {
        this.ticketCategories = ticketCategories;
    }

    public int getAvailableTickets() {
        return availableTickets;
    }

    public void setAvailableTickets(int availableTickets) {
        this.availableTickets = availableTickets;
    }
}
