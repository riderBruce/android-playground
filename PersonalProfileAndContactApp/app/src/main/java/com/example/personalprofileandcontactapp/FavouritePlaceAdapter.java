package com.example.personalprofileandcontactapp;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class FavouritePlaceAdapter extends RecyclerView.Adapter<FavouritePlaceAdapter.ViewHolder> {

    public interface OnFavouritePlaceClickListener {
        void onFavouritePlaceClicked(FavouritePlace place);
    }
    private List<FavouritePlace> places = new ArrayList<>();

    private final OnFavouritePlaceClickListener listener;

    public FavouritePlaceAdapter(OnFavouritePlaceClickListener listener) {
        this.listener = listener;
    }

    public void setPlaces(List<FavouritePlace> newPlaces) {
        places.clear();

        if (newPlaces != null) {
            places.addAll(newPlaces);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FavouritePlaceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favourite_place, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavouritePlaceAdapter.ViewHolder holder, int position) {
        FavouritePlace place = places.get(position);
        holder.bind(place);
    }

    @Override
    public int getItemCount() {
        return places.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvId;
        private final TextView tvPlaceName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvId = itemView.findViewById(R.id.tvId);
            tvPlaceName = itemView.findViewById(R.id.tvPlaceName);

            itemView.setOnClickListener(v->{
                int position = getBindingAdapterPosition();

                if (position !=RecyclerView.NO_POSITION && listener != null){
                    listener.onFavouritePlaceClicked(places.get(position));
                }
            });
        }

        public void bind(FavouritePlace place) {
            tvId.setText(String.valueOf(place.getId()));
            tvPlaceName.setText(place.getPlaceName());
        }
    }
}
