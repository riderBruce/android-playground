package com.example.week3app;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Product_table")
public class Product {

    @PrimaryKey(autoGenerate = true)
    public int ProdID;

    @ColumnInfo(name = "ProdName")
    private String prodName;

    @ColumnInfo(name = "Quantity")
    private int quantity;

    public String getProdName(){
        return prodName;
    }

    public int getProdID(){
        return ProdID;
    }


    public void setProdName(String n){
        prodName = n;
    }

    public int getQuantity(){
        return quantity;
    }

    public void setQuantity(int q){
        quantity = q;
    }
}
