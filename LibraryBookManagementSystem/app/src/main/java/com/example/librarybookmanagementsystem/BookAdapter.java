package com.example.librarybookmanagementsystem;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.librarybookmanagementsystem.databinding.ItemBookBinding;

import java.util.ArrayList;
import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder>{

    List<Book> books = new ArrayList<>();
    Context context;

    public BookAdapter(Context context) {
        this.context = context;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemBookBinding binding = ItemBookBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Book book = books.get(position);
        holder.bind(book);
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemBookBinding binding;
        public ViewHolder(@NonNull ItemBookBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.cardBook.setOnClickListener(view -> {
                int position = getBindingAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    Intent intent = new Intent(context, DetailActivity.class);
                    intent.putExtra("book_details", books.get(position));
                    context.startActivity(intent);
                }
            });
        }


        public void bind(Book book) {
            binding.tvBookId. setText(String.valueOf(book.getId()));
            binding.tvBookTitle.setText(book.getTitle());
            binding.tvBookAuthor.setText(book.getAuthor());
            binding.tvBookIsdn.setText(book.getIsdn());
        }
    }
}
