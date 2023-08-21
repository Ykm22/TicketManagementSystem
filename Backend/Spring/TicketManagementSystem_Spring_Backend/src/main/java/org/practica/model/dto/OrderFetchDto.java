package org.practica.model.dto;

import org.practica.model.TicketCategory;

import java.util.Date;
import java.util.List;

public class OrderFetchDto {
    private String orderId;
    private int numberOfTickets;
    private double totalPrice;
    private Date orderedAt;

    private TicketCategoryOrderDto ticketCategoryDto; //ticketCategoryId, description, price
    private EventOrderDto eventDto; //eventId, type, location, start/end dates
    private List<TicketCategoryOrderDto> ticketCategoryList;

    public OrderFetchDto(String orderId, int numberOfTickets, double totalPrice, Date orderedAt, TicketCategoryOrderDto ticketCategoryDto, EventOrderDto eventDto, List<TicketCategoryOrderDto> ticketCategoryList) {
        this.orderId = orderId;
        this.numberOfTickets = numberOfTickets;
        this.totalPrice = totalPrice;
        this.orderedAt = orderedAt;
        this.ticketCategoryDto = ticketCategoryDto;
        this.eventDto = eventDto;
        this.ticketCategoryList = ticketCategoryList;
    }

    public List<TicketCategoryOrderDto> getTicketCategoryList() {
        return ticketCategoryList;
    }

    public void setTicketCategoryList(List<TicketCategoryOrderDto> ticketCategoryList) {
        this.ticketCategoryList = ticketCategoryList;
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

    public Date getOrderedAt() {
        return orderedAt;
    }

    public void setOrderedAt(Date orderedAt) {
        this.orderedAt = orderedAt;
    }

    public TicketCategoryOrderDto getTicketCategoryDto() {
        return ticketCategoryDto;
    }

    public void setTicketCategoryDto(TicketCategoryOrderDto ticketCategoryDto) {
        this.ticketCategoryDto = ticketCategoryDto;
    }

    public EventOrderDto getEventDto() {
        return eventDto;
    }

    public void setEventDto(EventOrderDto eventDto) {
        this.eventDto = eventDto;
    }
}
