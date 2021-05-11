package com.example.venux.controllers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseController extends SQLiteOpenHelper {

    private static final String TABLE_NAME = "player_table";
    private static final String NAME = "name";
    private static final String ID = "ID";


    public DatabaseController(Context context) {
        super(context, TABLE_NAME, null, 1);

    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        // Creates Table and assign ID to name
        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER, " +
                NAME + " TEXT)";
        sqLiteDatabase.execSQL(createTable);

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }


    // Adds data and return -1 if something went wrong
    public boolean addData(int id, String item) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME, item);
        contentValues.put(ID, id);

        long result = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);

        if (result == -1) {
            return false;
        } else {
            return true;
        }


    }

    public Cursor getData(int id) {

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor data = db.rawQuery("select * from " + TABLE_NAME + " where id=" + id + "", null);

        return data;


    }

    public boolean resetTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_NAME);
        return true;
    }

    public boolean updatePlayer(int id, String newName) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME, newName);
        db.update(TABLE_NAME, contentValues, ID + " = ? ",
                new String[]{String.valueOf(id)});
        return true;
    }

}
