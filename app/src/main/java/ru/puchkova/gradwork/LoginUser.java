package ru.puchkova.gradwork;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;

class LoginUser {

    int getUserId(String login, String pass, Boolean isNew, Context context){
        DB db = new DB(context);
        SQLiteDatabase database = db.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(DB.KEY_LOGIN, login);
        contentValues.put(DB.KEY_PASS, pass);

        int userId;
        if(isNew){
            userId = (int) database.insert(DB.TABLE_DATA, null, contentValues);
        } else {
            Cursor cursor = database.rawQuery("SELECT DISTINCT " + DB.KEY_ID + ", " + DB.KEY_PASS + " FROM " + DB.TABLE_DATA +
                    " WHERE " + DB.KEY_LOGIN + " = '" + login + "'", null);
            cursor.moveToFirst();
            try {
                if (cursor.getString(cursor.getColumnIndex(DB.KEY_PASS)).equals(pass)) {
                    userId = cursor.getInt(cursor.getColumnIndex(DB.KEY_ID));
                } else {
                    userId = -1;
                }
            } catch (CursorIndexOutOfBoundsException e){
                userId = -2;
            }
        }
        return userId;
    }
}
