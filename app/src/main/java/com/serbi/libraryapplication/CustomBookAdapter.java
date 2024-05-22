package com.serbi.libraryapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomBookAdapter extends RecyclerView.Adapter<CustomBookAdapter.MyViewHolder> {

    private Context context;
    private ArrayList book_id, book_title, book_author, book_pages;

    public CustomBookAdapter(Context context, ArrayList book_id, ArrayList book_title, ArrayList book_author, ArrayList book_pages) {
        this.context = context;
        this.book_id = book_id;
        this.book_title = book_title;
        this.book_author = book_author;
        this.book_pages = book_pages;
    }

    @NonNull
    @Override
    public CustomBookAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.bookrow, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomBookAdapter.MyViewHolder holder, int position) {
        holder.tv_book_id.setText(book_id.get(position).toString());
        holder.tv_book_title.setText(book_title.get(position).toString());
        holder.tv_book_author.setText(book_author.get(position).toString());
        holder.tv_book_pages.setText(book_pages.get(position).toString());
    }

    @Override
    public int getItemCount() {
        return book_id.size()   ;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_book_id, tv_book_title, tv_book_author, tv_book_pages;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_book_id = itemView.findViewById(R.id.tv_book_id);
            tv_book_title = itemView.findViewById(R.id.tv_book_title);
            tv_book_author = itemView.findViewById(R.id.tv_book_author);
            tv_book_pages = itemView.findViewById(R.id.tv_book_pages);
        }
    }
}
