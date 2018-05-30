package com.example.bojun.test;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;


public class Matching_main extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matching_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(0xFF90EE90));
        setTitle("매칭");

        Button taxi_btn = (Button) findViewById(R.id.taxi_move);
        taxi_btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(), Matching_taxi.class);
                startActivity(intent);
            }
        });

        Button exercise_btn = (Button) findViewById(R.id.exercise_move);
        exercise_btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(), Matching_exercise.class);
                startActivity(intent);
            }
        });

        Button eat_btn = (Button) findViewById(R.id.eat_move);
        eat_btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(), Matching_meal.class);
                startActivity(intent);
            }
        });

        Button majorting_btn = (Button) findViewById(R.id.majorting_move);
        majorting_btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(), Matching_majorting.class);
                startActivity(intent);
            }
        });

        Button matching_btn = (Button) findViewById(R.id.matching_button);
        matching_btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(Matching_main.this, Matching_optioncheck.class);
                startActivity(intent);
            }
        });

    }
}
