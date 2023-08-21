package org.practica.model.dto;

import org.practica.model.TicketCategory;
import org.practica.model.Venue;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class EventDto {
    private UUID eventId;
    private Venue venue;
    private String type;
    private String description;
    private String name;
    private Date startDate;
    private Date endDate;
    private List<TicketCategoryDto> ticketCategories;
    private int availableTickets;

    public EventDto() {
    }

    public EventDto(UUID eventId, Venue venue, String type, String description, String name, Date startDate, Date endDate, List<TicketCategoryDto> ticketCategories, int availableTickets) {
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

    public int getAvailableTickets() {
        return availableTickets;
    }

    public void setAvailableTickets(int availableTickets) {
        this.availableTickets = availableTickets;
    }

    public UUID getEventId() {
        return eventId;
    }

    public void setEventId(UUID eventId) {
        this.eventId = eventId;
    }

    public Venue getVenue() {
        return venue;
    }

    public void setVenue(Venue venue) {
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public List<TicketCategoryDto> getTicketCategories() {
        return ticketCategories;
    }

    public void setTicketCategories(List<TicketCategoryDto> ticketCategories) {
        this.ticketCategories = ticketCategories;
    }
}