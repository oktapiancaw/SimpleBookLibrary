package com.serbi.libraryapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AddBookActivity extends AppCompatActivity {

    Button btn_addBookToMain, btn_addBook;
    EditText et_bookName, et_bookAuthor, et_bookPages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_book);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.btn_add), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btn_addBookToMain = findViewById(R.id.btn_addBookToMain);
        btn_addBook = findViewById(R.id.btn_addBook);
        et_bookName = findViewById(R.id.et_bookName);
        et_bookAuthor = findViewById(R.id.et_bookAuthor);
        et_bookPages = findViewById(R.id.et_bookPages);

        btn_addBookToMain.setOnClickListener(v -> startActivity(new Intent(AddBookActivity.this, MainActivity.class)));

        btn_addBook.setOnClickListener(v -> {
            DatabaseHelper database = new DatabaseHelper(AddBookActivity.this);
            database.addBook(et_bookName.getText().toString().trim(),
                    et_bookAuthor.getText().toString().trim(),
                    Integer.parseInt(et_bookPages.getText().toString().trim()));
        });
    }
}