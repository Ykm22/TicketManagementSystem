package com.example.ticketmanagementsystem_android.models;

public class LoginResponse {
    private String message; // what is present in the body
    private String status; // success or failure

    // Add constructor and getters

    public LoginResponse(String message, String status) {
        this.message = message;
        this.status = status;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
