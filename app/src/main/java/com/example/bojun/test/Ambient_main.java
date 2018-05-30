package com.example.bojun.test;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;


public class Ambient_main extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ambient_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(0xFFFFB6C1));
        setTitle("주변");

        Button score_btn = (Button) findViewById(R.id.score_move);
        score_btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(), Ambient_weather.class);
                startActivity(intent);
            }
        });

        Button homework_btn = (Button) findViewById(R.id.homework_move);
        homework_btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(), Ambient_map.class);
                startActivity(intent);
            }
        });


        Button schoolbus_btn = (Button) findViewById(R.id.schoolbus_move);
        schoolbus_btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(), Ambient_schoolbus.class);
                startActivity(intent);
            }
        });
    }
}
