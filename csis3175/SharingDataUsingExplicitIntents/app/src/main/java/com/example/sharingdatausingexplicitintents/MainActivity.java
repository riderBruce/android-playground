package com.example.sharingdatausingexplicitintents;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    EditText nameInput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        nameInput = findViewById(R.id.edt_Name);
        Button submitBtn = findViewById(R.id.btn_submit);

//        submitBtn.setOnClickListener(this::showOnSecondActivity1);
        submitBtn.setOnClickListener(this::showOnSecondActivity2);
    }

    private void showOnSecondActivity2(View view) {
        Intent intent = new Intent(MainActivity.this, SecondActivity.class);
        intent.putExtra("name", nameInput.getText().toString());
        startActivity(intent);
    }

    private void showOnSecondActivity1(View view) {
        Intent intent = new Intent(MainActivity.this, SecondActivity.class);
        Bundle b = new Bundle();
        b.putString("name", nameInput.getText().toString());
        intent.putExtras(b);

        startActivity(intent);
    }
}