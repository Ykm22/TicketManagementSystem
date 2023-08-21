package com.example.ticketmanagementsystem_android.callbacks;

import android.content.Context;

import com.example.ticketmanagementsystem_android.models.dtos.EventDto;

public interface OrderFetchCallback {

    void onEventLoaded(EventDto event, Context context);

    void onEventLoadFailed(String toString, Context context);
}
