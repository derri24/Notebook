package com.example.notebook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        connect();
        GetData();
    }

    public SQLiteDatabase db;

    public void GetData() {
        // создание TextView

        LinearLayout layout = (LinearLayout) findViewById(R.id.layout);

        Cursor query = db.rawQuery("SELECT * FROM data;", null);
        int i = 0;
        while (query.moveToNext()) {

            String name = query.getString(0);
            TextView textView = new TextView(this);
            textView.setText(name);
            textView.setId(i);

            textView.setTextSize(60);
            textView.setTop(50);

            //setContentView(scrollView);
            layout.addView(textView);
            i++;
        }
    }

    private void connect() {
        db = getBaseContext().openOrCreateDatabase("application.db", MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS data (name TEXT, note Text)");
    }

    public void createNote(View view) {
        Intent intent = new Intent(this, NoteActivity.class);
        startActivity(intent);
    }
}