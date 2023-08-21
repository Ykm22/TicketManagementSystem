package com.example.ticketmanagementsystem_android.callbacks;

import android.content.Context;

import com.example.ticketmanagementsystem_android.models.dtos.EventDto;

import java.util.List;

public interface EventCallback {
    void onEventsLoaded(List<EventDto> events);
    void onEventsLoadFailed(String errorMessage, Context context);

    void onFilteredEventsLoaded(List<EventDto> events);
}
