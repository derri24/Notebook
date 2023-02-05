package com.example.notebook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        connect();
    }

    @Override
    protected void onStart() {
        super.onStart();
        getData();
    }

    public void createNote(View view) {
        Intent intent = new Intent(this, NoteActivity.class);
        NoteActivity.setActivity("CREATE");
        startActivity(intent);
    }

    public void updateNote() {
        Intent intent = new Intent(this, NoteActivity.class);
        NoteActivity.setActivity("UPDATE");
        startActivity(intent);
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

    private void getData() {
        LinearLayout layout = (LinearLayout) findViewById(R.id.layout);
        layout.removeAllViews();
        try {
            Cursor query = db.rawQuery("SELECT id,name,note FROM data;", null);
            int i = 0;
            while (query.moveToNext()) {
                int id = query.getInt(0);
                String name = query.getString(1);

                TextView textView = createTextView(i, name);
                Button deleteBtn = createDeleteButton(i, id);
                Button updateBtn = createUpdateButton(i, id);

                layout.addView(textView);
                layout.addView(updateBtn);
                layout.addView(deleteBtn);
                i++;
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private TextView createTextView(int i, String name) {
        TextView textView = new TextView(this);
        textView.setText(name);
        textView.setId(i);
        textView.setTextSize(18);
        textView.setTop(50);
        textView.setEms(13);
        return textView;
    }

    private Button createDeleteButton(int i, int id) {
        Button deleteBtn = new Button(this);
        deleteBtn.setId(i);
        deleteBtn.setText("⌫");
        deleteBtn.setWidth(50);
        deleteBtn.setHeight(50);
        deleteBtn.setTop(50);
        deleteBtn.setLeft(500);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    db.execSQL(" DELETE FROM data WHERE id =" + id + ";");
                    getData();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

            }
        });
        return deleteBtn;
    }

    private Button createUpdateButton(int i, int id) {
        Button updateBtn = new Button(this);
        updateBtn.setId(i);
        updateBtn.setText("✏️");
        updateBtn.setHeight(50);
        updateBtn.setTop(50);

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NoteActivity.setId(id);
                updateNote();
            }
        });
        return updateBtn;
    }


}