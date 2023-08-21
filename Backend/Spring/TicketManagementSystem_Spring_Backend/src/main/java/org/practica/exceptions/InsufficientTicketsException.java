package org.practica.exceptions;

public class InsufficientTicketsException extends Throwable {
    private String message;

    public InsufficientTicketsException(String s) {
        this.message = s;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
