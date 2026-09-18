package com.example.todolistdemo.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolistdemo.AddNewTask;
import com.example.todolistdemo.MainActivity;
import com.example.todolistdemo.R;
import com.example.todolistdemo.model.TodoModel;
import com.example.todolistdemo.utils.DatabaseHelper;

import java.util.List;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.ViewHolder> {

    private List<TodoModel> todos;
    private MainActivity activity;
    private DatabaseHelper db;

    public TodoAdapter(MainActivity activity, DatabaseHelper db) {
        this.activity = activity;
        this.db = db;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoAdapter.ViewHolder holder, int position) {
        final TodoModel todo = todos.get(position);
        holder.checkBox.setText(todo.getTask());
        holder.checkBox.setChecked(todo.getStatus() != 0);

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(@NonNull CompoundButton compoundButton, boolean isChecked) {
                db.updateStatus(todo.getId(), isChecked ? 0 : 1);
            }
        });
    }

    public Context getContext() {
        return activity;
    }

    public void setTasks(List<TodoModel> todos) {
        this.todos = todos;
        notifyDataSetChanged();
    }

    public void deleteTask(int position) {
        TodoModel todo = todos.get(position);
        db.deleteTask(todo.getId());
        todos.remove(position);
        notifyItemRemoved(position);
    }

    public void editTask(int position) {
        TodoModel todo = todos.get(position);

        Bundle bundle = new Bundle();
        bundle.putInt("id", todo.getId());
        bundle.putString("task", todo.getTask());

        AddNewTask newTask = new AddNewTask();
        newTask.setArguments(bundle);
        newTask.show(activity.getSupportFragmentManager(), newTask.getTag());
    }

    @Override
    public int getItemCount() {
        return todos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBox;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.checkBox);
        }
    }
}
