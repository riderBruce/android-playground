package com.example.roompractice;

import android.os.Bundle;
import android.view.View;
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
    
    EditText edtProductName;
    Spinner spnProductQuantity;
    TextView tvResult;
    ProductDao productDao;

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
        
        edtProductName = findViewById(R.id.edtProductName);
        spnProductQuantity = findViewById(R.id.spnProductQuantity);
        tvResult = findViewById(R.id.tvResult);
        
        AppDatabase db = Room.databaseBuilder(
                this,
                AppDatabase.class,
                "inventory.db"
        ).allowMainThreadQueries().build();
        
        productDao = db.productDao();
        
        findViewById(R.id.btnAdd).setOnClickListener(this::addProduct);
        findViewById(R.id.btnView).setOnClickListener(this::viewProduct);
    }

    private void viewProduct(View view) {
        List<Product> products = productDao.getAllProducts();

        StringBuilder sb = new StringBuilder();
        for (var p : products) {
            sb.append(String.format("%d, %s, %d%n", p.getProductId(), p.getProductName(), p.getQuantity()));
        }
        tvResult.setText(sb);
    }

    private void addProduct(View view) {
        Product product = new Product();
        product.setProductName(edtProductName.getText().toString());
        product.setQuantity(Integer.parseInt(spnProductQuantity.getSelectedItem().toString()));

        try {
            productDao.addProduct(product);
            Toast.makeText(this, "Added Successfully", Toast.LENGTH_SHORT).show();
        } catch(Exception e) {
            Toast.makeText(this, "Failed: "+ e, Toast.LENGTH_SHORT).show();
        }
    }
}