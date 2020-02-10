package ru.puchkova.gradwork;

import android.app.Application;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

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

    }

    public int getId(String login, String pass, Boolean isNew){
        userId = user.getUserId(login, pass, isNew, this);
        return userId;
    }

    public void createNote(String title, String description, long deadline){
        notes = notesList.addNote(userId, title, description, deadline, this);
    }

    public ArrayList<Notes> getNotes(){
        return notes = notesList.getNotesList(userId, this);
    }

    public RecyclerView.Adapter<NotesAdapter.ViewHolder> setAdapter(ArrayList<Notes> notes){
        notesAdapter = new NotesAdapter(this, notes);

        return notesAdapter;
    }

    public void changeNote(Notes note){
        notesList.saveNote(userId, note, this);
    }
}
