package com.example.ticketmanagementsystem_android.dialog_fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.ticketmanagementsystem_android.R;
import com.example.ticketmanagementsystem_android.adapters.EventAdapter;
import com.example.ticketmanagementsystem_android.adapters.OrderAdapter;
import com.example.ticketmanagementsystem_android.api_clients.EventApiClient;
import com.example.ticketmanagementsystem_android.api_clients.OrderApiClient;
import com.example.ticketmanagementsystem_android.callbacks.OrderDeleteCallback;
import com.example.ticketmanagementsystem_android.callbacks.OrderFetchCallback;
import com.example.ticketmanagementsystem_android.callbacks.OrderUpdatedCallback;
import com.example.ticketmanagementsystem_android.models.dtos.EventDto;
import com.example.ticketmanagementsystem_android.models.dtos.OrderSavedDto;
import com.example.ticketmanagementsystem_android.models.dtos.TicketCategoryDto;

import java.util.List;
import java.util.stream.Collectors;

public class OrderOptionsDialogFragment extends DialogFragment {
    private String orderId;
    private OrderDeleteCallback orderDeleteCallback;
    private OrderUpdatedCallback orderUpdatedCallback;
    private int availableTickets;
    public NumberPicker numberPickerTickets;
    private Spinner ticketTypeSpinner;
    private OrderAdapter.ViewHolder orderViewHolder;

    public static OrderOptionsDialogFragment newInstance(OrderAdapter.ViewHolder orderViewHolder, OrderDeleteCallback orderDeleteCallback){
        Bundle args = new Bundle();
        args.putString("orderId", orderViewHolder.getOrderId());
        OrderOptionsDialogFragment dialogFragment = new OrderOptionsDialogFragment(orderDeleteCallback, orderViewHolder);
        dialogFragment.setArguments(args);
        return dialogFragment;
    }

    public OrderOptionsDialogFragment(OrderDeleteCallback orderDeleteCallback, OrderAdapter.ViewHolder orderViewHolder) {
        this.orderDeleteCallback = orderDeleteCallback;
        this.orderViewHolder = orderViewHolder;
        this.orderUpdatedCallback = orderUpdatedCallback;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog_order_options, container, false);
        Bundle args = getArguments();
        if(args != null){
            orderId = args.getString("orderId");
        }
        Button updateOrderButton = view.findViewById(R.id.update_order);
        Button deleteOrderButton = view.findViewById(R.id.delete_order);

        updateOrderButton.setOnClickListener(v -> {
            createOrderUpdateDialog(orderViewHolder, orderUpdatedCallback);
            dismiss();
        });
        deleteOrderButton.setOnClickListener(v -> {
            OrderApiClient.deleteOrder(orderId, orderDeleteCallback);
            dismiss();
        });

        return view;
    }

    private void createOrderUpdateDialog(OrderAdapter.ViewHolder orderViewHolder, OrderUpdatedCallback callback) {
        View dialogView = LayoutInflater.from(this.getContext()).inflate(R.layout.dialog_ticket_updating, null);
        AlertDialog dialog = buildDialog(dialogView);
        // PUT dialogView, not dialog
        numberPickerTickets = dialogView.findViewById(R.id.numberPickerTickets_update);
        ticketTypeSpinner = dialogView.findViewById(R.id.ticketTypeSpinner_update);
        EventApiClient.fetchEventForOrderId(orderId, dialog.getContext(), new OrderFetchCallback() {
            @Override
            public void onEventLoaded(EventDto event, Context context) {
                availableTickets = event.getAvailableTickets();
                numberPickerTickets.setMaxValue(availableTickets);
                numberPickerTickets.setMinValue(1);
                ArrayAdapter<String> ticketTypeSpinnerAdapter = getAdapter(event, context);
                ticketTypeSpinner.setAdapter(ticketTypeSpinnerAdapter);
            }

            private ArrayAdapter<String> getAdapter(EventDto event, Context context) {
                List<String> ticketTypes = event.getTicketCategories()
                        .stream().map(TicketCategoryDto::getDescription).collect(Collectors.toList());
                try{
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, ticketTypes);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    return adapter;
                } catch (Exception ex){
                    Log.e("error:(", "idk how to fix this XD: " + ex.getMessage());
                    Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
                return null;
            }
            @Override
            public void onEventLoadFailed(String errorMessage, Context context) {
                Log.e("error loading event", errorMessage);
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });

        setConfirmButtonAction(dialog, dialogView, numberPickerTickets, ticketTypeSpinner, orderViewHolder);

        // show dialog
        dialog.show();
    }
    private void setConfirmButtonAction(AlertDialog dialog, View dialogView, NumberPicker numberPickerTickets, Spinner ticketTypeSpinner, OrderAdapter.ViewHolder orderViewHolder) {
        Button confirmButton = dialogView.findViewById(R.id.confirmButton);
        confirmButton.setOnClickListener(v -> {
            int selectedTicketQuantity = numberPickerTickets.getValue();
            String selectedTicketType = ticketTypeSpinner.getSelectedItem().toString();
            OrderApiClient.updateOrder(orderViewHolder.getOrderId(), selectedTicketType, selectedTicketQuantity, new OrderUpdatedCallback() {
                @Override
                public void onOrderUpdateLoaded(OrderSavedDto orderUpdatedDto) {
                    try{
//                        Log.i("hihi", "hehe");
                        Log.i("UpdateOrder", "Success mfk");
                        orderViewHolder.ticketType.setText(orderUpdatedDto.getDescription());
                        orderViewHolder.totalPrice.setText("£" + orderUpdatedDto.getTotalPrice());
                        if(orderUpdatedDto.getNumberOfTickets() == 1){
                            orderViewHolder.numberOfTickets.setText(orderUpdatedDto.getNumberOfTickets() + " Ticket");
                        } else{
                            orderViewHolder.numberOfTickets.setText(orderUpdatedDto.getNumberOfTickets() + " Tickets");
                        }
                    } catch(Exception ex){
                        Log.i("some exc", ex.getMessage());
                    }
                }

                @Override
                public void onOrderUpdateLoadFailed(String failed_to_save_order) {
                    //nu da getContext(), tre sa fii in ceva view(?)
                    Log.e("UpdateOrder", failed_to_save_order);
                    Toast.makeText(getContext(), failed_to_save_order, Toast.LENGTH_SHORT).show();
                }
            });
            dialog.dismiss();
        });
    }
    private Spinner createTicketTypeSpinner(EventAdapter.ViewHolder eventViewHolder, View dialogView) {
        List<String> ticketTypes = eventViewHolder.getTicketTypes();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getContext(), android.R.layout.simple_spinner_dropdown_item, ticketTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ticketTypeSpinner.setAdapter(adapter);
        return ticketTypeSpinner;
    }
    private NumberPicker createTicketsNumberPicker(int availableTickets, View dialogView) {
        numberPickerTickets.setMaxValue(availableTickets);
        numberPickerTickets.setMinValue(1);
        return numberPickerTickets;
    }
    private AlertDialog buildDialog(View dialogView) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
        builder.setView(dialogView);
        return builder.create();
    }

}
