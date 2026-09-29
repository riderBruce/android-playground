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

    @Query("SELECT * FROM Product_table")
    List<Product> getAllProducts();

    @Query("SELECT * FROM Product_table where ProdID = :id")
    Product getProduct(int id);

    @Update
    void updateProduct(Product product);

    @Query("DELETE FROM Product_table where ProdID = :id")
    void deleteProduct(int id);
}
