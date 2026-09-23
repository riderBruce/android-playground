package com.example.roompractice;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ProductDao {

    @Query("SELECT * FROM products")
    List<Product> getAllProducts();

    @Query("SELECT * FROM products WHERE product_id = :id")
    Product getProductById(int id);

    @Insert
    void addProduct(Product product);

    @Update
    void updateProduct(Product product);

    @Query("DELETE FROM products WHERE product_id = :id")
    int deleteProductById(int id);
}
