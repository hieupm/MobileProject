package com.example.myapplication;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;


public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "BangDB";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "player_table";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_IMAGE = "image";
    public static final String COLUMN_WINS = "wins";
    public static final String COLUMN_LOSTS = "lost";

    public DBHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table player_table " +
                "(id integer primary key autoincrement, name text, image blob, wins integer, losts integer)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS player_table");
        onCreate(db);
    }

    public void createPlayer (String name, byte[] image) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", name);
        cv.put("image", image);
        cv.put("wins",0);
        cv.put("losts",0);
        db.insert(TABLE_NAME, null, cv);
        db.close();
    }

    public ArrayList<String> getAllId() {
        ArrayList<String> array_list = new ArrayList<String>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from player_table", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(COLUMN_ID)));
            res.moveToNext();
        }
        return array_list;
    }

    public Cursor getNamebyID(String id1){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select name from player_table where id="+id1, null );
        return res;
    }

    public Cursor getImagebyID(String id1){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select image from player_table where id="+id1, null );
        return res;
    }

    public Cursor getCashbyID(String id1){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select (wins-losts)*1000000 from player_table where id="+id1, null );
        return res;
    }
}