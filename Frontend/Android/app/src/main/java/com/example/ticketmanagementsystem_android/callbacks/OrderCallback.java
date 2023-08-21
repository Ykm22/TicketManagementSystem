package com.example.ticketmanagementsystem_android.callbacks;

import com.example.ticketmanagementsystem_android.models.dtos.TicketCategoryDto;

public interface OrderCallback {
    void onTicketCategoryLoaded(TicketCategoryDto ticketCategoryDto);

    void onTicketCategoryLoadFailed(String failed_to_load_events);
}
