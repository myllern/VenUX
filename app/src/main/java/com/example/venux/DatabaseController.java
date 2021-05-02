package com.example.venux;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseController extends SQLiteOpenHelper {

    private static final String TABLE_NAME_PLAYER = "player_table";
    private static final String TABLE_NAME_MOVES = "moves_table";
    private static final String NAME = "name";
    private static final String ID = "ID";

    public DatabaseController(Context context) {
        super(context, TABLE_NAME_PLAYER, null,1);

    }

        @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Creates Table
        String createPlayerTable = "CREATE TABLE " + TABLE_NAME_PLAYER + " (ID INTEGER, " + NAME +" TEXT)";
        String createMovesTable = "CREATE TABLE " + TABLE_NAME_MOVES + " (ID INTEGER, " + NAME +" TEXT)";
        sqLiteDatabase.execSQL(createPlayerTable);
        sqLiteDatabase.execSQL(createMovesTable);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_PLAYER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_MOVES);
        onCreate(db);
    }

    // Adds data and return -1 if something went wrong

    public boolean addPlayer(int id, String item){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME, item);
        contentValues.put(ID, id);

        long result = sqLiteDatabase.insert(TABLE_NAME_PLAYER, null, contentValues);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public String getPlayer(int id){

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("select * from " + TABLE_NAME_PLAYER + " where id="+id+"", null);
        ArrayList<String> listData = new ArrayList<>();
        while(data.moveToNext()){
            // Adds player 1 to list
            listData.add(data.getString(1));
        }
        //create the list adapter and set the adapter
        return(listData.get(0));
    }

    public void resetTable(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ TABLE_NAME_PLAYER);
    }

    public boolean updatePlayer(int id, String newName){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME,newName);
        db.update(TABLE_NAME_PLAYER, contentValues, ID + " = ? " ,
                new String[]{String.valueOf(id)});
        return true;
    }

}
