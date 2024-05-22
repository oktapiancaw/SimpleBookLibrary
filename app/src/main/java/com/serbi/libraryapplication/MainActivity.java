package com.serbi.libraryapplication;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView rv_books;
    FloatingActionButton btn_toAddBookPage;

    DatabaseHelper database;
    ArrayList<String> book_id, book_title, book_author, book_pages;
    CustomBookAdapter bookAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.btn_add), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        rv_books = findViewById(R.id.rv_books);
        btn_toAddBookPage = findViewById(R.id.btn_plus);
        btn_toAddBookPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AddBookActivity.class));
            }
        });

        database = new DatabaseHelper(MainActivity.this);
        book_id = new ArrayList<>();
        book_title = new ArrayList<>();
        book_author = new ArrayList<>();
        book_pages = new ArrayList<>();

        storeBookDataInArrays();
        bookAdapter = new CustomBookAdapter(MainActivity.this, book_id, book_title, book_author, book_pages);
        rv_books.setAdapter(bookAdapter);
        rv_books.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }

    void storeBookDataInArrays() {
        Cursor cursor = database.readAllData();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No book data found", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                book_id.add(cursor.getString(0));
                book_title.add(cursor.getString(1));
                book_author.add(cursor.getString(2));
                book_pages.add(cursor.getString(3));
            }
        }
    }
}