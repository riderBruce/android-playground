package com.example.movemate.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movemate.R;
import com.example.movemate.models.LogRecord;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    public interface OnLogClickListener {
        void onLogClicked(LogRecord record);
    }

    private final List<LogRecord> records = new ArrayList<>();
    private final OnLogClickListener listener;

    public RecyclerViewAdapter(OnLogClickListener listener){
        this.listener = listener;
    }

    public void setRecords(List<LogRecord> newRecords) {
        records.clear();

        if (newRecords != null) {
            records.addAll(newRecords);
        }

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_move_log, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
        LogRecord record = records.get(position);
        holder.bind(record);
    }

    @Override
    public int getItemCount() {
        return records.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvLogType;
        private final TextView tvLogIntensity;
        private final TextView tvLogDate;
        private final TextView tvLogDuration;
        private final TextView tvLogDistance;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvLogType = itemView.findViewById(R.id.tvLogType);
            tvLogIntensity = itemView.findViewById(R.id.tvLogIntensity);
            tvLogDate = itemView.findViewById(R.id.tvLogDate);
            tvLogDuration = itemView.findViewById(R.id.tvLogDuration);
            tvLogDistance = itemView.findViewById(R.id.tvLogDistance);

            itemView.setOnClickListener(v-> {
             int position = getBindingAdapterPosition();

             if (position != RecyclerView.NO_POSITION && listener != null) {
                 listener.onLogClicked(records.get(position));
             }
            });
        }

        public void bind(LogRecord record) {
            tvLogType.setText(record.getType());
            tvLogIntensity.setText(record.getIntensity());
            tvLogDate.setText(record.getDate());
            tvLogDuration.setText(String.format(Locale.getDefault(), "Duration: %d min", record.getDurationMinute()));
            tvLogDistance.setText(String.format(Locale.getDefault(), "Distance: %.1f km", record.getDistanceKm()));
        }
    }
}
