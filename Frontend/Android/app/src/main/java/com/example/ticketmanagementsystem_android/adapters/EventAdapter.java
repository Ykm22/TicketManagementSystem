package com.example.ticketmanagementsystem_android.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ticketmanagementsystem_android.R;
import com.example.ticketmanagementsystem_android.models.dtos.EventDto;
import com.example.ticketmanagementsystem_android.models.dtos.TicketCategoryDto;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {

    private List<EventDto> eventList;
    private OnMyItemClickListener eventListener;
    public EventAdapter(Context context, List<EventDto> eventList){
        this.eventList = eventList;
    }

    public void setEventList(List<EventDto> eventList) {
        try{
            if(eventList != null){
//                Log.i("size", String.valueOf(eventList.size()));
//                this.eventList.clear();
                this.eventList = eventList;
                notifyDataSetChanged();
            }
        } catch(Exception ex){
            Log.i("xd", ex.getMessage());
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        Log.i("on bind view holder", "aloooooo");
        EventDto event = eventList.get(position);
        holder.bind(event);
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private int availableTicketsValue;
        private List<TicketCategoryDto> ticketCategoriesDto;

        public List<TicketCategoryDto> getTicketCategoriesDto() {
            return ticketCategoriesDto;
        }

        public void setTicketCategoriesDto(List<TicketCategoryDto> ticketCategoriesDto) {
            this.ticketCategoriesDto = ticketCategoriesDto;
        }

        public int getAvailableTickets() {
            return availableTicketsValue;
        }

        public void setAvailableTickets(int availableTickets) {
            this.availableTicketsValue = availableTickets;
        }

        public CardView eventView;
        public TextView eventName;
        public TextView startDate;
        public TextView endDate;
        public TextView availableTickets;
        public ImageView imageView;
        private UUID eventId;
        public TextView standardPrice;
        public TextView vipPrice;

        private List<String> ticketDescriptions;
        public UUID getEventId() {
            return eventId;
        }

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            eventView = itemView.findViewById(R.id.event_card);
            eventName = itemView.findViewById(R.id.event_name);
            startDate = itemView.findViewById(R.id.start_date);
            endDate = itemView.findViewById(R.id.end_date);
            availableTickets = itemView.findViewById(R.id.available_tickets);
            imageView = itemView.findViewById(R.id.background_image);
            standardPrice = itemView.findViewById(R.id.standard_price);
            vipPrice = itemView.findViewById(R.id.vip_price);

            itemView.setOnClickListener(v -> {
                if(eventListener != null){
                    eventListener.onMyItemClickListener(getAdapterPosition());
                }
            });
        }

        @SuppressLint("SetTextI18n")
        private void bind(EventDto event){
            ticketCategoriesDto = event.getTicketCategories();
            availableTicketsValue = event.getAvailableTickets();
            eventId = UUID.fromString(event.getEventId());
            eventName.setText(event.getName());
            startDate.setText(event.getStartDate().split("T")[0]);
            endDate.setText(event.getEndDate().split("T")[0]);
            availableTickets.setText("Available tickets: " + event.getAvailableTickets());
            standardPrice.setText("");
            vipPrice.setText("");
            List<TicketCategoryDto> ticketCategories = event.getTicketCategories();
            if(ticketCategories != null){
                ticketDescriptions = new ArrayList<>();
                for(TicketCategoryDto ticketCategory : ticketCategories){
                    if(ticketCategory.getDescription().equals("Standard")){
                        ticketDescriptions.add("Standard");
                        standardPrice.setText("Standard: " + ticketCategory.getPrice());
                    }
                    if(ticketCategory.getDescription().equals("VIP")){
                        ticketDescriptions.add("VIP");
                        vipPrice.setText("VIP: " + ticketCategory.getPrice());
                    }
                }
            }
            imageView = getImage(itemView, event.getVenue().getType());
        }

        private ImageView getImage(View itemView, String venueType) {
            ImageView imageView = itemView.findViewById(R.id.background_image);
            if(venueType.equals("Conference Center")){
                imageView.setImageResource(R.drawable.conference_center);
            }
            if(venueType.equals("Castle")){
                imageView.setImageResource(R.drawable.castle);
            }
            if(venueType.equals("Ballroom")){
                imageView.setImageResource(R.drawable.ballroom);
            }
            if(venueType.equals("Park")){
                imageView.setImageResource(R.drawable.park);
            }
            if(venueType.equals("Stadion")){
                imageView.setImageResource(R.drawable.stadium);
            }
            if(venueType.equals("Theater")){
                imageView.setImageResource(R.drawable.theater);
            }
            if(venueType.equals("University")){
                imageView.setImageResource(R.drawable.university);
            }
            return imageView;
        }

        public List<String> getTicketTypes() {
            return ticketDescriptions;
        }
    }

    public interface OnMyItemClickListener {
        void onMyItemClickListener(int position);
    }
    public void setOnMyItemClickListener(OnMyItemClickListener listener){
        this.eventListener = listener;
    }
}
