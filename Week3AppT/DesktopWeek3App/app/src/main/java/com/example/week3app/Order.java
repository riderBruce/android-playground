package com.example.week3app;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "Order_table",
        foreignKeys = @ForeignKey(entity=Product.class,
                parentColumns = "ProdID",
                childColumns = "ProdID"))
public class Order {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "orderId")
    private int orderId;
    @ColumnInfo(name = "ProdID")
    private int proID;
    @ColumnInfo(name = "OrderQuantity")
    private int orderQ;

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getProID() {
        return proID;
    }

    public void setProID(int proID) {
        this.proID = proID;
    }

    public int getOrderQ() {
        return orderQ;
    }

    public void setOrderQ(int orderQ) {
        this.orderQ = orderQ;
    }
}
