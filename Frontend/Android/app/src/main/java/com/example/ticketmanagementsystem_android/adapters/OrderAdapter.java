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
import androidx.recyclerview.widget.RecyclerView;

import com.example.ticketmanagementsystem_android.R;
import com.example.ticketmanagementsystem_android.callbacks.EventUpdateCallback;
import com.example.ticketmanagementsystem_android.models.dtos.EventOrderDto;
import com.example.ticketmanagementsystem_android.models.dtos.EventUpdateDto;
import com.example.ticketmanagementsystem_android.models.dtos.OrderDto;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder>{
    private List<OrderDto> orderList;
    private OrderAdapter.OnMyOrderClickListener orderListener;

    public OrderAdapter(Context context, List<OrderDto> orderList){
        this.orderList = orderList;
    }

    public void setOrderList(List<OrderDto> orderList) {
        try{
            if(orderList != null){
//                Log.i("size", String.valueOf(eventList.size()));
                this.orderList.clear();
                this.orderList.addAll(orderList);
                notifyDataSetChanged();
            }
        } catch(Exception ex){
            Log.i("xd", ex.getMessage());
        }
    }

    @NonNull
    @Override
    public OrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_card, parent, false);
        return new OrderAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.ViewHolder holder, int position) {
//        Log.i("on bind view holder", "aloooooo");
        OrderDto event = orderList.get(position);
        holder.bind(event);
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public EventUpdateDto removeOrderById(String orderId) {
        for (int i = 0; i < orderList.size(); i++) {
            OrderDto order = orderList.get(i);
            if (order.getOrderId().equals(orderId)) {
                OrderDto orderedRemoved = orderList.remove(i);
                notifyItemRemoved(i);
                return new EventUpdateDto(
                        orderedRemoved.getEventDto().getEventId(),
                        orderedRemoved.getNumberOfTickets()
                );
            }
        }
        return new EventUpdateDto("", 0);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private int availableTickets;
        public TextView eventName;
        public TextView eventLocation;
        public TextView startDate;
        public TextView endDate;
        public TextView numberOfTickets;
        public TextView totalPrice;
        public TextView lastModified;
        public ImageView imageView;
        public TextView ticketType;
        private String orderId;

        public String getOrderId() {
            return orderId;
        }

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            eventName = itemView.findViewById(R.id.event_name);
            eventLocation = itemView.findViewById(R.id.event_location);
            startDate = itemView.findViewById(R.id.start_date_order);
            endDate = itemView.findViewById(R.id.end_date_order);
            numberOfTickets = itemView.findViewById(R.id.number_of_tickets);
            totalPrice = itemView.findViewById(R.id.totalPrice);
            lastModified = itemView.findViewById(R.id.last_modified);
            ticketType = itemView.findViewById(R.id.ticket_type);
            itemView.setOnClickListener(v -> {
                if(orderListener != null){
                    orderListener.onMyOrderClickListener(getAdapterPosition());
                }
            });
        }

        @SuppressLint("SetTextI18n")
        private void bind(OrderDto order){
            setEventDetails(order.getEventDto());
            setOrderDetails(order);
            setPrivateAttributes(order);
        }

        private void setPrivateAttributes(OrderDto order) {
            orderId = order.getOrderId();
        }

        private void setOrderDetails(OrderDto order) {
            totalPrice.setText("£" + (order.getTotalPrice()));
            lastModified.setText(order.getOrderedAt().split("T")[0]);
            ticketType.setText(order.getTicketCategoryDto().getDescription());
            if(order.getNumberOfTickets() == 1){
                numberOfTickets.setText((order.getNumberOfTickets()) + " Ticket");
            } else{
                numberOfTickets.setText((order.getNumberOfTickets()) + " Tickets");
            }
        }

        private void setEventDetails(EventOrderDto event) {
            eventName.setText(event.getName());
            eventLocation.setText(event.getLocation());
            startDate.setText(event.getStartDate().split("T")[0]);
            endDate.setText(event.getEndDate().split("T")[0]);
            imageView = getImage(itemView, event.getVenueType());
        }

        private ImageView getImage(View itemView, String venueType) {
            ImageView imageView = itemView.findViewById(R.id.background_image_order);
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

    }
    public interface OnMyOrderClickListener {
        void onMyOrderClickListener(int position);
    }
    public void setOnMyOrderClickListener(OrderAdapter.OnMyOrderClickListener listener){
        this.orderListener = listener;
    }

}
