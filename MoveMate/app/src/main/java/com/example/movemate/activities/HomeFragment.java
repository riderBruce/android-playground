package com.example.movemate.activities;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.movemate.R;
import com.example.movemate.models.LogRecord;
import com.example.movemate.utils.WeeklySummaryCalculator;

import java.util.List;

public class HomeFragment extends Fragment {
    interface HomeFragmentListener {
        void onQuickSearchRequested(String activity);
        List<LogRecord> onRequestLogRecords();
    }

    private HomeFragmentListener listener;
    private HomeWeatherController weatherController;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        Context context = requireContext();
        WeeklySummaryCalculator calculator = new WeeklySummaryCalculator(
                context.getSharedPreferences("profile_preferences", Context.MODE_PRIVATE));
        new WeeklySummaryRenderer().render(view, calculator.calculate(listener.onRequestLogRecords()));
        weatherController = new HomeWeatherController(view, context);
        weatherController.load();

        view.findViewById(R.id.imgQuickWalking).setOnClickListener(v -> requestActivity("Walking"));
        view.findViewById(R.id.imgQuickRunning).setOnClickListener(v -> requestActivity("Running"));
        view.findViewById(R.id.imgQuickBiking).setOnClickListener(v -> requestActivity("Biking"));
        view.findViewById(R.id.imgQuickSwimming).setOnClickListener(v -> requestActivity("Swimming"));
        view.findViewById(R.id.imgQuickStandUp).setOnClickListener(v -> requestActivity("StandUp"));
        return view;
    }

    private void requestActivity(String activity) {
        if (listener != null) listener.onQuickSearchRequested(activity);
    }

    @Override
    public void onDestroyView() {
        if (weatherController != null) {
            weatherController.release();
            weatherController = null;
        }
        super.onDestroyView();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof HomeFragmentListener) listener = (HomeFragmentListener) context;
        else throw new RuntimeException(context + " must implement HomeFragmentListener");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
}
