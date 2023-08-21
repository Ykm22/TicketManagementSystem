package com.example.ticketmanagementsystem_android.callbacks;

import com.example.ticketmanagementsystem_android.models.dtos.OrderSavedDto;

public interface OrderUpdatedCallback {
    void onOrderUpdateLoaded(OrderSavedDto orderSavedDto);
    void onOrderUpdateLoadFailed(String failed_to_save_order);
}
