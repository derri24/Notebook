package com.example.notebook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
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

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        connect();
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

    //TOLOWER
    private void getData() {
        LinearLayout layout = (LinearLayout) findViewById(R.id.layout);
        layout.removeAllViews();


        LinearLayout relativeLayout = new LinearLayout(this);
        Cursor query;
        try {
            String searchText = ((EditText) findViewById(R.id.search)).getText().toString();
            query = db.rawQuery("SELECT id,name FROM data WHERE name LIKE '" + searchText + "%';", null);
            int i = 0;
            while (query.moveToNext()) {
                int id = query.getInt(0);
                String name = query.getString(1);

                TextView textView = createTextView(i, name);
                Button deleteBtn = createDeleteButton(i, id);
                Button updateBtn = createUpdateButton(i, id);

                relativeLayout.addView(textView,200,80);
                relativeLayout.addView(deleteBtn,150,150);
                relativeLayout.addView(updateBtn,150,150);

                layout.addView(relativeLayout,500,500);
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
        textView.setTextSize(20);
        textView.setTop(50);
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
      //  deleteBtn.setWidth(50);
        //deleteBtn.setHeight(50);
        deleteBtn.setTop(150);
       // deleteBtn.setX(200);
        //deleteBtn.setY(200);
        deleteBtn.setLeft(300);
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
       // updateBtn.setHeight(50);
        updateBtn.setBackgroundColor(Color.parseColor("#abdfff"));
        updateBtn.setTextColor(Color.parseColor("#ffffff"));

        updateBtn.setTop(50);
        // deleteBtn.setX(200);
        //deleteBtn.setY(200);
        updateBtn.setLeft(500);
       // updateBtn.setLeft(100);

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