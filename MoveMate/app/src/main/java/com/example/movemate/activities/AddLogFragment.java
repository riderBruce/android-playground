package com.example.movemate.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.movemate.R;
import com.example.movemate.models.LogRecord;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class AddLogFragment extends Fragment {

    private static final String LOG_DEFAULTS_PREFS = "log_defaults_preferences";
    private static final String KEY_TYPE = "type";
    private static final String KEY_DURATION = "duration";
    private static final String KEY_DISTANCE = "distance";
    private static final String KEY_INTENSITY = "intensity";

    Spinner spnType, spnDuration,spnDistance, spnIntensity;
    EditText edtNote;
    CalendarView cldDate;
    private Button btnSaveLog;
    private long selectedDate;

    private AddLogFragmentListener listener;

    public interface AddLogFragmentListener {
        void onSaveLogRequested(LogRecord record);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_log, container, false);
        spnType = view.findViewById(R.id.spnType);
        spnDuration = view.findViewById(R.id.spnDuration);
        spnDistance = view.findViewById(R.id.spnDistance);
        spnIntensity = view.findViewById(R.id.spnIntensity);
        edtNote = view.findViewById(R.id.edtNote);
        cldDate = view.findViewById(R.id.cldDate);
        btnSaveLog = view.findViewById(R.id.btnSaveLog);
        selectedDate = cldDate.getDate();
        cldDate.setOnDateChangeListener((calendarView, year, month, dayOfMonth) -> {
            Calendar selectedCalendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
            selectedCalendar.clear();
            selectedCalendar.set(year, month, dayOfMonth);
            selectedDate = selectedCalendar.getTimeInMillis();
        });

        loadLastInput();
        btnSaveLog.setOnClickListener(v->saveLog());

        return view;
    }

    private void saveLog() {
        if (spnType.getSelectedItem() == null
                || spnDuration.getSelectedItem() == null
                || spnDistance.getSelectedItem() == null
                || spnIntensity.getSelectedItem() == null) {
            Toast.makeText(
                    requireContext(),
                    "Please complete all movement fields",
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }

        try {
            String type = spnType.getSelectedItem().toString();
            int duration = Integer.parseInt(
                    spnDuration.getSelectedItem().toString()
            );
            String distanceValue = spnDistance.getSelectedItem().toString();
            double distance = Double.parseDouble(
                    distanceValue
            );
            String intensity = spnIntensity.getSelectedItem().toString();
            String note = edtNote.getText().toString().trim();

            SimpleDateFormat dateFormat = new SimpleDateFormat(
                    "yyyy-MM-dd",
                    Locale.US
            );
            dateFormat.setTimeZone(TimeZone.getDefault());
            String date = dateFormat.format(new Date(selectedDate));

            LogRecord record = new LogRecord(
                    type,
                    date,
                    duration,
                    distance,
                    intensity,
                    note
            );

            saveLastInput(type, duration, distanceValue, intensity);

            if (listener != null) {
                listener.onSaveLogRequested(record);
            } else {
                Toast.makeText(requireContext(), "AddLogFragmentListener should be implemented", Toast.LENGTH_SHORT).show();
            }
        } catch (NumberFormatException e) {
            Toast.makeText(requireContext(), "Duration and distance must be numeric", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadLastInput() {
        SharedPreferences preferences = requireContext().getSharedPreferences(
                LOG_DEFAULTS_PREFS,
                Context.MODE_PRIVATE
        );

        setSpinnerValue(spnType, preferences.getString(KEY_TYPE, "Running"));
        setSpinnerValue(spnDuration, preferences.getString(KEY_DURATION, "30"));
        setSpinnerValue(spnDistance, preferences.getString(KEY_DISTANCE, "5"));
        setSpinnerValue(spnIntensity, preferences.getString(KEY_INTENSITY, "Medium"));
    }

    private void saveLastInput(String type, int duration, String distance, String intensity) {
        requireContext().getSharedPreferences(
                LOG_DEFAULTS_PREFS,
                Context.MODE_PRIVATE
        ).edit()
                .putString(KEY_TYPE, type)
                .putString(KEY_DURATION, String.valueOf(duration))
                .putString(KEY_DISTANCE, String.valueOf(distance))
                .putString(KEY_INTENSITY, intensity)
                .apply();
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

        if (context instanceof AddLogFragmentListener) {
            listener = (AddLogFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement AddLogFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
}
