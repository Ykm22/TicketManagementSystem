package org.practica.model.dto;

public class TicketCategoryOrderDto {
    private String description;
    private double price;
    private String id;

    public TicketCategoryOrderDto(String description, double price, String id) {
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
