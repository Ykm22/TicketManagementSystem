package com.example.ticketmanagementsystem_android.activities.customer_activities;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ticketmanagementsystem_android.R;
import com.example.ticketmanagementsystem_android.api_clients.EventApiClient;
import com.example.ticketmanagementsystem_android.api_clients.OrderApiClient;
import com.example.ticketmanagementsystem_android.callbacks.EventCallback;
import com.example.ticketmanagementsystem_android.callbacks.OrderSaveCallback;
import com.example.ticketmanagementsystem_android.dialog_fragments.CustomDialogFragment;
import com.example.ticketmanagementsystem_android.models.Event;
import com.example.ticketmanagementsystem_android.models.TicketCategory;
import com.example.ticketmanagementsystem_android.models.dtos.EventDto;
import com.example.ticketmanagementsystem_android.models.dtos.OrderSavedDto;
import com.example.ticketmanagementsystem_android.adapters.EventAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class EventsDisplayActivity extends AppCompatActivity implements EventCallback {
    private List<EventDto> eventList = new ArrayList<>();
    private RecyclerView eventRecyclerView;
    private EventAdapter eventAdapter;
    private RecyclerView.LayoutManager eventLayoutManager;
    private TextView locationTextView;
    private UUID customerId;
    private TextView typeTextView;
    private PopupMenu locationPopupMenu;
    private PopupMenu typePopupMenu;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        try{
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_events_display);

            EventApiClient.fetchEvents(this, getBaseContext());

//            customerId = UUID.fromString(getIntent().getStringExtra("customerId"));

            //to avoid login in running application
            customerId = UUID.fromString("52121162-C87B-44B2-9D86-0866D610E363");
//            Log.i("customerId", customerId.toString());

            eventRecyclerView = findViewById(R.id.events_recycler);
            eventLayoutManager = new LinearLayoutManager(this);
            eventRecyclerView.setLayoutManager(eventLayoutManager);
            eventAdapter = new EventAdapter(this, eventList);
            eventRecyclerView.setAdapter(eventAdapter);
            eventAdapter.setOnMyItemClickListener(this::onMyItemClick);

            locationTextView = findViewById(R.id.locationTextView);
            locationTextView.setOnClickListener(v -> locationPopupMenu.show());

            typeTextView = findViewById(R.id.typeTextView);
            typeTextView.setOnClickListener(v -> typePopupMenu.show());

            ImageButton hamBarButton = findViewById(R.id.hamBarButton);
            hamBarButton.setOnClickListener(v -> {
                CustomDialogFragment dialogFragment = CustomDialogFragment.newInstance("eventsDisplay", customerId);
                dialogFragment.show(getSupportFragmentManager(), "dialog");
            });

        } catch (Exception ex){
            Log.v("error in main try block", ex.getMessage());
        }

    }

    private void populateTypePopupMenu() {
        typePopupMenu = new PopupMenu(this, typeTextView);
        typePopupMenu.inflate(R.menu.menu_toolbar_type);

        Set<String> types = new HashSet<>();
        Menu menu = typePopupMenu.getMenu();

        for (EventDto event : eventList){
            String type = event.getType();
            if(!types.contains(type)){
                menu.add(Menu.NONE, 0, Menu.NONE, type);
                types.add(type);
            }
        }

        typePopupMenu.setOnMenuItemClickListener(item -> {
            String selectedType = item.getTitle().toString();
            TextView typeTextView = findViewById(R.id.typeTextView);
            typeTextView.setText(selectedType);
            return true;
        });
    }

    private void populateLocationPopupMenu() {
        locationPopupMenu = new PopupMenu(this, locationTextView);
        locationPopupMenu.inflate(R.menu.menu_toolbar_location);

        Set<String> locations = new HashSet<>();
        Menu menu = locationPopupMenu.getMenu();

        for (EventDto event : eventList){
            String location = event.getVenue().getLocation();
            if(!locations.contains(location)){
                menu.add(Menu.NONE, 0, Menu.NONE, location);
                locations.add(location);
            }
        }
        locationPopupMenu.setOnMenuItemClickListener(item -> {
            String selectedLocation = item.getTitle().toString();
            TextView locationTextView = findViewById(R.id.locationTextView);
            locationTextView.setText(selectedLocation);
            return true;
        });
    }

    @Override
    public void onEventsLoaded(List<EventDto> events) {
//        this.eventList = events;
        eventList.clear();
        eventList.addAll(events);
        eventAdapter.notifyDataSetChanged();

        populateLocationPopupMenu();
        populateTypePopupMenu();

        Log.i("Events loaded", String.valueOf(events.size()));
    }

    @Override
    public void onEventsLoadFailed(String errorMessage, Context context) {
        Log.e("EventsLoadFailed", errorMessage);
        Toast.makeText(context, "Error.. " + errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFilteredEventsLoaded(List<EventDto> events) {
        eventList.clear();
        eventList.addAll(events);
        eventAdapter.notifyDataSetChanged();
        Log.i("Events loaded", String.valueOf(events.size()));
    }

    private List<Event> getMockEvents() {
        Event event1 = new Event("conference", "10-08-2023", "15-08-2023", "10", "conference");
        TicketCategory ticketCategory1 = new TicketCategory("Standard", 10.99, UUID.randomUUID());
        TicketCategory ticketCategory2 = new TicketCategory("VIP", 10.99, UUID.randomUUID());
        event1.setTicketCategories(new ArrayList<>(Arrays.asList(ticketCategory1, ticketCategory2)));

        Event event2 = new Event("castle", "10-08-2023", "15-08-2023", "10", "castle");
        TicketCategory ticketCategory3 = new TicketCategory("Standard", 15.99, UUID.randomUUID());
        event2.setTicketCategories(new ArrayList<>(Arrays.asList(ticketCategory3)));

        Event event3 = new Event("ballroom", "10-08-2023", "15-08-2023", "10", "ballroom");
        Event event4 = new Event("park", "10-08-2023", "15-08-2023", "10", "park");
        Event event5 = new Event("stadion", "10-08-2023", "15-08-2023", "10", "stadion");
        Event event6 = new Event("university", "10-08-2023", "15-08-2023", "10", "university");
        Event event7 = new Event("theater", "10-08-2023", "15-08-2023", "10", "theater");
        return new ArrayList<>(Arrays.asList(event1, event2, event3, event4, event5, event6, event7));
    }

    private void onMyItemClick(int position) {
        RecyclerView.ViewHolder viewHolder = eventRecyclerView.findViewHolderForAdapterPosition(position);
        if(viewHolder instanceof EventAdapter.ViewHolder){
            EventAdapter.ViewHolder eventViewHolder = (EventAdapter.ViewHolder)viewHolder;
            createTicketBuyingDialog(eventViewHolder);
        }
    }

    private void createTicketBuyingDialog(EventAdapter.ViewHolder eventViewHolder) {
        // creating the dialog
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_ticket_buying, null);
        AlertDialog dialog = buildDialog(dialogView);

        // setting its views
        NumberPicker numberPickerTickets = createTicketsNumberPicker(eventViewHolder, dialogView);
        Spinner ticketTypeSpinner = createTicketTypeSpinner(eventViewHolder, dialogView);
        setConfirmButtonAction(dialog, dialogView, numberPickerTickets, ticketTypeSpinner, eventViewHolder);

        // show dialog
        dialog.show();
    }

    private AlertDialog buildDialog(View dialogView) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);
        return builder.create();
    }

    private void setConfirmButtonAction(AlertDialog dialog, View dialogView, NumberPicker numberPickerTickets, Spinner ticketTypeSpinner, EventAdapter.ViewHolder eventViewHolder) {
        Button confirmButton = dialogView.findViewById(R.id.confirmButton);
        confirmButton.setOnClickListener(v -> {
            int selectedTicketQuantity = numberPickerTickets.getValue();
            String selectedTicketType = ticketTypeSpinner.getSelectedItem().toString();
            OrderApiClient.saveOrder(customerId, eventViewHolder.getEventId(), eventViewHolder.getTicketCategoriesDto(), selectedTicketType, selectedTicketQuantity, new OrderSaveCallback() {
                @Override
                public void onOrderLoaded(OrderSavedDto orderSavedDto) {
                    Toast.makeText(getBaseContext(), "Order saved successfully!\nTotal price: " + orderSavedDto.getTotalPrice(), Toast.LENGTH_SHORT).show();
                    try{
                        eventViewHolder.availableTickets.setText("Available tickets: " + (eventViewHolder.getAvailableTickets() - orderSavedDto.getNumberOfTickets()));
                    } catch(Exception ex){
                        Log.i("some exc", ex.getMessage());
                        Toast.makeText(getBaseContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onOrderLoadFailed(String failed_to_save_order) {
                    Log.e("OrderSave failed", failed_to_save_order);
                    Toast.makeText(getBaseContext(), failed_to_save_order, Toast.LENGTH_SHORT).show();
                }
            });
            dialog.dismiss();
        });
    }

    private Spinner createTicketTypeSpinner(EventAdapter.ViewHolder eventViewHolder, View dialogView) {
        Spinner ticketTypeSpinner = dialogView.findViewById(R.id.ticketTypeSpinner);
        List<String> ticketTypes = eventViewHolder.getTicketTypes();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, ticketTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ticketTypeSpinner.setAdapter(adapter);
        return ticketTypeSpinner;
    }

    private NumberPicker createTicketsNumberPicker(EventAdapter.ViewHolder eventViewHolder, View dialogView) {
        NumberPicker numberPickerTickets = dialogView.findViewById(R.id.numberPickerTickets);
        numberPickerTickets.setMaxValue(eventViewHolder.getAvailableTickets());
        numberPickerTickets.setMinValue(1);
        return numberPickerTickets;
    }

    public void onFilterButtonClicked(View view) {
        String location = locationTextView.getText().toString();
        String type = typeTextView.getText().toString();
        // TODO: idk if getBaseContext() works, might have to refactor everywhere where context is
        EventApiClient.fetchFilteredEvents(this, location, type, getBaseContext());
    }

    public void onRefreshButtonClicked(View view, Context context) {
        EventApiClient.fetchEvents(this, context);
    }

}
