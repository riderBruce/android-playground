package com.example.passdatafromactivitytofragmentusingbundleobject;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {

    private FragmentManager manager;
    private EditText edtFirstNumber, edtSecondNumber;

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

        manager = getSupportFragmentManager();

        edtFirstNumber = findViewById(R.id.edtFirstNumber);
        edtSecondNumber = findViewById(R.id.edtSecondNumber);

    }

    public void sendDataToFragmentA(View view) {

        int firstNumber = Integer.valueOf(edtFirstNumber.getText().toString());
        int secondNumber = Integer.valueOf(edtSecondNumber.getText().toString());

        // using Bundle Object to send data from Activity to a Fragment
        Bundle bundle = new Bundle();
        bundle.putInt("first_number", firstNumber);
        bundle.putInt("second_number", secondNumber);

        FragmentA fragmentA = new FragmentA();
        fragmentA.setArguments(bundle);

        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.containerFragmentA, fragmentA, "fregA");
        transaction.commit();
    }
}