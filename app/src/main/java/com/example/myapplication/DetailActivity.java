package com.example.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    private SQLiteHelper dbHelper;
    private int bookId;
    private EditText titleEditText, authorEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        dbHelper = new SQLiteHelper(this);
        titleEditText = findViewById(R.id.titleEditText);
        authorEditText = findViewById(R.id.authorEditText);
        Button updateButton = findViewById(R.id.updateButton);
        Button deleteButton = findViewById(R.id.deleteButton);

        bookId = getIntent().getIntExtra("BOOK_ID", -1);
        loadBookData();

        updateButton.setOnClickListener(v -> {
            String title = titleEditText.getText().toString();
            String author = authorEditText.getText().toString();
            dbHelper.updateBook(bookId, title, author);
            Toast.makeText(this, "Book updated", Toast.LENGTH_SHORT).show();
            finish();
        });

        deleteButton.setOnClickListener(v -> {
            dbHelper.deleteBook(bookId);
            Toast.makeText(this, "Book deleted", Toast.LENGTH_SHORT).show();
            finish();
        });
    }

    private void loadBookData() {
        Cursor cursor = dbHelper.getBookById(bookId);
        if (cursor.moveToFirst()) {
            titleEditText.setText(cursor.getString(1));
            authorEditText.setText(cursor.getString(2));
        }
        cursor.close();
    }
}
