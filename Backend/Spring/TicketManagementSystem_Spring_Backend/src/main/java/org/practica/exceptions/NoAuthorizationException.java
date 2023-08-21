package org.practica.exceptions;

public class NoAuthorizationException extends Exception {
    private String message;

    public NoAuthorizationException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
