package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

public class Setting extends AppCompatActivity {

    DBHelper dbHelper = new DBHelper(this);
    MediaPlayer mp=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        mp= MediaPlayer.create(this, R.raw.setting);
        mp.setLooping(true);
        mp.start();

        Switch audioSwitch = (Switch) findViewById(R.id.audioSwitch);
        AudioManager  amanager =(AudioManager)getSystemService(Context.AUDIO_SERVICE);
        audioSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    amanager.setStreamMute(AudioManager.STREAM_MUSIC, false);
                } else {
                    amanager.setStreamMute(AudioManager.STREAM_MUSIC, true);
                }
            }
        });

        Button resetWinsLosts = findViewById(R.id.resetWinsLosts);
        resetWinsLosts.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                db.execSQL("update player_table set wins=0, losts=0 where id>0");
                Toast.makeText(Setting.this, "Đặt lại thành công.", Toast.LENGTH_SHORT).show();
            }
        });


        Button resetAll = findViewById(R.id.resetAll);
        resetAll.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                db.execSQL("delete from player_table");
                Toast.makeText(Setting.this, "Đặt lại thành công.", Toast.LENGTH_SHORT).show();
            }
        });
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
    @Override
    protected void onResume() {
        super.onResume();
        if (mp != null) {
            mp.start();
        }
    }
}