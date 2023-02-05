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
import java.util.Objects;

public class NoteActivity extends AppCompatActivity {

    public SQLiteDatabase db;


    public static String flag;
    public static int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        connect();
    }


    @Override
    protected void onStart() {
        super.onStart();

    }

    private void connect() {
        db = getBaseContext().openOrCreateDatabase("application.db", MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS data (id INTEGER PRIMARY KEY,name TEXT, note Text)");
    }


    public void insert(String name, String content) {
        db.execSQL("INSERT OR IGNORE INTO data(name,note) VALUES ('" + name + "','" + content + "');");
    }

    public void update() {

    }

    public void saveNote(View view) {
        try {
            String name = ((EditText) findViewById(R.id.nameBox)).getText().toString();
            String note = ((EditText) findViewById(R.id.noteBox)).getText().toString();

            if (Objects.equals(flag, "UPDATE")) {

                db.execSQL("UPDATE data SET note ='" + note + "', name = '" + name + "' WHERE id =" + id + ";");
                finish();
            }

            insert(name, note);

            finish();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}