package com.example.ticketmanagementsystem_android.api_clients;

import android.content.Context;
import android.util.Log;

import com.example.ticketmanagementsystem_android.callbacks.EventCallback;
import com.example.ticketmanagementsystem_android.callbacks.OrderFetchCallback;
import com.example.ticketmanagementsystem_android.models.dtos.EventDto;
import com.example.ticketmanagementsystem_android.services.EventService;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EventApiClient {
//    private static final String BASE_URL = "http://192.168.1.104:8080/tms/api/spring/";
    private static final String BASE_URL = "http://172.16.98.71:8080/tms/api/spring/";

    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder()
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS);
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClientBuilder.build())
                    .build();
        }
        return retrofit;
    }
    public static void fetchEvents(final EventCallback callback, Context context) {
        EventService eventService = getClient().create(EventService.class);
        Call<List<EventDto>> call = eventService.getEvents();

        call.enqueue(new Callback<List<EventDto>>() {
            @Override
            public void onResponse(Call<List<EventDto>> call, Response<List<EventDto>> response) {
                if (response.isSuccessful()) {
                    List<EventDto> events = response.body();
                    callback.onEventsLoaded(events);
                } else {
                    callback.onEventsLoadFailed("Failed to load events", context);
                }
            }

            @Override
            public void onFailure(Call<List<EventDto>> call, Throwable t) {
                callback.onEventsLoadFailed(t.toString(), context);
            }
        });
    }

    public static void fetchFilteredEvents(final EventCallback callback, String location, String type, Context context) {
        EventService eventService = getClient().create(EventService.class);
        Call<List<EventDto>> call = eventService.getEventsByLocationAndType(location, type);

        call.enqueue(new Callback<List<EventDto>>() {
            @Override
            public void onResponse(Call<List<EventDto>> call, Response<List<EventDto>> response) {
                if (response.isSuccessful()) {
                    List<EventDto> events = response.body();
                    callback.onFilteredEventsLoaded(events);
                } else {
                    callback.onEventsLoadFailed("Failed to load events", context);
                }
            }

            @Override
            public void onFailure(Call<List<EventDto>> call, Throwable t) {
                callback.onEventsLoadFailed(t.toString(), context);
            }
        });
    }

    public static void fetchEventForOrderId(String orderId, Context context, OrderFetchCallback callback) {
        EventService eventService = getClient().create(EventService.class);
        Call<EventDto> call = eventService.getEventByOrderId(orderId);

        call.enqueue(new Callback<EventDto>() {
            @Override
            public void onResponse(Call<EventDto> call, Response<EventDto> response) {
                if (response.isSuccessful()) {
                    EventDto event = response.body();
                    callback.onEventLoaded(event, context);
                } else {
                    callback.onEventLoadFailed("Failed to load events", context);
                }
            }

            @Override
            public void onFailure(Call<EventDto> call, Throwable t) {
                callback.onEventLoadFailed(t.toString(), context);
            }
        });
    }
}
