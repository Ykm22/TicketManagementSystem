package com.example.ticketmanagementsystem_android.models.dtos;

public class VenueDto {
    private String location;
    private String type;
    private int capacity;
    private double pricePerHour;
    private String id;

    // Add getters and setters for the above fields

    public VenueDto(String location, String type, int capacity, double pricePerHour, String id) {
        this.location = location;
        this.type = type;
        this.capacity = capacity;
        this.pricePerHour = pricePerHour;
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public double getPricePerHour() {
        return pricePerHour;
    }

    public void setPricePerHour(double pricePerHour) {
        this.pricePerHour = pricePerHour;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
