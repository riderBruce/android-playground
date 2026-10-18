package com.example.librarybookmanagementsystem;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "books")
public class Book implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "author")
    private String author;

    @ColumnInfo(name = "isdn")
    private String isdn;

    @Ignore
    public Book(int id, String title, String author, String isdn) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isdn = isdn;
    }

    public Book(String title, String author, String isdn) {
        this.title = title;
        this.author = author;
        this.isdn = isdn;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsdn() {
        return isdn;
    }

    public void setIsdn(String isdn) {
        this.isdn = isdn;
    }
}
