package com.serbi.libraryapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class UpdateBookActivity extends AppCompatActivity {

    EditText et_bookName_update, et_bookAuthor_update, et_bookPages_update;
    Button btn_updateBookToMain, btn_updateBook, btn_deleteBook;
    String bookId, bookName, bookAuthor, bookPages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update_book);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        et_bookName_update = findViewById(R.id.et_bookName_update);
        et_bookAuthor_update = findViewById(R.id.et_bookAuthor_update);
        et_bookPages_update = findViewById(R.id.et_bookPages_update);

        btn_deleteBook = findViewById(R.id.btn_deleteBook);
        btn_updateBook = findViewById(R.id.btn_updateBook);
        btn_updateBookToMain = findViewById(R.id.btn_updateBookToMain);

        getAndSetIntentData();

        btn_updateBookToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UpdateBookActivity.this, MainActivity.class));
            }
        });

        btn_updateBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper databaseHelper = new DatabaseHelper(UpdateBookActivity.this);

                String updatedBookName = et_bookName_update.getText().toString();
                String updatedBookAuthor = et_bookAuthor_update.getText().toString();
                String updateBookPages = et_bookPages_update.getText().toString();

                databaseHelper.updateBook(bookId, updatedBookName, updatedBookAuthor, updateBookPages);
            }
        });

        btn_deleteBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDeleteBookDialog();
            }
        });
    }

    private void getAndSetIntentData() {
        if (getIntent().hasExtra("id") && getIntent().hasExtra("title") && getIntent().hasExtra("author") && getIntent().hasExtra("pages")) {
            bookId = getIntent().getStringExtra("id");
            bookName = getIntent().getStringExtra("title");
            bookAuthor = getIntent().getStringExtra("author");
            bookPages = getIntent().getStringExtra("pages");

            et_bookName_update.setText(bookName.toString());
            et_bookAuthor_update.setText(bookAuthor.toString());
            et_bookPages_update.setText(bookPages.toString());
        } else {
            Toast.makeText(this, "No book data", Toast.LENGTH_SHORT).show();
        }
    }

    private void confirmDeleteBookDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(UpdateBookActivity.this);
        builder.setTitle("Delete " + bookName + "?");
        builder.setMessage("Are you sure you want to delete " + bookName + "?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DatabaseHelper databaseHelper = new DatabaseHelper(UpdateBookActivity.this);
                databaseHelper.deleteBook(bookId);
                startActivity(new Intent(UpdateBookActivity.this, MainActivity.class));
                finish();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do nothing
            }
        });

        builder.create().show();
    }
}