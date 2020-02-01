package ru.puchkova.gradwork;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ImageButton add;
    private ImageButton settings;
    private RecyclerView notesList;
    private App app;
    private ArrayList<Notes> notes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init(){
        add = findViewById(R.id.add);
        settings = findViewById(R.id.settings);
        notesList = findViewById(R.id.notes_list);

        add.setOnClickListener(addOnclickListener);
        settings.setOnClickListener(settingsOnClickListener);

        app = (App)MainActivity.this.getApplication();
        notes = app.getNotes();
        notesList.setAdapter(app.setAdapter(notes));
    }

    View.OnClickListener settingsOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent sett = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(sett);
        }
    };

    View.OnClickListener addOnclickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent addNote = new Intent(MainActivity.this, NoteActivity.class);
            startActivity(addNote);
        }
    };

}
