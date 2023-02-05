package com.example.notebook;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText myTextBox = (EditText) findViewById(R.id.search);
        myTextBox.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                getData();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        connect();
        getData();
    }

    @Override
    protected void onStop() {
        super.onStop();
        db.close();
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


        Cursor query;
        try {
            String searchText = ((EditText) findViewById(R.id.search)).getText().toString();
            query = db.rawQuery("SELECT id,name FROM data WHERE name LIKE '%" + searchText + "%';", null);
            int i = 0;
            while (query.moveToNext()) {
                int id = query.getInt(0);
                String name = query.getString(1);

                TextView textView = createTextView(i, name);
                Button deleteBtn = createDeleteButton(i, id);
                Button updateBtn = createUpdateButton(i, id);
                TextView space = new TextView(this);

                LinearLayout linearLayout = new LinearLayout(this);
                linearLayout.addView(textView, 706, 140);
                linearLayout.addView(updateBtn, 140, 140);
                linearLayout.addView(space, 17, 140);
                linearLayout.addView(deleteBtn, 140, 140);

                layout.addView(linearLayout, 1100, 155);
                i++;
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private TextView createTextView(int i, String name) {
        TextView textView = new TextView(this);
        if (name.length() > 18) {
            name = name.substring(0, 18);
            char[] charsName = name.toCharArray();
            charsName[15] = '.';
            charsName[16] = '.';
            charsName[17] = '.';
            name = String.valueOf(charsName);
        }
        textView.setText(name);
        textView.setId(i);
        textView.setTextSize(22);
        textView.setEms(10);
        return textView;
    }

    private Button createDeleteButton(int i, int id) {
        Button deleteBtn = new Button(this);
        deleteBtn.setId(i);
        deleteBtn.setText("⌫");
        deleteBtn.setBackgroundColor(Color.parseColor("#ff4040"));
        deleteBtn.setTextColor(Color.parseColor("#ffffff"));
        deleteBtn.setTextSize(20);

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
        updateBtn.setTextSize(20);
        updateBtn.setBackgroundColor(Color.parseColor("#abdfff"));
        updateBtn.setTextColor(Color.parseColor("#ffffff"));

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