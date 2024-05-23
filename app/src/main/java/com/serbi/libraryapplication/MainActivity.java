package com.serbi.libraryapplication;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
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
    FloatingActionButton btn_toAddBookPage, btn_deleteAll;

    DatabaseHelper database;
    ArrayList<String> book_id, book_title, book_author, book_pages;
    CustomBookAdapter bookAdapter;
    ImageView iv_noData;
    TextView tv_noData_txt;

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
        btn_deleteAll = findViewById(R.id.btn_deleteAllButton);

        database = new DatabaseHelper(MainActivity.this);

        iv_noData = findViewById(R.id.iv_noData);

        tv_noData_txt = findViewById(R.id.tv_noData_txt);

        book_id = new ArrayList<>();
        book_title = new ArrayList<>();
        book_author = new ArrayList<>();
        book_pages = new ArrayList<>();

        btn_deleteAll.setOnClickListener(v -> deleteAllBook());
        btn_toAddBookPage.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, AddBookActivity.class)));

        storeBookDataInArrays();
        bookAdapter = new CustomBookAdapter(MainActivity.this, book_id, book_title, book_author, book_pages);
        rv_books.setAdapter(bookAdapter);
        rv_books.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }

    private void storeBookDataInArrays() {
        Cursor cursor = database.readAllData();
        if (cursor.getCount() == 0) {
            iv_noData.setVisibility(View.VISIBLE);
            tv_noData_txt.setVisibility(View.VISIBLE);
        } else {
            while (cursor.moveToNext()) {
                book_id.add(cursor.getString(0));
                book_title.add(cursor.getString(1));
                book_author.add(cursor.getString(2));
                book_pages.add(cursor.getString(3));

                iv_noData.setVisibility(View.GONE);
                tv_noData_txt.setVisibility(View.GONE);
            }
        }
    }

    private void deleteAllBook() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Delete all books");
        builder.setMessage("Are you sure you want to delete all books?");

        builder.setPositiveButton("Yes", (dialog, which) -> {
            database.deleteAllBook();
            startActivity(new Intent(MainActivity.this, MainActivity.class));
            finish();
        });

        builder.setNegativeButton("No", (dialog, which) -> {
            // Do nothing
        });
        builder.create().show();
    }
}