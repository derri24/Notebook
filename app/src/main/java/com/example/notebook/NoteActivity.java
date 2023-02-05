package com.example.notebook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;

public class NoteActivity extends AppCompatActivity {
    private static String activity;

    public static void setActivity(String _activity) {
        activity = _activity;
    }

    private static int id;

    public static void setId(int _id) {
        id = _id;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        connect();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (Objects.equals(activity, "UPDATE"))
            getData();
    }

    private void getData() {

        Cursor query = db.rawQuery("SELECT name,note FROM data WHERE id=" + id + ";", null);
        query.moveToNext();
        String name = query.getString(0);
        String note = query.getString(1);

        if (Objects.equals(name, "(Untitled)")){
            name="";
        }

        ((EditText) findViewById(R.id.nameBox)).setText(name);
        ((EditText) findViewById(R.id.noteBox)).setText(note);
    }


    private SQLiteDatabase db;

    private void connect() {
        try {
            db = getBaseContext().openOrCreateDatabase("application.db", MODE_PRIVATE, null);
            db.execSQL("CREATE TABLE IF NOT EXISTS data (id INTEGER PRIMARY KEY,name TEXT, note Text)");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void insert(String name, String note) {
        try {
            if (Objects.equals(name, "")){
                name="(Untitled)";
            }
            db.execSQL("INSERT OR IGNORE INTO data(name,note) VALUES ('" + name + "','" + note + "');");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void update(String name, String note) {
        try {
            if (Objects.equals(name, "")){
                name="(Untitled)";
            }
            db.execSQL("UPDATE data SET note ='" + note + "', name = '" + name + "' WHERE id =" + id + ";");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void saveNote(View view) {
        try {
            String name = ((EditText) findViewById(R.id.nameBox)).getText().toString();
            String note = ((EditText) findViewById(R.id.noteBox)).getText().toString();

            if (Objects.equals(activity, "CREATE")) {
                insert(name, note);
            } else if (Objects.equals(activity, "UPDATE")) {
                update(name, note);
            }
            finish();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}