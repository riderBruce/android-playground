package com.example.personalprofileandcontactapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FavouritePlacesActivity extends AppCompatActivity
        implements FavouritePlaceAdapter.OnFavouritePlaceClickListener {

    RecyclerView rvFavouritePlaces;
    RecyclerView.LayoutManager manager;
    FavouritePlaceAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_favourite_places);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        rvFavouritePlaces = findViewById(R.id.rvFavouritePlaces);

        manager = new LinearLayoutManager(this);
        rvFavouritePlaces.setLayoutManager(manager);

        adapter = new FavouritePlaceAdapter(this);
        rvFavouritePlaces.setAdapter(adapter);

        List<FavouritePlace> places = Arrays.asList(
                new FavouritePlace(1, "North Vancouver"),
                new FavouritePlace(2, "New Westminster"),
                new FavouritePlace(3, "Whistler")
        );

        adapter.setPlaces(places);
    }

    @Override
    public void onFavouritePlaceClicked(FavouritePlace place) {

        Intent intent = new Intent(FavouritePlacesActivity.this, FavouritePlaceDetailActivity.class);
        intent.putExtra("place", place);
        startActivity(intent);
    }
}