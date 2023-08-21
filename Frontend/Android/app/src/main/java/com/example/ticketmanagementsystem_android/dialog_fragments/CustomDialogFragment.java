package com.example.ticketmanagementsystem_android.dialog_fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.example.ticketmanagementsystem_android.R;
import com.example.ticketmanagementsystem_android.activities.customer_activities.OrdersDisplayActivity;

import java.util.UUID;

public class CustomDialogFragment extends DialogFragment {
    private String currentView;
    private UUID userId;
    public static CustomDialogFragment newInstance(String currentView, UUID userId) {
        Bundle args = new Bundle();
        args.putString("currentView", currentView);
        args.putString("userId", userId.toString());
        CustomDialogFragment fragment = new CustomDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog_layout, container, false);

        Bundle args = getArguments();
        if(args != null){
            currentView = args.getString("currentView");
            userId = UUID.fromString(args.getString("userId"));
        }
        Log.i("in custom dialog fragment", userId.toString());
        Button viewEventsButton = view.findViewById(R.id.view_events);
        Button viewOrdersButton = view.findViewById(R.id.view_orders);

        viewEventsButton.setOnClickListener(v -> {
            if(currentView.equals("eventsDisplay")){
                Toast.makeText(this.getContext(), "Already on this view..", Toast.LENGTH_SHORT).show();
            } else{
                Toast.makeText(this.getContext(), "Redirecting to events", Toast.LENGTH_SHORT).show();
            }
            dismiss();
        });

        viewOrdersButton.setOnClickListener(v -> {
            if(currentView.equals("ordersDisplay")){
                Toast.makeText(this.getContext(), "Already on this view..", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(getContext(), OrdersDisplayActivity.class);
                intent.putExtra("userId", userId.toString());
                startActivity(intent);
                Toast.makeText(this.getContext(), "Redirecting to orders", Toast.LENGTH_SHORT).show();

            }
            dismiss();
        });

        return view;
    }
}
