package com.example.ticketmanagementsystem_android.services;

import com.example.ticketmanagementsystem_android.models.LoginResponse;
import com.example.ticketmanagementsystem_android.models.dtos.CustomMessageDto;
import com.example.ticketmanagementsystem_android.models.dtos.UserLoginDto;
import com.example.ticketmanagementsystem_android.models.dtos.UserRegistrationDto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserService {
    @POST("users/login")
    Call<CustomMessageDto> loginUser(@Body UserLoginDto userLoginDto);

    @POST("users/register")
    Call<CustomMessageDto> registerUser(@Body UserRegistrationDto userRegistrationDto);
}
