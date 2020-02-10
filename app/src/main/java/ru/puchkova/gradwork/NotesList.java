package ru.puchkova.gradwork;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class NotesList {
    private Notes note;

    private final static int FALSE = 0;
    private final static int TRUE = 1;

    public ArrayList<Notes> getNotesList(int userId, Context context){
        ArrayList<Notes> notesList = new ArrayList<>();
        DB db = new DB(context);
        SQLiteDatabase database = db.getWritableDatabase();


        Cursor cursor = database.rawQuery("SELECT DISTINCT " + DB.KEY_NOTE_ID + ", " + DB.KEY_TITLE + ", " + DB.KEY_DESCRIPTION + ", " + DB.KEY_DEADLINE + ", " + DB.KEY_CHECKED +
                " FROM " + DB.TABLE_NOTES + " WHERE " + DB.KEY_ID + " = " + userId + " ORDER BY" + " CASE WHEN " + DB.KEY_DEADLINE + " = -1 THEN " +
                "(SELECT DISTINCT MAX(" + DB.KEY_DEADLINE + ")" + " FROM " + DB.TABLE_NOTES + " WHERE " + DB.KEY_ID + " = " + userId + ") + 1 ELSE "  + DB.KEY_DEADLINE + " END" , null);
        cursor.moveToFirst();

            for(int i = 0; i < cursor.getCount(); i++){
                int noteId = cursor.getInt(cursor.getColumnIndex(DB.KEY_NOTE_ID));
                String title = cursor.getString(cursor.getColumnIndex(DB.KEY_TITLE));
                String description = cursor.getString(cursor.getColumnIndex(DB.KEY_DESCRIPTION));
                String deadline = cursor.getString(cursor.getColumnIndex(DB.KEY_DEADLINE));
                int check = Integer.parseInt(cursor.getString(cursor.getColumnIndex(DB.KEY_CHECKED)));
                boolean isCheck = check == 0 ? false : true;
                note = new Notes(noteId, title, description, deadline, isCheck);
                notesList.add(note);
                cursor.moveToNext();
            }

        return notesList;
    }

    public ArrayList<Notes> addNote(int userId, String title, String description, long deadline, Context context){
        DB db = new DB(context);
        SQLiteDatabase database = db.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(DB.KEY_ID, userId);
        contentValues.put(DB.KEY_TITLE, title);
        contentValues.put(DB.KEY_DESCRIPTION, description);
        contentValues.put(DB.KEY_DEADLINE, deadline);
        contentValues.put(DB.KEY_CHECKED, FALSE);

        database.insert(DB.TABLE_NOTES, null, contentValues);

        return getNotesList(userId, context);
    }

    public Notes getNote(int userId, int noteId, Context context){
        DB db = new DB(context);
        SQLiteDatabase database = db.getWritableDatabase();


        Cursor cursor = database.rawQuery("SELECT DISTINCT " + DB.KEY_TITLE + ", " + DB.KEY_DESCRIPTION + ", " + DB.KEY_DEADLINE + ", " + DB.KEY_CHECKED +
                " FROM " + DB.TABLE_NOTES + " WHERE " + DB.KEY_ID + " = " + userId + " AND " + DB.KEY_NOTE_ID + " = " + noteId, null);
        cursor.moveToFirst();

        String title = cursor.getString(cursor.getColumnIndex(DB.KEY_TITLE));
        String description = cursor.getString(cursor.getColumnIndex(DB.KEY_DESCRIPTION));
        String deadline = cursor.getString(cursor.getColumnIndex(DB.KEY_DEADLINE));
        int check = Integer.parseInt(cursor.getString(cursor.getColumnIndex(DB.KEY_CHECKED)));
        boolean isCheck = check != 0;

        note = new Notes(noteId, title, description, deadline, isCheck);

        return note;
    }

    public void saveNote(int userId, Notes note, Context context){
        DB db = new DB(context);
        int check;
        SQLiteDatabase database = db.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(DB.KEY_ID, userId);
        contentValues.put(DB.KEY_TITLE, note.getTitle());
        contentValues.put(DB.KEY_DESCRIPTION, note.getDescription());
        contentValues.put(DB.KEY_DEADLINE, note.getDeadline());
        boolean isCheck = note.isCheck();
        if (isCheck)
            check = TRUE;
        else
            check = FALSE;
        contentValues.put(DB.KEY_CHECKED, check);

        database.update(DB.TABLE_NOTES, contentValues, DB.KEY_NOTE_ID + "= ?", new String[]{Integer.toString(note.getId())});
    }


}
