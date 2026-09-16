package com.example.movemate.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.movemate.R;

public class ProfileFragment extends Fragment {

    private static final String PROFILE_PREFS = "profile_preferences";
    private static final String KEY_NAME = "name";
    private static final String KEY_CITY = "city";
    private static final String DEFAULT_CITY = "New Westminster";
    private static final String KEY_PREFERRED_ACTIVITY = "preferred_activity";
    private static final String KEY_WEEKLY_GOAL_PREFIX = "weekly_goal_";
    private static final String KEY_DISTANCE_UNIT = "distance_unit";

    // interface communication with MainActivity
    public interface ProfileFragmentListener {
        void onUpdateProfileRequested();
    }

    private ProfileFragmentListener listener;

    private Button btnUpdateUser;
    private ImageView imgAvatar;
    private TextView tvName;
    private TextView tvCity;
    private TextView tvPreferredActivity;
    private TextView tvRunningWeeklyGoal;
    private TextView tvWalkingWeeklyGoal;
    private TextView tvBikingWeeklyGoal;
    private TextView tvSwimmingWeeklyGoal;
    private TextView tvStandUpWeeklyGoal;
    private TextView tvDistanceUnit;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        btnUpdateUser = view.findViewById(R.id.btnUpdateProfile);
        imgAvatar = view.findViewById(R.id.imgAvatar);
        tvName = view.findViewById(R.id.tvName);
        tvCity = view.findViewById(R.id.tvCity);
        tvPreferredActivity = view.findViewById(R.id.tvPreferredActivity);
        tvRunningWeeklyGoal = view.findViewById(R.id.tvRunningWeeklyGoal);
        tvWalkingWeeklyGoal = view.findViewById(R.id.tvWalkingWeeklyGoal);
        tvBikingWeeklyGoal = view.findViewById(R.id.tvBikingWeeklyGoal);
        tvSwimmingWeeklyGoal = view.findViewById(R.id.tvSwimmingWeeklyGoal);
        tvStandUpWeeklyGoal = view.findViewById(R.id.tvStandUpWeeklyGoal);
        tvDistanceUnit = view.findViewById(R.id.tvDistanceUnit);

        btnUpdateUser.setOnClickListener(v -> listener.onUpdateProfileRequested());

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadProfile();
    }

    private void loadProfile() {
        if (getView() == null) {
            return;
        }

        SharedPreferences preferences = requireContext().getSharedPreferences(PROFILE_PREFS, Context.MODE_PRIVATE);
        String name = preferences.getString(KEY_NAME, "Alex");
        String city = preferences.getString(KEY_CITY, DEFAULT_CITY);
        String activity = preferences.getString(KEY_PREFERRED_ACTIVITY, "Running");
        String distanceUnit = preferences.getString(KEY_DISTANCE_UNIT, "km");

        tvName.setText(name);
        tvCity.setText(city);
        tvPreferredActivity.setText(activity);
        tvRunningWeeklyGoal.setText(formatGoal(preferences, "Running"));
        tvWalkingWeeklyGoal.setText(formatGoal(preferences, "Walking"));
        tvBikingWeeklyGoal.setText(formatGoal(preferences, "Biking"));
        tvSwimmingWeeklyGoal.setText(formatGoal(preferences, "Swimming"));
        tvStandUpWeeklyGoal.setText(formatGoal(preferences, "StandUp"));
        tvDistanceUnit.setText(distanceUnit);

        String imageName = activity.toLowerCase(Locale.ROOT).replace(" ", "");
        int imageId = getResources().getIdentifier(imageName, "drawable", requireContext().getPackageName());
        imgAvatar.setImageResource(imageId != 0 ? imageId : R.drawable.biking);
        imgAvatar.setContentDescription(activity + " avatar");
    }

    private String getWeeklyGoalKey(String activity) {
        return KEY_WEEKLY_GOAL_PREFIX + activity.toLowerCase(Locale.ROOT);
    }

    private String formatGoal(SharedPreferences preferences, String activity) {
        String displayActivity = "StandUp".equals(activity) ? "Stand-up" : activity;
        String goal = preferences.getString(getWeeklyGoalKey(activity), "210");
        return displayActivity + ": " + goal + " min/week";
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        // context : MainActivity, Parent Activity of Fragment
        if (context instanceof ProfileFragmentListener) {
            listener = (ProfileFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement ProfileFragemntListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
}
