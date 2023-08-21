package com.example.ticketmanagementsystem_android.api_clients;

import android.util.Log;

import com.example.ticketmanagementsystem_android.callbacks.EventUpdateCallback;
import com.example.ticketmanagementsystem_android.callbacks.OrderCallback;
import com.example.ticketmanagementsystem_android.callbacks.OrderDeleteCallback;
import com.example.ticketmanagementsystem_android.callbacks.OrderSaveCallback;
import com.example.ticketmanagementsystem_android.callbacks.OrderUpdatedCallback;
import com.example.ticketmanagementsystem_android.callbacks.OrdersFetchedCallback;
import com.example.ticketmanagementsystem_android.models.dtos.CustomMessageDto;
import com.example.ticketmanagementsystem_android.models.dtos.OrderDto;
import com.example.ticketmanagementsystem_android.models.dtos.OrderNETDto;
import com.example.ticketmanagementsystem_android.models.dtos.OrderPostDto;
import com.example.ticketmanagementsystem_android.models.dtos.OrderSavedDto;
import com.example.ticketmanagementsystem_android.models.dtos.TicketCategoryDto;
import com.example.ticketmanagementsystem_android.models.dtos.TicketDto;
import com.example.ticketmanagementsystem_android.services.OrderService;

import java.security.cert.CertPathBuilder;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.PATCH;

public class OrderApiClient {
    private static final String BASE_URL_SPRING = "http://172.16.98.71:8080/tms/api/spring/";
//    private static final String BASE_URL_SPRING = "http://192.168.1.104:8080/tms/api/spring/";
    private static final String BASE_URL_NET = "https://172.16.98.71:9090/tms/api/net/";
//    private static final String BASE_URL_NET = "https://192.168.1.104:9090/tms/api/net/";
    private static Retrofit retrofit_spring = null;
    private static Retrofit retrofit_net = null;


    public static Retrofit getClient(String BE_type) {
        if(BE_type.equals("SPRING")){
            if (retrofit_spring == null) {
                OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder()
                        .connectTimeout(30, TimeUnit.SECONDS)
                        .readTimeout(30, TimeUnit.SECONDS)
                        .writeTimeout(30, TimeUnit.SECONDS);
                //            Gson gson = new GsonBuilder().setLenient().create();
                retrofit_spring = new Retrofit.Builder()
                        .baseUrl(BASE_URL_SPRING)
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(httpClientBuilder.build())
                        .build();
            }
            return retrofit_spring;
        } else
        {
            if(retrofit_net == null){
                OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder()
                        .connectTimeout(7, TimeUnit.SECONDS)
                        .readTimeout(7, TimeUnit.SECONDS)
                        .writeTimeout(7, TimeUnit.SECONDS);
//                    .followRedirects(true);
                //            Gson gson = new GsonBuilder().setLenient().create();
//            retrofit_net = new Retrofit.Builder()
//                    .baseUrl(BASE_URL_NET)
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .client(httpClientBuilder.build())
//                    .build();
                retrofit_net = new Retrofit.Builder()
                        .baseUrl(BASE_URL_NET)
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(getUnsafeOkHttpClient().build())
                        .build();
            }
            return retrofit_net;
        }
//        Log.e("retrofit create", "huuuh");
//        return null;
    }

