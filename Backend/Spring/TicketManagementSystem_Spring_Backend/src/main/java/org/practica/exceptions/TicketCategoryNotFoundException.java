package org.practica.exceptions;

public class TicketCategoryNotFoundException extends Throwable {
    private String message;
    public TicketCategoryNotFoundException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
