package org.practica.model.dto;

public class GetAllResponse {
    private Iterable<EventDto> events;
    private int totalPages;
    private boolean isFirst;
    private boolean isLast;

    public GetAllResponse(Iterable<EventDto> events, int totalPages, boolean isFirst, boolean isLast) {
        this.events = events;
        this.totalPages = totalPages;
        this.isFirst = isFirst;
        this.isLast = isLast;
    }

    public boolean isFirst() {
        return isFirst;
    }

    public void setFirst(boolean first) {
        isFirst = first;
    }

    public boolean isLast() {
        return isLast;
    }

    public void setLast(boolean last) {
        isLast = last;
    }

    public Iterable<EventDto> getEvents() {
        return events;
    }

    public void setEvents(Iterable<EventDto> events) {
        this.events = events;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
