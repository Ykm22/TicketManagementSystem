package com.example.ticketmanagementsystem_android.callbacks;

import com.example.ticketmanagementsystem_android.models.dtos.OrderSavedDto;

public interface OrderSaveCallback {
    void onOrderLoaded(OrderSavedDto orderSavedDto);

    void onOrderLoadFailed(String failed_to_save_order);
}
