package org.practica.model.dto;

import org.practica.model.TicketCategory;
import org.practica.model.User;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

public class OrderDto {
    private UUID orderId;
    private UUID eventId;
    private Date orderedAt;
    private UUID ticketCategoryId;
    private int numberOfTickets;
    private BigDecimal totalPrice;


    public OrderDto() {
    }

    public OrderDto(UUID orderId, UUID eventId, Date orderedAt, UUID ticketCategoryId, int numberOfTickets, BigDecimal totalPrice) {
        this.orderId = orderId;
        this.eventId = eventId;
        this.orderedAt = orderedAt;
        this.ticketCategoryId = ticketCategoryId;
        this.numberOfTickets = numberOfTickets;
        this.totalPrice = totalPrice;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }

    public UUID getEventId() {
        return eventId;
    }

    public void setEventId(UUID eventId) {
        this.eventId = eventId;
    }

    public Date getOrderedAt() {
        return orderedAt;
    }

    public void setOrderedAt(Date orderedAt) {
        this.orderedAt = orderedAt;
    }

    public UUID getTicketCategoryId() {
        return ticketCategoryId;
    }

    public void setTicketCategoryId(UUID ticketCategoryId) {
        this.ticketCategoryId = ticketCategoryId;
    }

    public int getNumberOfTickets() {
        return numberOfTickets;
    }

    public void setNumberOfTickets(int numberOfTickets) {
        this.numberOfTickets = numberOfTickets;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}
