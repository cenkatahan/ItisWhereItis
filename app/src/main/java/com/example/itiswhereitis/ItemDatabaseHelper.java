package com.example.itiswhereitis;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ItemDatabaseHelper extends SQLiteOpenHelper {

    private Context context;

    private static final String DB_NAME = "ITEM.db";
    private static final int DB_VERSION = 1;

    private static final String TABLE_NAME = "items";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_TYPE = "type";
    private static final String COLUMN_LOCATION = "locaiton";



    public ItemDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query_createTable = " CREATE TABLE " + TABLE_NAME + " ( " +
                                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                    COLUMN_NAME + " TEXT, " +
                                    COLUMN_TYPE + " TEXT, " +
                                    COLUMN_LOCATION + " TEXT)";

        db.execSQL(query_createTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }

    public Cursor readAllItems(){
        String query_returnAll = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query_returnAll, null);
        }
        return cursor;
    }


    public Cursor readKeysOnly(){
        String query_returnKeys =  "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_TYPE + " = ?";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query_returnKeys, new String[]{"KEY"});
        }

        return cursor;
    }


    public void addItem(String itemName, String itemType, String itemLocation){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues itemValues = new ContentValues();

        itemValues.put(COLUMN_NAME, itemName);
        itemValues.put(COLUMN_TYPE, itemType);
        itemValues.put(COLUMN_LOCATION, itemLocation);

        db.insert(TABLE_NAME, null, itemValues);
    }

    public void updateItem(String selectedItemId, String  newName, String newType, String newLocation){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues itemValues = new ContentValues();

        itemValues.put(COLUMN_NAME, newName);
        itemValues.put(COLUMN_TYPE, newType);
        itemValues.put(COLUMN_LOCATION, newLocation);
        db.update(TABLE_NAME, itemValues, COLUMN_ID + " = ? ", new String[]{selectedItemId});
    }

    public void deleteTheItem(String selectedItemId){
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_NAME, COLUMN_ID + " = ? ", new String[]{selectedItemId});
    }

    public void deleteAllItems(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
    }
}
