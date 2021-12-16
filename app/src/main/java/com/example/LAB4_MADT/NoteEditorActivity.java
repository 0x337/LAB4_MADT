package com.example.LAB4_MADT;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashSet;

public class NoteEditorActivity extends AppCompatActivity {

    int noteId;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editor);

        editText = findViewById(R.id.edNote);

        // Fetch data that is passed from MainActivity
        Intent intent = getIntent();

        // Accessing the data using key and value
        noteId = intent.getIntExtra("noteId", -1);
        if (noteId != -1) {
            editText.setText(MainActivity.notes.get(noteId));
        } else {

            MainActivity.notes.add("");
            noteId = MainActivity.notes.size() - 1;
            MainActivity.adapter.notifyDataSetChanged();

        }
    }

    public void onBtnSaveClick(View view) {
        String noteText = editText.getText().toString();
        if(noteText.isEmpty()){
            Toast.makeText(getApplicationContext(), "Please enter text", Toast.LENGTH_SHORT).show();
        }else {
            MainActivity.notes.set(noteId, noteText);
            MainActivity.adapter.notifyDataSetChanged();
            // Creating Object of SharedPreferences to store data in the phone
            SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("notes", Context.MODE_PRIVATE);
            HashSet<String> set = new HashSet(MainActivity.notes);
            sharedPreferences.edit().putStringSet(Constants.BASE_NOTE_KEY, set).apply();
            finish();
        }
    }
}