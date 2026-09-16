package com.example.movemate.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.movemate.R;

public class UpdateProfileFragment extends Fragment {

    private static final String PROFILE_PREFS = "profile_preferences";
    private static final String KEY_NAME = "name";
    private static final String KEY_CITY = "city";
    private static final String DEFAULT_CITY = "New Westminster";
    private static final String KEY_PREFERRED_ACTIVITY = "preferred_activity";
    private static final String KEY_WEEKLY_GOAL_PREFIX = "weekly_goal_";
    private static final String KEY_DISTANCE_UNIT = "distance_unit";

    public interface UpdateProfileFragmentListener {
        void onSaveProfileRequested();
    }

    private UpdateProfileFragmentListener listener;
    private Button btnSaveProfile;
    private EditText edtName;
    private EditText edtCity;
    private Spinner spnPreferredActivity;
    private EditText edtRunningGoalMinutes;
    private EditText edtWalkingGoalMinutes;
    private EditText edtBikingGoalMinutes;
    private EditText edtSwimmingGoalMinutes;
    private EditText edtStandUpGoalMinutes;
    private Spinner spnDistanceUnit;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_update_profile, container, false);
        btnSaveProfile = view.findViewById(R.id.btnSaveProfile);
        edtName = view.findViewById(R.id.edtName);
        edtCity = view.findViewById(R.id.edtCity);
        spnPreferredActivity = view.findViewById(R.id.spnPreferredActivity);
        edtRunningGoalMinutes = view.findViewById(R.id.edtRunningGoalMinutes);
        edtWalkingGoalMinutes = view.findViewById(R.id.edtWalkingGoalMinutes);
        edtBikingGoalMinutes = view.findViewById(R.id.edtBikingGoalMinutes);
        edtSwimmingGoalMinutes = view.findViewById(R.id.edtSwimmingGoalMinutes);
        edtStandUpGoalMinutes = view.findViewById(R.id.edtStandUpGoalMinutes);
        spnDistanceUnit = view.findViewById(R.id.spnDistanceUnit);

        loadProfile();

        btnSaveProfile.setOnClickListener(v->{
            saveProfile();
        });

        return view;
    }

    private void loadProfile() {
        SharedPreferences preferences = requireContext().getSharedPreferences(PROFILE_PREFS, Context.MODE_PRIVATE);

        edtName.setText(preferences.getString(KEY_NAME, "Alex"));
        edtCity.setText(preferences.getString(KEY_CITY, DEFAULT_CITY));
        edtRunningGoalMinutes.setText(preferences.getString(getWeeklyGoalKey("Running"), "210"));
        edtWalkingGoalMinutes.setText(preferences.getString(getWeeklyGoalKey("Walking"), "210"));
        edtBikingGoalMinutes.setText(preferences.getString(getWeeklyGoalKey("Biking"), "210"));
        edtSwimmingGoalMinutes.setText(preferences.getString(getWeeklyGoalKey("Swimming"), "210"));
        edtStandUpGoalMinutes.setText(preferences.getString(getWeeklyGoalKey("StandUp"), "210"));
        setSpinnerValue(spnPreferredActivity, preferences.getString(KEY_PREFERRED_ACTIVITY, "Running"));
        setSpinnerValue(spnDistanceUnit, preferences.getString(KEY_DISTANCE_UNIT, "km"));
    }

    private void saveProfile() {
        SharedPreferences preferences = requireContext().getSharedPreferences(PROFILE_PREFS, Context.MODE_PRIVATE);

        preferences.edit()
                .putString(KEY_NAME, edtName.getText().toString().trim())
                .putString(KEY_CITY, edtCity.getText().toString().trim())
                .putString(KEY_PREFERRED_ACTIVITY, spnPreferredActivity.getSelectedItem().toString())
                .putString(getWeeklyGoalKey("Running"), edtRunningGoalMinutes.getText().toString().trim())
                .putString(getWeeklyGoalKey("Walking"), edtWalkingGoalMinutes.getText().toString().trim())
                .putString(getWeeklyGoalKey("Biking"), edtBikingGoalMinutes.getText().toString().trim())
                .putString(getWeeklyGoalKey("Swimming"), edtSwimmingGoalMinutes.getText().toString().trim())
                .putString(getWeeklyGoalKey("StandUp"), edtStandUpGoalMinutes.getText().toString().trim())
                .putString(KEY_DISTANCE_UNIT, spnDistanceUnit.getSelectedItem().toString())
                .apply();

        if (listener != null) {
            listener.onSaveProfileRequested();
        }
    }

    private String getWeeklyGoalKey(String activity) {
        return KEY_WEEKLY_GOAL_PREFIX + activity.toLowerCase(java.util.Locale.ROOT);
    }

    private void setSpinnerValue(Spinner spinner, String value) {
        for (int index = 0; index < spinner.getCount(); index++) {
            if (value.equals(spinner.getItemAtPosition(index).toString())) {
                spinner.setSelection(index);
                return;
            }
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof UpdateProfileFragmentListener) {
            listener = (UpdateProfileFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement UpdateProfileFragmentListener.");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
}
