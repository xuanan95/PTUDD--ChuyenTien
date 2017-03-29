package com.example.buixuanan.moneyconvert.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.ArrayList;
import java.util.List;


public class MyDatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "SQLite";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "MoneyConvert";
    private static final String TABLE_NAME = "Rate";


    public MyDatabaseHelper(Context context){
        super(context,DATABASE_NAME,null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        Log.i(TAG,"Create");
        String script = "CREATE TABLE " + TABLE_NAME+"(Id INTEGER PRIMARY KEY,Date DATE, Price VARCHAR(25) )";
        db.execSQL(script);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        Log.i(TAG,"drop");

    }
    public void deleteData(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
    public void insertData(Note note){
        Log.i(TAG,"Insert");
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("Id",note.getId());
        values.put("Date",note.getDate());
        values.put("Price",note.getPrice());

        db.insert(TABLE_NAME, null, values);
        db.close();
    }
    public List<String> getData(){
        List<String> notes = new ArrayList<String>();
        String select = "SELECT * FROM " +TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(select,null);
        String a;

        if(cursor.moveToFirst()){
            do{
                a = cursor.getString(2);
                notes.add(a);
            }while (cursor.moveToNext());
        }

        cursor.close();
        return notes;
    }
    public String getDate(){
        String select = "SELECT Date FROM "+TABLE_NAME+" WHERE Id = 1";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(select,null);
        String date ="";
        if(cursor.moveToFirst()){
            date = cursor.getString(0);
        }
        cursor.close();
        return date;
    }
    public int updateData(Note note){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("Date",note.getDate());
        values.put("Price",note.getPrice());

        return db.update(TABLE_NAME,values,"Id = "+note.getId(),null);

    }
}
