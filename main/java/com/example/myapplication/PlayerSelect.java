package com.example.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

public class PlayerSelect extends AppCompatActivity {
    MediaPlayer mp1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_select);

        Spinner spinner1 = (Spinner) findViewById(R.id.spinner1);
        Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);
        TextView textView1 = (TextView) findViewById(R.id.textView1);
        TextView textView2 = (TextView) findViewById(R.id.textView2);
        ImageView imageView1 = (ImageView) findViewById(R.id.imageView1);
        ImageView imageView2 = (ImageView) findViewById(R.id.imageView2);
        TextView textCash1 = (TextView) findViewById(R.id.textCash1);
        TextView textCash2 = (TextView) findViewById(R.id.textCash2);
        Button bangButton = findViewById(R.id.bangButton);

        ImageView versus = (ImageView) findViewById(R.id.versus);

        DBHelper dbHelper = new DBHelper(this);

        mp1= MediaPlayer.create(this, R.raw.playerselect);
        mp1.setVolume(0.4f,0.4f);
        mp1.setLooping(true);
        mp1.start();

        ArrayList id_array = dbHelper.getAllId();
        id_array.add(0, "ID");
        ArrayAdapter adapter = new ArrayAdapter(this,  android.R.layout.simple_spinner_dropdown_item, id_array);
        spinner1.setAdapter(adapter);
        spinner2.setAdapter(adapter);

        MediaPlayer mp2 = MediaPlayer.create(this, R.raw.leesin);
        mp2.setVolume(0.5f,0.5f);
        MediaPlayer mp3 = MediaPlayer.create(this,R.raw.highnoon);

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                if (spinner1.getItemAtPosition(pos).toString()!="ID")
                {
                    Cursor res = dbHelper.getNamebyID(spinner1.getItemAtPosition(pos).toString());
                    if (res.moveToFirst())
                        textView1.setText(res.getString(0));
                    res = dbHelper.getImagebyID(spinner1.getItemAtPosition(pos).toString());
                    if (res.moveToFirst())
                    {
                        byte[] imageByteArray=res.getBlob(0);
                        ByteArrayInputStream imageStream = new ByteArrayInputStream(imageByteArray);
                        imageView1.setImageBitmap(BitmapFactory.decodeStream(imageStream));
                    }
                    res = dbHelper.getCashbyID(spinner1.getItemAtPosition(pos).toString());
                    if (res.moveToFirst())
                        textCash1.setText("Tổng tiền thưởng: " + res.getString(0)+" đồng");
                    mp2.start();
                }
                if (spinner1.getSelectedItem().toString()!="ID" && spinner2.getSelectedItem().toString()!="ID"){
                    versus.setImageResource(R.drawable.versus0);
                    bangButton.setBackgroundColor(0xff800000);
                }
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                if (spinner2.getItemAtPosition(pos).toString()!="ID") {
                    Cursor res = dbHelper.getNamebyID(spinner2.getItemAtPosition(pos).toString());
                    if (res.moveToFirst())
                        textView2.setText(res.getString(0));
                    res = dbHelper.getImagebyID(spinner2.getItemAtPosition(pos).toString());
                    if (res.moveToFirst()) {
                        byte[] imageByteArray = res.getBlob(0);
                        ByteArrayInputStream imageStream = new ByteArrayInputStream(imageByteArray);
                        imageView2.setImageBitmap(BitmapFactory.decodeStream(imageStream));
                    }
                    res = dbHelper.getCashbyID(spinner2.getItemAtPosition(pos).toString());
                    if (res.moveToFirst())
                        textCash2.setText("Tổng tiền thưởng: " + res.getString(0) + " đồng");
                    mp2.start();
                    if (spinner1.getSelectedItem().toString() != "ID" && spinner2.getSelectedItem().toString() != "ID") {
                        versus.setImageResource(R.drawable.versus0);
                        bangButton.setBackgroundColor(0xffb30000);
                    }
                }
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        bangButton.setOnClickListener(new View.OnClickListener( ) {
            public void onClick(View v) {
                String id1 = spinner1.getSelectedItem().toString();
                String id2 = spinner2.getSelectedItem().toString();
                Intent bangIntent = new Intent(PlayerSelect.this, Bang.class);
                bangIntent.putExtra("ID1",id1);
                bangIntent.putExtra("ID2",id2);
                Handler handler = new Handler();
                mp3.start();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        startActivity(bangIntent);
                    }
                }, 3000);   //2second
            }
        });
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