package com.example.ticketmanagementsystem_android.models.dtos;

import java.util.Date;

public class EventOrderDto {
    private String eventId;
    private String type;
    private String location;
    private String startDate;
    private String endDate;
    private String name;
    private String venueType;

    public EventOrderDto(String eventId, String type, String location, String startDate, String endDate, String name, String venueType) {
        this.eventId = eventId;
        this.type = type;
        this.location = location;
        this.startDate = startDate;
        this.endDate = endDate;
        this.name = name;
        this.venueType = venueType;
    }

    public String getVenueType() {
        return venueType;
    }

    public void setVenueType(String venueType) {
        this.venueType = venueType;
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

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
