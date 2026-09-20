package com.example.studentcourseregistration.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentcourseregistration.R;
import com.example.studentcourseregistration.models.Student;

import java.util.ArrayList;
import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ViewHolder> {

    public interface OnStudentClickListener {
        void onRequestDeleteStudentClicked(int id);
        void onRequestUpdateStudentClicked(Student student);
    }

    private final OnStudentClickListener listener;

    public StudentAdapter(OnStudentClickListener listener) {
        this.listener = listener;
    }
    List<Student> students = new ArrayList<>();

    public void setStudents(List<Student> newStudents) {
        students.clear();

        if (newStudents != null) {
            students.addAll(newStudents);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public StudentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_student, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Student student = students.get(position);
        holder.bind(student);
    }


    @Override
    public int getItemCount() {
        return students.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvId, tvStudentName, tvStudentEmail;
        private final Button btnUpdate, btnDelete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvId = itemView.findViewById(R.id.tvId);
            tvStudentName = itemView.findViewById(R.id.tvStudentName);
            tvStudentEmail = itemView.findViewById(R.id.tvStudentEmail);

            btnUpdate = itemView.findViewById(R.id.btnUpdate);

            btnUpdate.setOnClickListener(v -> {
                int position = getBindingAdapterPosition();
                if (position !=RecyclerView.NO_POSITION && listener != null){
                    listener.onRequestUpdateStudentClicked(students.get(position));
                }
            });

            btnDelete = itemView.findViewById(R.id.btnDelete);
            btnDelete.setOnClickListener(v -> {
                int position = getBindingAdapterPosition();
                if (position != RecyclerView.NO_POSITION && listener != null) {
                    listener.onRequestDeleteStudentClicked(students.get(position).getId());
                }
            });
        }


        public void bind(Student student) {
            tvId.setText(String.valueOf(student.getId()));
            tvStudentName.setText(student.getStudentName());
            tvStudentEmail.setText(student.getEmail());
        }
    }
}
