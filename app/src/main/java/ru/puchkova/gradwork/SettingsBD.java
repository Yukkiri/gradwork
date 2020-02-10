package ru.puchkova.gradwork;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class SettingsBD {
    private static final int defaultInt = 0;
    public void changeSettings(int userId, Settings settings, Context context){
        DB db = new DB(context);
        SQLiteDatabase database = db.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(DB.KEY_THEME, settings.getTheme());
        contentValues.put(DB.KEY_LANG, settings.getLanguage());

        database.update(DB.TABLE_SETTINGS, contentValues, DB.KEY_ID + "= ?", new String[]{Integer.toString(userId)});
    }

    public Settings getSettings(int userId, Context context){
        Settings settings;

        DB db = new DB(context);
        SQLiteDatabase database = db.getWritableDatabase();


        Cursor cursor = database.rawQuery("SELECT DISTINCT " + DB.KEY_THEME + ", " + DB.KEY_LANG +
                " FROM " + DB.TABLE_SETTINGS + " WHERE " + DB.KEY_ID + " = " + userId, null);
        cursor.moveToFirst();

        int theme = cursor.getInt(cursor.getColumnIndex(DB.KEY_THEME));
        int lang = cursor.getInt(cursor.getColumnIndex(DB.KEY_LANG));

        settings = new Settings(theme, lang);

        return settings;
    }

    public void setDefaultSettings(int userId, Context context){
        DB db = new DB(context);
        SQLiteDatabase database = db.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(DB.KEY_ID, userId);
        contentValues.put(DB.KEY_THEME, defaultInt);
        contentValues.put(DB.KEY_LANG, defaultInt);

        database.insert(DB.TABLE_SETTINGS, null, contentValues);
    }
}
