package com.example.ticketmanagementsystem_android.services;

import com.example.ticketmanagementsystem_android.models.dtos.TicketCategoryDto;

import java.util.UUID;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TicketCategoryService {
    @GET("ticketcategories")
    Call<TicketCategoryDto> getTicketCategoryByEventAndType(
            @Query("eventId") UUID eventId,
            @Query("description") String description);
}
