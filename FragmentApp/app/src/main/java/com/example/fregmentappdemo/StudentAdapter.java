package com.example.fregmentappdemo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fregmentappdemo.databinding.ItemStudentBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ViewHolder> {
    List<Student> students = new ArrayList<>();

    public interface OnStudentClickListener {
        void onShowStudentDetails(Student student);
    }

    private final OnStudentClickListener listener;
    public StudentAdapter(OnStudentClickListener listener) {
        this.listener = listener;
    }

    public void setStudents(List<Student> newStudents) {
        students.clear();
        this.students = newStudents;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public StudentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_student, parent, false);
//        return new ViewHolder(view);
        ItemStudentBinding binding = ItemStudentBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentAdapter.ViewHolder holder, int position) {
        Student student = students.get(position);
        holder.bind(student);
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemStudentBinding binding;
        public ViewHolder(@NonNull ItemStudentBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.cardStudent.setOnClickListener(v->{
                int position = getBindingAdapterPosition();

                if (position != RecyclerView.NO_POSITION && listener != null) {
                    listener.onShowStudentDetails(students.get(position));
                }
            });
        }

        public void bind(Student student) {
            binding.tvCardId.setText(String.valueOf(student.getId()));
            binding.tvCardName.setText(student.getName());
        }
    }
}
