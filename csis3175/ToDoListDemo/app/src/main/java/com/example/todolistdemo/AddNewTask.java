package com.example.todolistdemo;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.todolistdemo.model.TodoModel;
import com.example.todolistdemo.utils.DatabaseHelper;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class AddNewTask extends BottomSheetDialogFragment {
    public static final String TAG = "Add New Task";

    private EditText edtNewTask;
    private Button btnSave;
    private DatabaseHelper db;

    public static AddNewTask newInstance() {
        return new AddNewTask();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_new_task, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        edtNewTask = view.findViewById(R.id.edtNewTask);
        btnSave = view.findViewById(R.id.btnSave);

        db = new DatabaseHelper(getContext());
        boolean isUpdated;

        Bundle bundle = getArguments();
        if (bundle != null) {
            isUpdated = true;
            String task = bundle.getString("task");
            edtNewTask.setText(task);

            if (task.length() > 0) {
                btnSave.setEnabled(false);
            }
        } else {
            isUpdated = false;
        }

        edtNewTask.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable editable) {

            }

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equals("")){
                    btnSave.setEnabled(false);
                    btnSave.setBackgroundColor(Color.GRAY);
                } else {
                    btnSave.setEnabled(true);
                    btnSave.setBackgroundColor(getResources().getColor(R.color.color_primary));
                }
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = edtNewTask.getText().toString();

                if (isUpdated) {
                    db.updateTask(bundle.getInt("id"), text);
                } else {
                    TodoModel newTodo = new TodoModel();
                    newTodo.setTask(text);
                    newTodo.setStatus(0);
                    db.insertTask(newTodo);
                }

                dismiss();
            }
        });

    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        Activity activity = getActivity();
        if (activity instanceof OnDialogCloseListener) {
            ((OnDialogCloseListener) activity).onDialogClose(dialog);
        }
    }
}
