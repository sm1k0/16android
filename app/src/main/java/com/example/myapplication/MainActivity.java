package com.example.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private BookAdapter adapter;
    private ArrayList<Book> bookList;
    private SQLiteHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new SQLiteHelper(this);
        recyclerView = findViewById(R.id.recyclerView);
        Button addButton = findViewById(R.id.addButton);

        bookList = new ArrayList<>();
        adapter = new BookAdapter(this, bookList, id -> {
            Intent intent = new Intent(MainActivity.this, DetailActivity.class);
            intent.putExtra("BOOK_ID", id);
            startActivity(intent);
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        loadBooks();

        addButton.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, AddBookActivity.class));
        });
    }

    private void loadBooks() {
        bookList.clear();
        Cursor cursor = dbHelper.getBooks();
        if (cursor.moveToFirst()) {
            do {
                bookList.add(new Book(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2)
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadBooks();
    }
}
