package com.example.ticketmanagementsystem_android.models.dtos;

import java.util.Date;

public class OrderDto {
    private String orderId;
    private int numberOfTickets;
    private double totalPrice;
    private String orderedAt;

    private TicketCategoryDto ticketCategoryDto; //ticketCategoryId, description, price
    private EventOrderDto eventDto; //eventId, type, location

    public OrderDto(String orderId, int numberOfTickets, double totalPrice, String orderedAt, TicketCategoryDto ticketCategoryDto, EventOrderDto eventDto) {
        this.orderId = orderId;
        this.numberOfTickets = numberOfTickets;
        this.totalPrice = totalPrice;
        this.orderedAt = orderedAt;
        this.ticketCategoryDto = ticketCategoryDto;
        this.eventDto = eventDto;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getNumberOfTickets() {
        return numberOfTickets;
    }

    public void setNumberOfTickets(int numberOfTickets) {
        this.numberOfTickets = numberOfTickets;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getOrderedAt() {
        return orderedAt;
    }

    public void setOrderedAt(String orderedAt) {
        this.orderedAt = orderedAt;
    }

    public TicketCategoryDto getTicketCategoryDto() {
        return ticketCategoryDto;
    }

    public void setTicketCategoryDto(TicketCategoryDto ticketCategoryDto) {
        this.ticketCategoryDto = ticketCategoryDto;
    }

    public EventOrderDto getEventDto() {
        return eventDto;
    }

    public void setEventDto(EventOrderDto eventDto) {
        this.eventDto = eventDto;
    }
}
