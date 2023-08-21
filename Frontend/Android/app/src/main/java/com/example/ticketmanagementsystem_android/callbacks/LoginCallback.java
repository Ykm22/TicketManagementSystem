package com.example.ticketmanagementsystem_android.callbacks;

import com.example.ticketmanagementsystem_android.models.LoginResponse;
import com.example.ticketmanagementsystem_android.models.dtos.UserLoginDto;

public interface LoginCallback {
    void onLoginSuccess(LoginResponse loginResponse);
    void onLoginFailure(LoginResponse loginResponse);
}