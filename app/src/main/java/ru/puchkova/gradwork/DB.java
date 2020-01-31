package ru.puchkova.gradwork;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DB extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "contactDb";
    public static final String TABLE_DATA = "data";
    public static final String TABLE_SETTINGS = "settings";
    public static final String TABLE_NOTES = "notes";

    public static final String KEY_ID = "_id";

    public static final String KEY_LOGIN = "login";
    public static final String KEY_PASS = "pass";

    public static final String KEY_THEME = "theme";
    public static final String KEY_LANG = "lang";

    public static final String KEY_NOTE_ID = "noteId";
    public static final String KEY_TITLE = "title";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_DEADLINE = "deadline";
    public static final String KEY_CHECKED = "checked";

    public DB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_DATA + "(" + KEY_ID + " integer primary key, "
                + KEY_LOGIN + " text, " + KEY_PASS + " text, UNIQUE(" + KEY_LOGIN + ", " + KEY_PASS + ")" + ")");

        db.execSQL("create table " + TABLE_SETTINGS + "(" + KEY_ID + " integer, "
                + KEY_THEME + " integer, " + KEY_LANG + " integer)");

        db.execSQL("create table " + TABLE_NOTES + "(" + KEY_ID + " integer, " + KEY_NOTE_ID + " integer primary key, "
                + KEY_TITLE + " text, " + KEY_DESCRIPTION + " text, " + KEY_DEADLINE + " integer, " + KEY_CHECKED + " integer" + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_DATA);
        db.execSQL("drop table if exists " + TABLE_SETTINGS);
        db.execSQL("drop table if exists " + TABLE_NOTES);
        onCreate(db);
    }
}
