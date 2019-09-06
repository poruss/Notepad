package com.example.notepad.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.notepad.R;

public class NotesDetailActivity extends AppCompatActivity {
    private TextView detailNotesTitle;
    private TextView detailNotesMessage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_detail);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("Detail");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        initializeUi();
        setData();
    }

    private void initializeUi() {
        detailNotesTitle = (TextView)findViewById(R.id.detailNotesTitle);
        detailNotesMessage = (TextView)findViewById(R.id.detailNotesMessage);
    }

    private void setData() {
        Intent intent  = getIntent();
        String title = intent.getStringExtra("notes_title_i");
        String message = intent.getStringExtra("notes_message_i");
        detailNotesTitle.setText(title);
        detailNotesMessage.setText(message);
    }
}
