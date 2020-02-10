package ru.puchkova.gradwork;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ImageButton add;
    private ImageButton settings;
    private RecyclerView notesList;
    private App app;
    private ArrayList<Notes> notes;
    RecyclerView.Adapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        app = (App)MainActivity.this.getApplication();

        init();
    }

    private void init(){
        add = findViewById(R.id.add);
        settings = findViewById(R.id.settings);
        notesList = findViewById(R.id.notes_list);

        add.setOnClickListener(addOnclickListener);
        settings.setOnClickListener(settingsOnClickListener);



    }


    private void setAdapter(){
        notes = app.getNotes();
        notesList.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        adapter = app.setAdapter(notes);
        notesList.setAdapter(adapter);
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

    //это очень неправильно и косо, но я не сообразила, как сделать иначе
    @Override
    protected void onResume() {
        super.onResume();
        setAdapter();
    }
}


/*
ToDo list:
 1. Темы
 2. Языки
 3. Подумать над уведомлением адаптера
 4. Добавить отображение только избранного
 5. Проверить почему отнимает полчаса
 6. Смена пароля
 7. авторизация после сворачивания
 8. Шифрование пароля
 */