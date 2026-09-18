package com.example.sharedpreferencesactivitylevel;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    EditText edtName, edtMajor, edtStudentId;
    TextView tvName, tvMajor, tvStudentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.pageLayout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        edtName = findViewById(R.id.edtName);
        edtMajor = findViewById(R.id.edtMajor);
        edtStudentId = findViewById(R.id.edtStudentId);

        tvName = findViewById(R.id.tvName);
        tvMajor = findViewById(R.id.tvMajor);
        tvStudentId = findViewById(R.id.tvStudentId);
    }

    public void saveData(View view) {
        SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("name", edtName.getText().toString());
        editor.putString("major", edtMajor.getText().toString());
        editor.putString("studentId", edtStudentId.getText().toString());

        editor.apply(); // asynchronously
        // editor.commit(); // return a boolean and synchronously
    }

    public void loadData(View view) {
        SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);

        String name = sharedPreferences.getString("name", "Name is not available!");
        String major = sharedPreferences.getString("major", "Major is not available!");
        String studentId = sharedPreferences.getString("studentId", "Student id is not available!");

        tvName.setText(name);
        tvMajor.setText(major);
        tvStudentId.setText(studentId);
    }
}