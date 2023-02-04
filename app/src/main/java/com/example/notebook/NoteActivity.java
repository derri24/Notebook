package com.example.notebook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class NoteActivity extends AppCompatActivity {

    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
    }

 

    public void insert(String name, String content) {
        db.execSQL("INSERT OR IGNORE INTO data VALUES ('" + name + "','" + content + "');");
    }

    public void saveNote(View view) {
        try {
            String name = ((EditText) findViewById(R.id.nameBox)).getText().toString();
            String note = ((EditText) findViewById(R.id.noteBox)).getText().toString();
            insert(name, note);
            
            Intent intent = new Intent (this, MainActivity.class);

            startActivity(intent);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}