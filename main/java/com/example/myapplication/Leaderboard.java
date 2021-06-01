package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.ListView;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

public class Leaderboard extends AppCompatActivity {

    MediaPlayer mp=null;
    DBHelper dbHelper = new DBHelper(this);
    SQLiteDatabase db;
    private ArrayList<String> Id = new ArrayList<String>();
    private ArrayList<String> Name = new ArrayList<String>();
    private ArrayList<Bitmap> Image = new ArrayList<Bitmap>();
    private ArrayList<String> Wins = new ArrayList<String>();
    private ArrayList<String> Losts = new ArrayList<String>();
    private ArrayList<String> Cash = new ArrayList<String>();

    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);
        lv = (ListView) findViewById(R.id.board);
        mp= MediaPlayer.create(this, R.raw.leaderboard);
        mp.setLooping(true);
        mp.setVolume(0.4f,0.4f);
        mp.start();
    }
    @Override
    protected void onResume() {
        displayData();
        super.onResume();
        if (mp != null) {
            mp.start();
        }
    }
    private void displayData() {
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select id, name, image, wins, losts, (wins-losts)*1000000 from player_table order by (wins-losts)*1000000 desc", null );
        Id.clear();
        Name.clear();
        Image.clear();
        Wins.clear();
        Losts.clear();
        Cash.clear();
        if (cursor.moveToFirst()) {
            do {
                Id.add(cursor.getString(cursor.getColumnIndex("id")));
                Name.add(cursor.getString(cursor.getColumnIndex("name")));

                byte[] imageByteArray=cursor.getBlob(cursor.getColumnIndex("image"));
                ByteArrayInputStream imageStream = new ByteArrayInputStream(imageByteArray);
                Image.add(BitmapFactory.decodeStream(imageStream));

                Wins.add(cursor.getString(cursor.getColumnIndex("wins")));
                Losts.add(cursor.getString(cursor.getColumnIndex("losts")));
                Cash.add(cursor.getString(cursor.getColumnIndex("(wins-losts)*1000000")));
            } while (cursor.moveToNext());
        }
        PlayerAdapter pa = new PlayerAdapter(Leaderboard.this, Id, Name, Image, Wins, Losts, Cash);
        lv.setAdapter(pa);
        //code to set adapter to populate list
        cursor.close();
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        if (mp != null) {
            mp.pause();
            mp.seekTo(0);
        }
    }
    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        mp.stop();
    }

}

