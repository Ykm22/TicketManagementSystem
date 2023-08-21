package com.example.ticketmanagementsystem_android.api_clients;

import com.example.ticketmanagementsystem_android.callbacks.OrderCallback;
import com.example.ticketmanagementsystem_android.models.dtos.EventDto;
import com.example.ticketmanagementsystem_android.models.dtos.TicketCategoryDto;
import com.example.ticketmanagementsystem_android.services.EventService;
import com.example.ticketmanagementsystem_android.services.TicketCategoryService;

import java.util.List;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TicketCategoryApiClient {
    private static final String BASE_URL = "http://172.16.98.71:8080/tms/api/spring/";
//    private static final String BASE_URL = "http://192.168.1.104:8080/tms/api/spring/";
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
//            Gson gson = new GsonBuilder().setLenient().create();
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static void fetchByEventAndType(UUID eventId, String selectedTicketType, final OrderCallback callback) {
        TicketCategoryService ticketCategoryService = getClient().create(TicketCategoryService.class);
        Call<TicketCategoryDto> call = ticketCategoryService.getTicketCategoryByEventAndType(eventId, selectedTicketType);

        call.enqueue(new Callback<TicketCategoryDto>() {
            @Override
            public void onResponse(Call<TicketCategoryDto> call, Response<TicketCategoryDto> response) {
                if (response.isSuccessful()) {
                    TicketCategoryDto ticketCategoryDto = response.body();
                    callback.onTicketCategoryLoaded(ticketCategoryDto);
                } else {
                    callback.onTicketCategoryLoadFailed("Failed to load events");
                }
            }

            @Override
            public void onFailure(Call<TicketCategoryDto> call, Throwable t) {
                callback.onTicketCategoryLoadFailed(t.toString());
            }
        });
    }
}
