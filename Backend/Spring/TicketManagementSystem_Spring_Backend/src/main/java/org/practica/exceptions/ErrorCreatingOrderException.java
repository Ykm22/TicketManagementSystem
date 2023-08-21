package org.practica.exceptions;

public class ErrorCreatingOrderException extends Exception {
    private String message;

    public ErrorCreatingOrderException(String s) {
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
