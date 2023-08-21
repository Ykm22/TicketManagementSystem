package com.example.ticketmanagementsystem_android.activities.customer_activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ticketmanagementsystem_android.R;
import com.example.ticketmanagementsystem_android.api_clients.OrderApiClient;
import com.example.ticketmanagementsystem_android.callbacks.EventUpdateCallback;
import com.example.ticketmanagementsystem_android.callbacks.OrderDeleteCallback;
import com.example.ticketmanagementsystem_android.callbacks.OrderUpdatedCallback;
import com.example.ticketmanagementsystem_android.callbacks.OrdersFetchedCallback;
import com.example.ticketmanagementsystem_android.dialog_fragments.OrderOptionsDialogFragment;
import com.example.ticketmanagementsystem_android.models.dtos.EventUpdateDto;
import com.example.ticketmanagementsystem_android.models.dtos.OrderDto;
import com.example.ticketmanagementsystem_android.adapters.OrderAdapter;

import java.util.ArrayList;
import java.util.List;

public class OrdersDisplayActivity extends AppCompatActivity implements OrdersFetchedCallback, OrderDeleteCallback {
    private RecyclerView ordersRecyclerView;
    private OrderAdapter orderAdapter;
    private List<OrderDto> orderList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        // Retrieve the user ID from intent extras
        String userId = getIntent().getStringExtra("userId");
        OrderApiClient.fetchOrdersForUser(userId, this);

        ordersRecyclerView = findViewById(R.id.orders_recycler);
        ordersRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        orderList = new ArrayList<>();
        orderAdapter = new OrderAdapter(this, orderList);
        orderAdapter.setOnMyOrderClickListener(this::onMyOrderClick);
        ordersRecyclerView.setAdapter(orderAdapter);

    }
    private void onMyOrderClick(int position) {
        RecyclerView.ViewHolder viewHolder = ordersRecyclerView.findViewHolderForAdapterPosition(position);
        if(viewHolder instanceof OrderAdapter.ViewHolder){
            OrderAdapter.ViewHolder orderViewHolder = (OrderAdapter.ViewHolder)viewHolder;
            createOrderOptionsDialogFragment(orderViewHolder, this);
        }
    }

    private void createOrderOptionsDialogFragment(OrderAdapter.ViewHolder orderViewHolder, OrderDeleteCallback orderDeleteCallback) {
        OrderOptionsDialogFragment dialogFragment = OrderOptionsDialogFragment.newInstance(orderViewHolder, orderDeleteCallback);
        dialogFragment.show(getSupportFragmentManager(), "order_options_dialog");
    }

    @Override
    public void onOrdersLoaded(List<OrderDto> orders) {
        orderList.clear();
        orderList.addAll(orders);
        orderAdapter.notifyDataSetChanged();
    }

    @Override
    public void onOrdersLoadFailed(String errorMessage) {
        Log.i("orders load", errorMessage);
        Toast.makeText(getBaseContext(), errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDeleteOrderSucceeded(String deletedOrderId) {
        EventUpdateDto eventUpdate = orderAdapter.removeOrderById(deletedOrderId);
//        eventUpdateCallback.onOrderDeleted(eventUpdate);
    }

    @Override
    public void onDeleteOrderFailed(String errorMessage) {
        Log.e("delete failed", errorMessage);
        Toast.makeText(getBaseContext(), errorMessage, Toast.LENGTH_SHORT).show();
    }
}