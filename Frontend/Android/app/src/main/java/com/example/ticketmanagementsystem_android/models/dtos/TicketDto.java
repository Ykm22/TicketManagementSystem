package com.example.ticketmanagementsystem_android.models.dtos;

public class TicketDto {
    private String description;
    private int numberOfTickets;

    public TicketDto() {
    }

    public TicketDto(String selectedTicketType, int selectedTicketQuantity) {
        this.description = selectedTicketType;
        this.numberOfTickets = selectedTicketQuantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNumberOfTickets() {
        return numberOfTickets;
    }

    public void setNumberOfTickets(int numberOfTickets) {
        this.numberOfTickets = numberOfTickets;
    }
}
