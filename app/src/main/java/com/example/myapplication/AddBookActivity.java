package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddBookActivity extends AppCompatActivity {

    private SQLiteHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        dbHelper = new SQLiteHelper(this);
        EditText titleEditText = findViewById(R.id.titleEditText);
        EditText authorEditText = findViewById(R.id.authorEditText);
        Button saveButton = findViewById(R.id.saveButton);

        saveButton.setOnClickListener(v -> {
            String title = titleEditText.getText().toString();
            String author = authorEditText.getText().toString();
            dbHelper.addBook(title, author);
            Toast.makeText(this, "Book added", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}
