package com.example.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Movie> movies;

    public RecyclerAdapter(Context context, ArrayList<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.ViewHolder holder, int position) {
        Movie movie = movies.get(position);
        holder.tvTitle.setText(movie.getTitle());
        holder.tvMovieRating.setText(movie.getRating().toString());
        holder.tvOverview.setText(movie.getOverview());
        Picasso.get().load(movie.getPoster()).into(holder.ivMovieImage);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailActivity.class);

                Bundle bundle = new Bundle();
                bundle.putString("title", movie.getTitle());
                bundle.putDouble("rating", movie.getRating());
                bundle.putString("overview", movie.getOverview());
                bundle.putString("poster", movie.getPoster());
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivMovieImage;
        TextView tvTitle, tvMovieRating, tvOverview;
        CardView cardView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivMovieImage = itemView.findViewById(R.id.ivMovieImage);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvMovieRating = itemView.findViewById(R.id.tvMovieRating);
            tvOverview = itemView.findViewById(R.id.tvOverview);
            cardView = itemView.findViewById(R.id.card_view);
        }
    }
}
