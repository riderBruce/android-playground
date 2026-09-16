package com.example.movemate.activities;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movemate.R;
import com.example.movemate.adapters.RecyclerViewAdapter;
import com.example.movemate.models.LogRecord;

import java.util.ArrayList;
import java.util.List;

public class LogListFragment extends Fragment {

    private static final String ARG_ACTIVITY_FILTER = "activity_filter";

    RecyclerView recyclerView;
    RecyclerView.LayoutManager manager;
    RecyclerViewAdapter adapter;
    LogListFragmentListener listener;

    interface LogListFragmentListener {
        List<LogRecord> onRequestLogRecords();
        void onViewDetailRequested(int id);
    }

    public static LogListFragment newInstance(String activityFilter) {
        LogListFragment fragment = new LogListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ACTIVITY_FILTER, activityFilter);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_log_list, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);

        adapter = new RecyclerViewAdapter(record -> {
            if (listener != null) {
                listener.onViewDetailRequested(record.getId());
            }
        });

        recyclerView.setAdapter(adapter);

        if (listener != null) {
            List<LogRecord> records = listener.onRequestLogRecords();
            Bundle arguments = getArguments();
            String activityFilter = arguments == null
                    ? null
                    : arguments.getString(ARG_ACTIVITY_FILTER);

            if (activityFilter != null) {
                List<LogRecord> filteredRecords = new ArrayList<>();
                for (LogRecord record : records) {
                    if (activityFilter.equalsIgnoreCase(record.getType())) {
                        filteredRecords.add(record);
                    }
                }
                records = filteredRecords;
            }

            adapter.setRecords(records);
        }
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof LogListFragmentListener) {
            listener = (LogListFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement LogListFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
}
