package com.example.ticketmanagementsystem_android.callbacks;

import com.example.ticketmanagementsystem_android.models.dtos.EventUpdateDto;

public interface EventUpdateCallback {
    void onOrderDeleted(EventUpdateDto eventUpdate);
}
