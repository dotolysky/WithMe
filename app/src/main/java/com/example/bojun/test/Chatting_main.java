package com.example.bojun.test;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;

public class Chatting_main extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting_main);
        setTitle("채팅");

        Button taxi_btn2 = (Button) findViewById(R.id.taxi_chat);
        taxi_btn2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(), Chatting_taxi.class);
                startActivity(intent);
            }
        });
        Button exercise_btn2 = (Button) findViewById(R.id.exercise_chat);
        exercise_btn2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(), Chatting_Exercise.class);
                startActivity(intent);
            }
        });
        Button meal_btn2 = (Button) findViewById(R.id.meal_chat);
        meal_btn2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(), Chatting_meal.class);
                startActivity(intent);
            }
        });
        Button majorting_btn2 = (Button) findViewById(R.id.majorting_chat);
        majorting_btn2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(), Chatting_majorting.class);
                startActivity(intent);
            }
        });

    }
}
