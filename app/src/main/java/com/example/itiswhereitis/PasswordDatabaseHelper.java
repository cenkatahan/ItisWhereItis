package com.example.itiswhereitis;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

public class PasswordDatabaseHelper extends SQLiteOpenHelper {

    private Context context;

    private static final String DB_NAME = "PASSWORD.db";
    private static final int DB_VERSION = 1;

    private static final String TABLE_PASSWORD = "password";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_VALUE = "password";

    public PasswordDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query_createTable = " CREATE TABLE " + TABLE_PASSWORD + " (" +
                                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                    COLUMN_VALUE + " TEXT)";

        db.execSQL(query_createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PASSWORD);
    }

    public void setPassword(String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues passwordVal = new ContentValues();

        passwordVal.put(COLUMN_VALUE, password);
        db.insert(TABLE_PASSWORD, null, passwordVal);
    }

    public Cursor readPassword(){
        String query_readPassword = "SELECT * FROM " + TABLE_PASSWORD;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query_readPassword, null);
        }
        return cursor;
    }

    public void deletePassword(){
        String query_delete = " DELETE FROM " + TABLE_PASSWORD;
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(query_delete);
    }

    public void deleteAllPasswords(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_PASSWORD);
    }
}
