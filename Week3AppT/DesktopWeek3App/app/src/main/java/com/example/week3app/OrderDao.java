package com.example.week3app;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface OrderDao {

    @Insert
    void addOrder(Order order);

    @Query("SELECT * FROM Order_table")
    List<Order> getAllOrders();

    @Query("SELECT p.ProdID, o.orderId, o.OrderQuantity " +
            "FROM Product_table p " +
            "INNER JOIN Order_table o " +
            "ON p.ProdID = o.ProdID")
    List<ProductOrder> getAllProductOrders();


}