    private static OkHttpClient.Builder getUnsafeOkHttpClient() {
        try{
            TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager(){
                        @Override
                        public X509Certificate[] getAcceptedIssuers() {
                            return new X509Certificate[0];
                        }
                        public void checkClientTrusted(X509Certificate[] certs, String authType) {
                            // Accept all client certificates
                        }
                        public void checkServerTrusted(X509Certificate[] certs, String authType) {
                            // Accept all server certificates
                        }
                    }
            };
            final SSLContext Context = SSLContext.getInstance("SSL");
            Context.init(null, trustAllCerts, new java.security.SecureRandom());

            final SSLSocketFactory sslSocketFactory = Context.getSocketFactory();

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
            builder.hostnameVerifier(new HostnameVerifier(){
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
            return builder;
        } catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }

    public static void saveOrder(UUID customerId, UUID eventId, List<TicketCategoryDto> ticketCategoriesDto, String selectedTicketType, int selectedTicketQuantity, final OrderSaveCallback callback) {
        TicketCategoryDto ticketCategoryDto = getTicketCategoryByDescription(selectedTicketType, ticketCategoriesDto);
        callPost(customerId, eventId, ticketCategoryDto.getId(), selectedTicketQuantity, callback);
    }

    private static TicketCategoryDto getTicketCategoryByDescription(String selectedTicketType, List<TicketCategoryDto> ticketCategoriesDto) {
        return ticketCategoriesDto.stream()
                .filter(tc -> tc.getDescription().equals(selectedTicketType))
                .findFirst().get();
    }

    private static void callPost(UUID customerId, UUID eventId, String ticketCategoryId, int selectedTicketQuantity, final OrderSaveCallback callback) {
        OrderService orderService = getClient("SPRING").create(OrderService.class);
        OrderPostDto orderPostDto = new OrderPostDto(eventId.toString(), ticketCategoryId, selectedTicketQuantity);
        Call<OrderSavedDto> call = orderService.saveOrder(customerId.toString(), orderPostDto);

        call.enqueue(new Callback<OrderSavedDto>() {
            @Override
            public void onResponse(Call<OrderSavedDto> call, Response<OrderSavedDto> response) {
                if (response.isSuccessful()) {
                    OrderSavedDto orderSavedDto = response.body();
                    callback.onOrderLoaded(orderSavedDto);
                } else {
                    callback.onOrderLoadFailed("Failed to save order");
                }
            }

            @Override
            public void onFailure(Call<OrderSavedDto> call, Throwable t) {
                callback.onOrderLoadFailed(t.toString());
            }
        });
    }

    public static void fetchOrdersForUser(String userId, OrdersFetchedCallback callback) {
        OrderService orderService = getClient("SPRING").create(OrderService.class);
        Call<List<OrderDto>> call = orderService.fetchOrdersForUserId(userId);

        call.enqueue(new Callback<List<OrderDto>>() {
            @Override
            public void onResponse(Call<List<OrderDto>> call, Response<List<OrderDto>> response) {
                if (response.isSuccessful()) {
                    List<OrderDto> orderList = response.body();
                    callback.onOrdersLoaded(orderList);
                } else {
                    callback.onOrdersLoadFailed("Failed to save order");
                }
            }

            @Override
            public void onFailure(Call<List<OrderDto>> call, Throwable t) {
                callback.onOrdersLoadFailed(t.toString());
            }
        });
    }

    public static void deleteOrder(String orderId, OrderDeleteCallback callback) {
        OrderService orderService = getClient("NET").create(OrderService.class);
        Call<CustomMessageDto> call = orderService.deleteOrderForId(orderId);

        call.enqueue(new Callback<CustomMessageDto>() {
            @Override
            public void onResponse(Call<CustomMessageDto> call, Response<CustomMessageDto> response) {
                if (response.isSuccessful()) {
                    String orderId = response.body().getMessage();
                    callback.onDeleteOrderSucceeded(orderId);
                } else {
                    callback.onDeleteOrderFailed("Failed to delete order");
                }
            }

            @Override
            public void onFailure(Call<CustomMessageDto> call, Throwable t) {
                callback.onDeleteOrderFailed(t.toString());
            }
        });
    }

    public static void updateOrder(String orderId, String selectedTicketType, int selectedTicketQuantity, OrderUpdatedCallback callback) {
        OrderService orderService = getClient("NET").create(OrderService.class);
        TicketDto ticketDto = new TicketDto(selectedTicketType, selectedTicketQuantity);
        Call<OrderSavedDto> call = orderService.updateOrder(orderId, ticketDto);

        call.enqueue(new Callback<OrderSavedDto>() {
            @Override
            public void onResponse(Call<OrderSavedDto> call, Response<OrderSavedDto> response) {
                if (response.isSuccessful()) {
                    OrderSavedDto order = response.body();
                    callback.onOrderUpdateLoaded(order);
                } else {
                    callback.onOrderUpdateLoadFailed("Failed to save order");
                }
            }

            @Override
            public void onFailure(Call<OrderSavedDto> call, Throwable t) {
                callback.onOrderUpdateLoadFailed(t.toString());
            }
        });
    }

}
