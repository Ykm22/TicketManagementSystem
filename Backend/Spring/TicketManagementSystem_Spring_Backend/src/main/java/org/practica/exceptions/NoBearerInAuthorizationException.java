package org.practica.exceptions;

public class NoBearerInAuthorizationException extends Throwable {
    private String message;

    public NoBearerInAuthorizationException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
