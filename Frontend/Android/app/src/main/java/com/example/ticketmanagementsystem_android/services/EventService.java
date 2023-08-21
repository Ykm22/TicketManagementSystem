package com.example.ticketmanagementsystem_android.services;

import com.example.ticketmanagementsystem_android.models.dtos.EventDto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface EventService {
    @GET("events")
    Call<List<EventDto>> getEvents();

    @GET("events")
    Call<List<EventDto>> getEventsByLocationAndType(
            @Query("location") String location,
            @Query("eventType") String type
    );

    @GET("events")
    Call<EventDto> getEventByOrderId(@Query("orderId") String orderId);
}
