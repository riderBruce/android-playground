package com.example.myfirstapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    private EditText edtName;
    private RadioGroup rdgGender;
    private Spinner spnCountry;
    private Button btnSave, btnView, btnWeb, btnSaveToFile, btnViewFromFile, btnFavourite;

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

        edtName = findViewById(R.id.edtName);

        rdgGender = findViewById(R.id.rdgGender);

        spnCountry = findViewById(R.id.spnCountry);

        Country[] countries = Country.values();
        ArrayAdapter<Country> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                countries
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnCountry.setAdapter(adapter);


        btnSave = findViewById(R.id.btnSave);
        btnView = findViewById(R.id.btnView);
        btnWeb = findViewById(R.id.btnWeb);
        btnSaveToFile = findViewById(R.id.btnSaveToFile);
        btnViewFromFile = findViewById(R.id.btnViewFromFile);
        btnFavourite = findViewById(R.id.btnViewFavourite);

        btnSave.setOnClickListener(this::saveSharedPreferences);
        btnView.setOnClickListener(this::openSecondActivity);
        btnWeb.setOnClickListener(this::openWebView);
        btnSaveToFile.setOnClickListener(this::saveUserToFile);
        btnViewFromFile.setOnClickListener(this::viewUserFromFile);
        btnFavourite.setOnClickListener(this::viewFavourite);
    }

    private void viewFavourite(View view) {
        Intent intent = new Intent(MainActivity.this, FavouritePlacesActivity.class);
        startActivity(intent);
    }

    private void viewUserFromFile(View view) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(openFileInput("user.txt")))){
            String data = reader.readLine();
            Toast.makeText(this, data, Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(this, "File Read Error", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveUserToFile(View view) {
        User user = createUser();

        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(openFileOutput("user.txt", MODE_PRIVATE)))) {
            writer.write(user.toString());
            Toast.makeText(this, "Saved: " + user.toString(), Toast.LENGTH_SHORT).show();
        } catch (IOException e){
            Toast.makeText(this, "File Write Error", Toast.LENGTH_SHORT).show();
        }
    }

    private void openWebView(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://douglascollege.ca"));
        startActivity(intent);
    }

    private void openSecondActivity(View view) {
        Intent intent = new Intent(MainActivity.this, SecondActivity.class);
        startActivity(intent);
    }

    private void saveSharedPreferences(View view) {
        SharedPreferences sharedPreferences = getSharedPreferences("user_profile", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        User user = createUser();

        editor.putString("name", user.getName());
        editor.putString("gender", user.getGender().toString());
        editor.putString("country", user.getCountry().toString());
        editor.apply();
    }

    private User createUser() {
        String name = edtName.getText().toString();

        Gender gender;
        if (rdgGender.getCheckedRadioButtonId() == R.id.rdbMale) {
            gender = Gender.MALE;
        } else if (rdgGender.getCheckedRadioButtonId() == R.id.rdbFemale) {
            gender = Gender.FEMALE;
        } else {
            gender = Gender.OTHER;
        }

        Country country = (Country) spnCountry.getSelectedItem();

        return new User(name, gender, country);
    }
}