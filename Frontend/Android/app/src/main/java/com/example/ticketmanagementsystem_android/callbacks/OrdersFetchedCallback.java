package com.example.ticketmanagementsystem_android.callbacks;

import com.example.ticketmanagementsystem_android.models.dtos.OrderDto;

import java.util.List;

public interface OrdersFetchedCallback {
    void onOrdersLoaded(List<OrderDto> orders);
    void onOrdersLoadFailed(String errorMessage);
}
