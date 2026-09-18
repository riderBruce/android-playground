package com.example.nextmovie;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    private ImageView ivDetailPoster;
    private TextView tvDetailTitle, tvDetailRating, tvDetailDescription;

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

        String title = getIntent().getStringExtra("title");
        String posterUrl = getIntent().getStringExtra("posterUrl");
        double rating = getIntent().getDoubleExtra("rating", 0);
        String description = getIntent().getStringExtra("description");

        ivDetailPoster = findViewById(R.id.ivDetailPoster);
        tvDetailTitle = findViewById(R.id.tvDetailTitle);
        tvDetailRating = findViewById(R.id.tvDetailRating);
        tvDetailDescription = findViewById(R.id.tvDetailDescription);

        Picasso.get().load(posterUrl).into(ivDetailPoster);
        tvDetailTitle.setText(title);
        tvDetailRating.setText(String.valueOf(rating));
        tvDetailDescription.setText(description);

    }
}