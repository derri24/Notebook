package com.example.notebook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
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
        //GetData();
    }


    @Override
    protected void onStart() {
        super.onStart();

        GetData();
    }

    public SQLiteDatabase db;

    public void GetData() {
        // создание TextView

        LinearLayout layout = (LinearLayout) findViewById(R.id.layout);

        layout.removeAllViews();
        Cursor query = db.rawQuery("SELECT id,name,note FROM data;", null);
        int i = 0;
        while (query.moveToNext()) {


            int idM = query.getInt(0);
            String name = query.getString(1);
            String note = query.getString(2);
            TextView textView = new TextView(this);
            textView.setText("id:" + idM + " name: " + name + " note: " + note);
            textView.setId(i);
            textView.setTextSize(18);
            textView.setTop(50);
            textView.setEms(13);

            Button deleteBtn = new Button(this);
            deleteBtn.setId(i);
            deleteBtn.setText("⌫");
            deleteBtn.setWidth(50);
            deleteBtn.setHeight(50);
            deleteBtn.setTop(50);
            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try{
                        db.execSQL(" DELETE FROM data WHERE id ="+ idM+";");
                        GetData();
                    }
                    catch(Exception ex){

                    }

                }
            });

            Button editBtn = new Button(this);
            editBtn.setId(i);
            editBtn.setText("✏️");
            // editBtn.set(50);
            editBtn.setHeight(50);
            editBtn.setTop(50);

            editBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    NoteActivity.flag ="UPDATE";
                    NoteActivity.id=idM;
                    createNote(view);
                }
            });


            //setContentView(scrollView);
            layout.addView(textView);
            layout.addView(deleteBtn);
            layout.addView(editBtn);
            i++;
        }
    }

    private void connect() {
        db = getBaseContext().openOrCreateDatabase("application.db", MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS data (id INTEGER PRIMARY KEY,name TEXT, note Text)");
    }

    public void createNote(View view) {
        Intent intent = new Intent(this, NoteActivity.class);
        startActivity(intent);
    }
}