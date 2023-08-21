package com.example.ticketmanagementsystem_android.callbacks;

public interface OrderDeleteCallback {
    void onDeleteOrderSucceeded(String orderId);
    void onDeleteOrderFailed(String errorMesage);
}
