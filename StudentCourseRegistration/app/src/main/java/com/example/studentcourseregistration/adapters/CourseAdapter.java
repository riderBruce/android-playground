package com.example.studentcourseregistration.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentcourseregistration.R;
import com.example.studentcourseregistration.models.Course;
import com.example.studentcourseregistration.models.CourseEnrollmentData;

import java.util.ArrayList;
import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder> {

    public interface OnCourseClickListener {
        void onRequestDeleteCourseClicked(int id);
        void onRequestUpdateCourseClicked(Course course);
    }

    private final OnCourseClickListener listener;

    public CourseAdapter(OnCourseClickListener listener) {
        this.listener = listener;
    }
    List<CourseEnrollmentData> courses = new ArrayList<>();

    public void setCourses(List<CourseEnrollmentData> newCourses) {
        courses.clear();

        if (newCourses != null) {
            courses.addAll(newCourses);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CourseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_course, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CourseEnrollmentData course = courses.get(position);
        holder.bind(course);
    }


    @Override
    public int getItemCount() {
        return courses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvId, tvCourseName, tvStudentName, tvStudentEmail;
        private final Button btnUpdate, btnDelete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvId = itemView.findViewById(R.id.tvId);
            tvCourseName = itemView.findViewById(R.id.tvCourseName);
            tvStudentName = itemView.findViewById(R.id.tvStudentName);
            tvStudentEmail = itemView.findViewById(R.id.tvStudentEmail);

            btnUpdate = itemView.findViewById(R.id.btnUpdate);

            btnUpdate.setOnClickListener(v -> {
                int position = getBindingAdapterPosition();
                if (position !=RecyclerView.NO_POSITION && listener != null){
                    CourseEnrollmentData enrollment = courses.get(position);
                    listener.onRequestUpdateCourseClicked(new Course(
                            enrollment.getCourseId(), enrollment.getCourseName(),
                            enrollment.getStudentId()));
                }
            });

            btnDelete = itemView.findViewById(R.id.btnDelete);
            btnDelete.setOnClickListener(v -> {
                int position = getBindingAdapterPosition();
                if (position != RecyclerView.NO_POSITION && listener != null) {
                    listener.onRequestDeleteCourseClicked(courses.get(position).getCourseId());
                }
            });
        }


        public void bind(CourseEnrollmentData course) {
            tvId.setText(String.valueOf(course.getCourseId()));
            tvCourseName.setText(course.getCourseName());
            tvStudentName.setText(course.getStudentName());
            tvStudentEmail.setText(course.getStudentEmail());
        }
    }
}

