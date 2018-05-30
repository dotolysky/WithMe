package com.example.bojun.test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.net.Uri;
import android.widget.ViewFlipper;

import android.view.View.OnClickListener;

import static com.example.bojun.test.Menu.lgn_name;

public class MainActivity extends Activity implements  OnClickListener  {


    Button  one, two, tree;
    ViewFlipper view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        one = (Button) findViewById(R.id.button5);
        two = (Button) findViewById(R.id.button6);
        tree =(Button) findViewById(R.id.button7);
        view = (ViewFlipper) findViewById(R.id.viewFlipper1);

        one.setOnClickListener(this);
        two.setOnClickListener(this);
        tree.setOnClickListener(this);


        // 페이지 넘기는 구간시간 설정 1000 = 1초
        view.setFlipInterval(1000);


        Button menu_btn = (Button) findViewById(R.id.menu_move);
        menu_btn.setOnClickListener(new OnClickListener(){
            public void onClick(View v){
                if(lgn_name==null) {
                    Intent intent = new Intent(getApplicationContext(), Menu.class);
                    startActivity(intent);
                }
                else{
                    Intent intent = new Intent(getApplicationContext(), login_after.class);
                    startActivity(intent);
                }

            }
        });

        Button matching_btn = (Button) findViewById(R.id.matching_move);
        matching_btn.setOnClickListener(new OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(), Matching_main.class);
                startActivity(intent);
                }
        });

        Button haksa_btn = (Button) findViewById(R.id.school_etc_move);
        haksa_btn.setOnClickListener(new OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(), Ambient_main.class);
                startActivity(intent);
            }
        });

        Button chatting_btn = (Button) findViewById(R.id.chat_move);
        chatting_btn.setOnClickListener(new OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(), Chatting_main.class);
                startActivity(intent);
            }
        });
        view.startFlipping();

    }
    public void onClick(View v) {
        // TODO Auto-generated method stub


        if(v==one){
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://smart.hallym.ac.kr/index.jsp")));
        }

        else if(v==two){
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://chonghak.hallym.ac.kr/")));
        }
        else if(v==tree){
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://cafe.daum.net/hallymlike")));
        }
    }

}
