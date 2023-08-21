package com.example.ticketmanagementsystem_android.models;

import java.math.BigDecimal;
import java.util.UUID;

public class TicketCategory {
    private String description;
    private double price;
    private UUID id;

    public TicketCategory(String description, double price, UUID id) {
        this.description = description;
        this.price = price;
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
