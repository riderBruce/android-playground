package com.example.popularmovies;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.slider.Slider;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageView ivDetailPoster = findViewById(R.id.ivDetailPoster);
        TextView tvDetailTitle = findViewById(R.id.tvDetailTitle);
        TextView tvDetailRating = findViewById(R.id.tvDetailRating);
        TextView tvDetailDescription = findViewById(R.id.tvDetailDescription);

        Bundle bundle = getIntent().getExtras();

        String title = bundle.getString("title");
        Double rating = bundle.getDouble("rating");
        String overview = bundle.getString("overview");
        String poster = bundle.getString("poster");

        Picasso.get().load(poster).into(ivDetailPoster);
        tvDetailTitle.setText(title);
        tvDetailRating.setText(rating.toString());
        tvDetailDescription.setText(overview);

        Slider slider = findViewById(R.id.sldExample);
        slider.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(Slider slider, float value, boolean fromUser) {
                tvDetailRating.setText(String.valueOf(value));
            }
        });

        SeekBar seekBar = findViewById(R.id.sbExample);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvDetailRating.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        Spinner spinner = findViewById(R.id.spnExample);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.planets_array,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }
}