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
    EditText prName;
    Spinner spinner;
    TextView textView;
    AppDatabase db;
    ProductDao productDao;
    OrderDao orderDao;

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
        //create the database
        db = Room.databaseBuilder(this,
                AppDatabase.class,
                "Inventory.db")
                .allowMainThreadQueries().
                build();
        //create a Dao
        productDao = db.productDao();
        orderDao = db.orderDao();
        prName = findViewById(R.id.inputProdName);
        spinner = findViewById(R.id.spGroup);
        textView = findViewById(R.id.txtOutput);
        Button btnSave = findViewById(R.id.btnSave);
        Button btnView = findViewById(R.id.btnView);
        Button btnDel = findViewById(R.id.btnDel);
        Button btnUpdate = findViewById(R.id.btnUpdate);
        Button btnAddOrder = findViewById(R.id.btnAddOrder);
        Button btnBoth = findViewById(R.id.btnBoth);
        btnSave.setOnClickListener(view -> saveRecord());
        btnView.setOnClickListener(view->viewRecord());
        btnDel.setOnClickListener(view ->deleteRec());
        btnUpdate.setOnClickListener(view -> updateRec());
        btnAddOrder.setOnClickListener(view -> addOrder());
        btnBoth.setOnClickListener(view -> showBoth());
    }

    private void showBoth() {
        List<Order> listProducts = orderDao.getAllOrders();
        StringBuilder stringBuilder = new StringBuilder();
        for(Order po : listProducts){
            stringBuilder.append("Id: "+ po.getOrderId() +
                    " name: " + po.getProID()
                    + " Q:" + po.getOrderQ() + "\n");
        }
//        textView.setText(stringBuilder.toString());
//
        List<ProductOrder> listProducts2 = orderDao.getAllProductOrders();
//        StringBuilder stringBuilder = new StringBuilder();
        for(ProductOrder po : listProducts2){
            stringBuilder.append("Id: "+ po.getOrderId() +
                    " name: " + po.getProdId()
                    + " Q:" + po.getOrderQuantity() + "\n");
        }
        textView.setText(stringBuilder.toString());
    }

    private void addOrder() {
        Order order = new Order();
        order.setProID(1);
        order.setOrderQ(12);

        try{
            orderDao.addOrder(order);
            Toast.makeText(this, "order saved", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Error " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    //----------------------------------
    public void updateRec(){

    }
    //----------------------------------
    public void deleteRec(){
        Product product = productDao.getProduct(1);
        if(product!=null)
        {
            productDao.deleteProduct(product.getProdID());
            Toast.makeText(this,"record deleted",Toast.LENGTH_LONG).show();
        }
        else
            Toast.makeText(this,"record not deleted",Toast.LENGTH_LONG).show();
    }
    //---------------------------------
    public void viewRecord(){
        List<Product> listProducts = productDao.getAllProducts();
        StringBuilder stringBuilder = new StringBuilder();
        for(Product product : listProducts){
            stringBuilder.append("Id: "+ product.getProdID() +
                    " name: " + product.getProdName()
                    + " Q:" + product.getQuantity() + "\n");
        }
        textView.setText(stringBuilder.toString());
    }
    //-------------------------------------------
    public void getOneProduct(){
        Product product = productDao.getProduct(1);
        textView.setText(product.getProdID() + " " + product.getProdName() + " "
        + product.getQuantity());
    }
    //-------------------------------------------
    public void saveRecord(){
        Product product = new Product();
        String name = prName.getText().toString();
        int quantity = Integer.parseInt(spinner.getSelectedItem().toString());
        product.setProdName(name);
        product.setQuantity(quantity);
        try{
            productDao.addProduct(product);
            Toast.makeText(this,"data saved",Toast.LENGTH_LONG).show();
        }
        catch (Exception e){
            Toast.makeText(this,"Error " + e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }
}