package com.example.teamnullpointer.ridesharenp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;




public class DataBaseOperation extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Rideshare_Local_DB.db";
    public static final String TABLE_NAME = "Rideshare_table";
    public static final String COL_1 = "USERNAME";
    public static final String COL_2 = "PASSWORD";
    public static final String COL_3 = "TOS";
    public static final String COL_4 = "REMEMBERME";


    public DataBaseOperation(Context context){
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("create table " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, USERNAME TEXT, PASSWORD TEXT, TOS TEXT, REMEMBERME TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String username, String password, String tos, String rememberme) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, username);
        contentValues.put(COL_2, password);
        contentValues.put(COL_3, tos);
        contentValues.put(COL_4, rememberme);
        long result = db.insert(TABLE_NAME, null, contentValues);
        if(result == -1){
            return false;
        }
        return true;
    }

    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return res;
    }

    public void restart(){
        SQLiteDatabase db = this.getWritableDatabase();
        onUpgrade(db,1,2);
    }

    public void deleteUser(String ID){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, "ID = ?", new String[]{ID});
    }

    public int findUserID(String username){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = getAllData();
        String un;
        int id = -1;
        if(res.moveToFirst()){
            do {
                un = res.getString(1).toString();
                if(un.equals(username)){
                    id = res.getInt(0);
                }
            } while(res.moveToNext());
        }
        return id;
    }

    public int getUserCount(){
        String countQuery = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        // return count
        return cursor.getCount();
    }
}
