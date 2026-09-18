package com.example.nextmovie;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MoviesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;

    private List<Movie> movies;
    private RequestQueue requestQueue;

    private String keyword;
    private int rating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_movies);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        movies = new ArrayList<>();

        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new RecyclerAdapter(this, movies);
        recyclerView.setAdapter(adapter);

        requestQueue = VolleySingleton.getInstatnce(this).getRequestQueue();

        keyword = getIntent().getStringExtra("keyword");
        rating = getIntent().getIntExtra("rating", 0);

        searchMovies();
    }

    private String getApiKey() {
        String apiKey = "7a3ddf6204f155b48c281b2a192f4fee";
        return "https://api.themoviedb.org/3/movie/popular?api_key="
                + apiKey
                + "&language=en-US&page=1";
    }

    private void searchMovies() {
        String url = getApiKey();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, (response) -> {
            try {
                JSONArray results = response.getJSONArray("results");
                for (int i = 0; i < results.length(); i++) {
                    JSONObject movieObject = results.getJSONObject(i);
                    String title = movieObject.getString("title");
                    String posterUrl = "https://image.tmdb.org/t/p/w500" + movieObject.getString("poster_path");
                    double rating = movieObject.getDouble("vote_average");
                    String description = movieObject.getString("overview");

                    if (title.toLowerCase().contains(keyword.toLowerCase()) && rating >= this.rating){
                        movies.add(new Movie(title, posterUrl, rating, description));
                    }
                }
                adapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, (error) -> {
            error.printStackTrace();
        });
        requestQueue.add(request);
    }
}