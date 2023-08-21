package com.example.ticketmanagementsystem_android.callbacks;

import com.example.ticketmanagementsystem_android.models.RegisterResponse;

public interface RegisterCallback{
    void onRegistrationSuccess(RegisterResponse registerResponse);
    void onRegistrationFailure(RegisterResponse registerResponse);
}
