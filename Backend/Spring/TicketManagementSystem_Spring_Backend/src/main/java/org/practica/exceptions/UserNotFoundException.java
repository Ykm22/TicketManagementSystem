package org.practica.exceptions;

public class UserNotFoundException extends Exception {
    private String message;
    public UserNotFoundException(String message) {
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
