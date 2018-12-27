package com.example.android.nearvenues.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.nearvenues.R;
import com.example.android.nearvenues.models.Venue;

import java.text.DecimalFormat;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private List<Venue> venues;
    private int itemLayout;

    public MyAdapter(int itemLayout, List<Venue> venues) {
        this.itemLayout = itemLayout;
        this.venues = venues;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public int getItemCount() {
        return venues.size();
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bind(venues.get(position));
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private TextView address;
        private TextView distance;

        MyViewHolder(View itemView) {
            super(itemView);
            this.name = itemView.findViewById(R.id.venueName);
            this.address = itemView.findViewById(R.id.venueAddress);
            this.distance = itemView.findViewById(R.id.distance);
        }

        void bind(final Venue venue) {
            this.name.setText(venue.getName());
            this.address.setText(venue.getAddress());
            DecimalFormat format = new DecimalFormat("#.#");
            this.distance.setText(format.format(venue.getDistance()) + "m");
        }
    }
}
