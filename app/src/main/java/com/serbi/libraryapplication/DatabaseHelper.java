package com.serbi.libraryapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    private final Context context;
    private static final String DATABASE_NAME = "library.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "books";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "book_title";
    private static final String COLUMN_AUTHOR = "book_author";
    private static final String COLUMN_PAGES = "book_pages";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" + COLUMN_ID + " " +
                "INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_NAME + " " +
                "TEXT, " + COLUMN_AUTHOR + " TEXT, " + COLUMN_PAGES + " INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addBook(String title, String author, int pages) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        
        values.put(COLUMN_NAME, title);
        values.put(COLUMN_AUTHOR, author);
        values.put(COLUMN_PAGES, pages);
        
        long result = database.insert(TABLE_NAME, null, values);
        if (result == -1) {
            Toast.makeText(context, "Adding book failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Book added successfully", Toast.LENGTH_SHORT).show();
        }
    }

    public void updateBook(String rowID, String title, String author, String pages) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues updatedBookValues = new ContentValues();

        updatedBookValues.put(COLUMN_NAME, title);
        updatedBookValues.put(COLUMN_AUTHOR, author);
        updatedBookValues.put(COLUMN_PAGES, pages);

        long result = database.update(TABLE_NAME, updatedBookValues, "id=?", new String[]{rowID});
        if (result == -1) {
            Toast.makeText(context, "Update failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Book has been updated", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteBook(String id) {
        SQLiteDatabase database = this.getWritableDatabase();
        long result = database.delete(TABLE_NAME, "id=?", new String[]{id});
        
        if (result == -1) {
            Toast.makeText(context, "Failed to remove book", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Book has been removed", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteAllBook() {
        SQLiteDatabase database = this.getWritableDatabase();
        database.execSQL("DELETE FROM " + TABLE_NAME);
    }

    public Cursor readAllData() {
        String readAllDataQuery = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase database = this.getReadableDatabase();

        return database.rawQuery(readAllDataQuery, null);
    }
}
