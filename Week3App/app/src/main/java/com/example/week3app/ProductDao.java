package com.example.week3app;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ProductDao {

    @Insert
    void addProduct(Product product);

    @Query("SELECT * FROM products")
    List<Product> getAllProducts();

    @Query("SELECT * FROM products WHERE product_id = :id")
    Product getProductById(int id);

    @Update
    void updateProduct(Product product);

    @Query("DELETE FROM products WHERE product_id = :id")
    int deleteProductById(int id);
}
