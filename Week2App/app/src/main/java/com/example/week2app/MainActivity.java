package com.example.week2app;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper;
    EditText edtProductName;
    Spinner spnGroup;
    TextView tvData;

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

        databaseHelper = new DatabaseHelper(this);
        edtProductName = findViewById(R.id.edtProductName);
        spnGroup = findViewById(R.id.spnGroup);
        Button btnSave = findViewById(R.id.btnSave);

        btnSave.setOnClickListener(v -> save());

        tvData = findViewById(R.id.tvData);
        Button btnView = findViewById(R.id.btnView);
        btnView.setOnClickListener(v -> view());

        Button btnDelete = findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(v -> deleteRec());

        Button btnUpdate = findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(v -> updateRec());
    }

    private void updateRec() {
        boolean isUpdated = databaseHelper.updateData(5, "new Item");
        if (isUpdated) {
            Toast.makeText(this, "updated", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "fail to update", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteRec() {
        boolean isDeleted = databaseHelper.deleteData(3);
        if (isDeleted) {
            Toast.makeText(this, "deleted", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "fail to delete", Toast.LENGTH_SHORT).show();
        }
    }

    private void view() {
        Cursor c = databaseHelper.viewData();
        StringBuilder str = new StringBuilder();
        while (c.moveToNext()){
            str.append(c.getInt(0) + " ");
            str.append(c.getString(1) + " ");
            str.append(c.getInt(2) + "\n");
        }
        tvData.setText(str);
        c.close();
    }

    private void save() {
        boolean isInserted;
        String prodName = edtProductName.getText().toString();
        int quantity = Integer.parseInt(spnGroup.getSelectedItem().toString());
        isInserted = databaseHelper.saveData(prodName, quantity);
        if (isInserted) {
            Toast.makeText(this, R.string.saved, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, R.string.not_saved, Toast.LENGTH_SHORT).show();
        }
    }
}