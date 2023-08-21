package org.practica.model.dto;

import jakarta.persistence.*;
import org.practica.model.Event;

import java.math.BigDecimal;
import java.util.UUID;

public class TicketCategoryDto {
    private UUID ticketCategoryId;

    private String description;

    private BigDecimal price;

    public TicketCategoryDto() {
    }

    public TicketCategoryDto(UUID ticketCategoryId, String description, BigDecimal price) {
        this.ticketCategoryId = ticketCategoryId;
        this.description = description;
        this.price = price;
    }

    public UUID getId() {
        return ticketCategoryId;
    }

    public void setId(UUID ticketCategoryId) {
        this.ticketCategoryId = ticketCategoryId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
