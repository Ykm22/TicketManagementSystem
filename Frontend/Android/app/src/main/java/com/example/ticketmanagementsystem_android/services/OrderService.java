package com.example.ticketmanagementsystem_android.services;

import com.example.ticketmanagementsystem_android.models.dtos.CustomMessageDto;
import com.example.ticketmanagementsystem_android.models.dtos.OrderDto;
import com.example.ticketmanagementsystem_android.models.dtos.OrderPostDto;
import com.example.ticketmanagementsystem_android.models.dtos.OrderSavedDto;
import com.example.ticketmanagementsystem_android.models.dtos.TicketDto;

import java.util.List;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface OrderService {
    @POST("orders")
    Call<OrderSavedDto> saveOrder(@Query("customerId") String customerId,
                                  @Body OrderPostDto orderPostDto);

    @GET("orders")
    Call<List<OrderDto>> fetchOrdersForUserId(@Query("userId") String userId);

    @DELETE("orders/{orderId}")
    Call<CustomMessageDto> deleteOrderForId(@Path("orderId") String orderId);
    @PATCH("orders/{orderId}")
    Call<OrderSavedDto> updateOrder(
            @Path("orderId") String orderId,
            @Body TicketDto ticketDto
    );
}
