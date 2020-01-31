package ru.puchkova.gradwork;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    private ImageButton add;
    private ImageButton settings;
    private RecyclerView notesList;
    App app = (App)MainActivity.this.getApplication();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        //Вот тут ошибка
        //я не понимаю, почему
        //задействованы классы App, NotesAdapter, NotesList
        notesList.setAdapter(app.setAdapter());
    }

    private void init(){
        add = findViewById(R.id.add);
        settings = findViewById(R.id.settings);
        notesList = findViewById(R.id.notes_list);

        add.setOnClickListener(addOnclickListener);
        settings.setOnClickListener(settingsOnClickListener);
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
