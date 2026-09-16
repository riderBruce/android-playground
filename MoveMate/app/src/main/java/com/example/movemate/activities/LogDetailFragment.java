package com.example.movemate.activities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.movemate.R;
import com.example.movemate.models.LogRecord;

import java.util.Locale;

public class LogDetailFragment extends Fragment {

    public static LogDetailFragment newInstance(LogRecord record) {
        LogDetailFragment fragment = new LogDetailFragment();

        Bundle args = new Bundle();
        args.putString("type", record.getType());
        args.putString("date", record.getDate());
        args.putInt("duration", record.getDurationMinute());
        args.putDouble("distance", record.getDistanceKm());
        args.putString("intensity", record.getIntensity());
        args.putString("note", record.getNote());
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_log_detail, container, false);

        TextView tvDetailType = view.findViewById(R.id.tvDetailType);
        TextView tvDetailDate = view.findViewById(R.id.tvDetailDate);
        TextView tvDetailDuration = view.findViewById(R.id.tvDetailDuration);
        TextView tvDetailDistance = view.findViewById(R.id.tvDetailDistance);
        TextView tvDetailIntensity = view.findViewById(R.id.tvDetailIntensity);
        TextView tvDetailNote = view.findViewById(R.id.tvDetailNote);
        ImageView ivActivity = view.findViewById(R.id.ivActivity);

        Bundle args = requireArguments();
        String type = args.getString("type");
        tvDetailType.setText(String.format(Locale.getDefault(), "Activity: %s", type));
        setActivityImage(ivActivity, type);

        tvDetailDate.setText(String.format(Locale.getDefault(), "Date: %s", args.getString("date")));
        tvDetailDuration.setText(String.format(Locale.getDefault(), "Duration: %d min", args.getInt("duration")));
        tvDetailDistance.setText(String.format(Locale.getDefault(), "Distance: %.1f km", args.getDouble("distance")));
        tvDetailIntensity.setText(String.format(Locale.getDefault(), "Intensity: %s", args.getString("intensity")));
        tvDetailNote.setText(args.getString("note"));

        Button btnBack = view.findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> requireActivity().getSupportFragmentManager().popBackStack());

        return view;
    }

    private void setActivityImage(ImageView imageView, String type) {
        String imageName = type == null
                ? "biking"
                : type.toLowerCase(Locale.ROOT).replace(" ", "_");
        int imageId = getResources().getIdentifier(imageName, "drawable", requireContext().getPackageName());
        imageView.setImageResource(imageId != 0 ? imageId : R.drawable.biking);
        imageView.setContentDescription(type + " illustration");
    }

}
