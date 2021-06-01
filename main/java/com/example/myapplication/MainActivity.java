package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    MediaPlayer mp=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mp= MediaPlayer.create(this, R.raw.maintheme);
        mp.setLooping(true);
        mp.setVolume(0.2f,0.2f);
        mp.start();

        Button playButton = findViewById(R.id.playButton);
        playButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent playerSelectIntent = new Intent(MainActivity.this, PlayerSelect.class);
                startActivity(playerSelectIntent);
            }
        });

        Button createPlayerButton = findViewById(R.id.createPlayerButton);
        createPlayerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent playerCreateIntent = new Intent(MainActivity.this, PlayerCreate.class);
                startActivity(playerCreateIntent);
            }
        });

        Button leaderboardButton = findViewById(R.id.leaderboardButton);
        leaderboardButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent leaderboardIntent = new Intent(MainActivity.this, Leaderboard.class);
                startActivity(leaderboardIntent);
            }
        });

        Button settingButton = findViewById(R.id.settingButton);
        settingButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent settingIntent = new Intent(MainActivity.this, Setting.class);
                startActivity(settingIntent);
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