package org.practica.model;

import java.util.UUID;

public class AuthenticationResponse {
    public boolean isCustomer() {
        return isCustomer;
    }

    public void setCustomer(boolean customer) {
        isCustomer = customer;
    }

    private boolean isCustomer;
    private String jwt;
    private UUID userId;

    public AuthenticationResponse() {
    }

    public AuthenticationResponse(String jwt, UUID userId, boolean isCustomer) {
        this.jwt = jwt;
        this.userId = userId;
        this.isCustomer = isCustomer;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getJwt() {
        return jwt;
    }
}
