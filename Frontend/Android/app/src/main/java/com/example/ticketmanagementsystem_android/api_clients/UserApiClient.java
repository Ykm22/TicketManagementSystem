package com.example.ticketmanagementsystem_android.api_clients;

import com.example.ticketmanagementsystem_android.callbacks.LoginCallback;
import com.example.ticketmanagementsystem_android.callbacks.RegisterCallback;
import com.example.ticketmanagementsystem_android.models.LoginResponse;
import com.example.ticketmanagementsystem_android.models.RegisterResponse;
import com.example.ticketmanagementsystem_android.models.dtos.CustomMessageDto;
import com.example.ticketmanagementsystem_android.models.dtos.UserLoginDto;
import com.example.ticketmanagementsystem_android.models.dtos.UserRegistrationDto;
import com.example.ticketmanagementsystem_android.services.UserService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserApiClient {
    private static final String BASE_URL = "http://172.16.98.71:8080/tms/api/spring/";
//    private static final String BASE_URL = "http://192.168.1.104:8080/tms/api/spring/";

    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            Gson gson = new GsonBuilder().setLenient().create();
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }
    private static UserService userService;

    public static void loginUser(String email, String password, final LoginCallback callback) {
        userService = getClient().create(UserService.class);

        UserLoginDto userLoginDto = new UserLoginDto(email, password);

        Call<CustomMessageDto> call = userService.loginUser(userLoginDto);
        call.enqueue(new Callback<CustomMessageDto>() {
            @Override
            public void onResponse(Call<CustomMessageDto> call, Response<CustomMessageDto> response) {
                if (response.isSuccessful()) {
                    // Handle successful response here
                    callback.onLoginSuccess(new LoginResponse(response.body().getMessage(), "Success"));
                } else {
                    // Handle error response here
                    callback.onLoginFailure(new LoginResponse("Email or password incorrect", "Failure"));
                }
            }

            @Override
            public void onFailure(Call<CustomMessageDto> call, Throwable t) {
                // Handle failure here
                callback.onLoginFailure(new LoginResponse(t.toString(), "Failure"));
            }
        });
    }
    public static void registerUser(String email, String password, String sex, int age, final RegisterCallback callback) {
        userService = getClient().create(UserService.class);

        UserRegistrationDto userRegistrationDto = new UserRegistrationDto(email, password, sex, age);

        Call<CustomMessageDto> call = userService.registerUser(userRegistrationDto);
        call.enqueue(new Callback<CustomMessageDto>() {
            @Override
            public void onResponse(Call<CustomMessageDto> call, Response<CustomMessageDto> response) {
                if (response.isSuccessful()) {
                    // Handle successful response here
                    callback.onRegistrationSuccess(new RegisterResponse(response.body().getMessage()));
                } else {
                    // Handle error response here
                    callback.onRegistrationFailure(new RegisterResponse("Some failure mate"));
                }
            }

            @Override
            public void onFailure(Call<CustomMessageDto> call, Throwable t) {
                // Handle failure here
                callback.onRegistrationFailure(new RegisterResponse("Some failure mate"));
            }
        });
    }
}
