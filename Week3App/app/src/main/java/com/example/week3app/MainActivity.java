package com.example.week3app;

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
import androidx.room.Room;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    AppDatabase db;
    ProductDao productDao;

    EditText productName;
    Spinner sp;
    TextView textView;
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

        db = Room.databaseBuilder(this,
                AppDatabase.class, "Inventory.db")
                .allowMainThreadQueries().build();

        productDao = db.productDao();
        productName = findViewById(R.id.inputProdName);
        sp = findViewById(R.id.spGroup);
        textView = findViewById(R.id.txtOutput);
        Button btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(view -> saveRecord());
    }

    public void viewRecord() {
        List<Product> listProducts = productDao.getAllProducts();
        StringBuilder sb = new StringBuilder();
        for(Product product : listProducts) {
            sb.append(product.getProductId() + " " + product.getProdName()
                    + " " + product.getQuantity() + "\n");
        }
        textView.setText(sb.toString());
    }

    public void saveRecord() {
        Product product = new Product();
        String name = productName.getText().toString();
        int quantity = Integer.parseInt(sp.getSelectedItem().toString());
        product.setProdName(name);
        product.setQuantity(quantity);

        try {
            productDao.addProduct(product);
            Toast.makeText(this, "Data saved", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
