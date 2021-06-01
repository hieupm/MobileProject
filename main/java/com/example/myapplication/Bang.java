package com.example.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.os.Handler;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.io.ByteArrayInputStream;
import java.net.URI;


public class Bang extends AppCompatActivity {
    MediaPlayer mp1=null;
    MediaPlayer mpexplosion=null;
    int check=0;
    Button leftScreen;
    Button rightScreen;
    TextView announcement;
    DBHelper dbHelper;
    SQLiteDatabase db;
    String id1;
    String id2;
    pl.droidsonroids.gif.GifImageView explosion1;
    pl.droidsonroids.gif.GifImageView explosion2;
    TextView change1;
    TextView change2;
    String name1,name2;
    Button returnButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bang);

        id1 = getIntent().getStringExtra("ID1");
        id2 = getIntent().getStringExtra("ID2");

        mp1= MediaPlayer.create(this, R.raw.wildwestbg);
        mp1.setLooping(true);
        mp1.start();

        MediaPlayer mpready = MediaPlayer.create(this, R.raw.ready);
        mpready.setVolume(0.5f,0.5f);
        MediaPlayer mpsteady = MediaPlayer.create(this, R.raw.steady);
        mpsteady.setVolume(0.5f,0.5f);
        MediaPlayer mpbang = MediaPlayer.create(this, R.raw.bang);
        mpbang.setVolume(1,1);
        MediaPlayer mpgun = MediaPlayer.create(this,R.raw.gunsfx);
        mpgun.setVolume(1,1);
        mpexplosion = MediaPlayer.create(this,R.raw.explosion);
        mpexplosion.setVolume(1,1);

        announcement = findViewById(R.id.announcement);
        leftScreen = findViewById(R.id.leftScreen);
        rightScreen = findViewById(R.id.rightScreen);
        returnButton = findViewById(R.id.returnButton);
        returnButton.setVisibility(View.GONE);

        change1 = findViewById(R.id.change1);
        change2 = findViewById(R.id.change2);


        ImageView avatar1 = (ImageView)findViewById(R.id.avatar1);
        ImageView avatar2 = (ImageView)findViewById(R.id.avatar2);
        explosion1 = (pl.droidsonroids.gif.GifImageView)findViewById(R.id.explosion1);
        explosion2 = (pl.droidsonroids.gif.GifImageView)findViewById(R.id.explosion2);
        explosion1.setVisibility(View.GONE);
        explosion2.setVisibility(View.GONE);


        dbHelper = new DBHelper(this);
        Cursor res = dbHelper.getImagebyID(id1);
        if (res.moveToFirst())
        {
            byte[] imageByteArray=res.getBlob(0);
            ByteArrayInputStream imageStream = new ByteArrayInputStream(imageByteArray);
            avatar1.setImageBitmap(BitmapFactory.decodeStream(imageStream));
        }
        res = dbHelper.getImagebyID(id2);
        if (res.moveToFirst())
        {
            byte[] imageByteArray=res.getBlob(0);
            ByteArrayInputStream imageStream = new ByteArrayInputStream(imageByteArray);
            avatar2.setImageBitmap(BitmapFactory.decodeStream(imageStream));
        }
        res = dbHelper.getNamebyID(id1);
        if (res.moveToFirst())
            name1=res.getString(0);
        res = dbHelper.getNamebyID(id2);
        if (res.moveToFirst())
            name2=res.getString(0);


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                if (check==0){
                    announcement.setText("READY");
                    mpready.start();
                }
            }
        }, 2500);   //2,5econd


        handler.postDelayed(new Runnable() {
            public void run() {
                if (check==0) {
                    announcement.setText("STEADY");
                    mpsteady.start();
                }
            }
        }, 4500);   //4,5 second

        int randomTime = (int)Math.floor(Math.random()*(3001)+6500);
        handler.postDelayed(new Runnable() {
            public void run() {
                if (check == 0) {
                    announcement.setText("BANG");
                    mpbang.start();
                    check = 1;
                }
            }
        }, randomTime);   //6,5-9,5 second


        leftScreen.setOnClickListener(new View.OnClickListener( ) {
            public void onClick(View v) {
                mpgun.start();
                if(check==0) explosion(1);
                else explosion(2);
            }
        });

        rightScreen.setOnClickListener(new View.OnClickListener( ) {
            public void onClick(View v) {
                mpgun.start();
                if(check==0) explosion(2);
                else explosion(1);
            }
        });

        returnButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent returnIntent = new Intent(Bang.this, MainActivity.class);
                startActivity(returnIntent);
            }
        });
    }

    public void explosion(int i)
    {
        check = 1;
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                mpexplosion.start();
                if (i==1)
                {
                    explosion1.setVisibility(View.VISIBLE);
                    announcement.setText(name2+" là người chiến thắng!!");
                    change1.setTextColor(0xAAff0000);
                    change2.setTextColor(0xAA00ff00);
                    change1.setText("-1000000");
                    change2.setText("+1000000");
                    db = dbHelper.getWritableDatabase();
                    db.execSQL("update player_table set losts=losts+1 where id="+id1);
                    db.execSQL("update player_table set wins=wins+1 where id="+id2);
                }

                if (i==2)
                {
                    explosion2.setVisibility(View.VISIBLE);
                    announcement.setText(name1+" là người chiến thắng!!");
                    change2.setTextColor(0xAAff0000);
                    change1.setTextColor(0xAA00ff00);
                    change1.setText("+1000000");
                    change2.setText("-1000000");
                    db = dbHelper.getWritableDatabase();
                    db.execSQL("update player_table set losts=losts+1 where id="+id2);
                    db.execSQL("update player_table set wins=wins+1 where id="+id1);
                }

                returnButton.setBackgroundColor(0xffb30000);
                returnButton.setVisibility(View.VISIBLE);
            }
        }, 1000);
    }
    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        if (mp1 != null) {
            mp1.pause();
            mp1.seekTo(0);
        }
    }
    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        mp1.stop();
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (mp1 != null) {
            mp1.start();
        }
    }
}