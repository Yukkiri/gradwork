package ru.puchkova.gradwork;

import android.app.Application;
import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class App extends Application {

    private static Notes note;
    private ArrayList<Notes> notes;
    private static LoginUser user = new LoginUser();
    private static int userId;
    private NotesAdapter notesAdapter;
    private static NotesList notesList = new NotesList();

    @Override
    public void onCreate() {
        super.onCreate();

        //note =
    }

    public int getId(String login, String pass, Boolean isNew){
        userId = user.getUserId(login, pass, isNew, this);
        return userId;
    }

    public void createNote(String title, String description, long deadline){
        notesList.addNote(userId, title, description, deadline, this);
    }

    public RecyclerView.Adapter<NotesAdapter.ViewHolder> setAdapter(){
        notes = notesList.getNotesList(userId, this);
        /*
        Кажется проблема не в этом
        if(notes == null){
            notes = new ArrayList<>();
            note = new Notes(userId, "meow", "why??", "no", false);
            notes.add(note);
        }
         */
        notesAdapter = new NotesAdapter(this, notes);
        return notesAdapter;
    }
}
