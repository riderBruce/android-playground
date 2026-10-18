package com.example.librarybookmanagementsystem;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface BookDao {
    @Insert
    long addBook(Book book);
    @Query("SELECT * FROM books")
    List<Book> getAllBooks();
    @Query("SELECT * FROM books WHERE id=:id")
    Book getBookById(int id);
    @Query("SELECT * FROM books WHERE title=:title LIMIT 1")
    Book getBookByTitle(String title);
    @Update
    int updateBook(Book book);
    @Query("DELETE FROM books WHERE id=:id")
    int deleteBook(int id);
    @Query("DELETE FROM books WHERE title=:title")
    int deleteBookByTitle(String title);
    @Query("DELETE FROM books WHERE author=:author")
    int deleteBookByAuthor(String author);
}
