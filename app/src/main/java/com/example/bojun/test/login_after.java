package com.example.bojun.test;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.bojun.test.Menu.lgn_id;
import static com.example.bojun.test.Menu.lgn_name;
import static com.example.bojun.test.Menu.lgn_pass;

public class login_after extends AppCompatActivity {
    TextView welcome ;
    Button move_help,logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_after);
        welcome = (TextView)findViewById(R.id.welcome);
        welcome.setText(lgn_name+"님 환영합니다");
        Button move_help = (Button) findViewById(R.id.move_help);
        Button move_message = (Button)findViewById(R.id.move_message);
        Button logout = (Button)findViewById(R.id.logout);
        move_help.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(), Help.class);
                startActivity(intent);
            }
        });
        move_message.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(), Message_receive.class);
                startActivity(intent);
            }
        });
        logout.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Toast.makeText(getApplicationContext(),lgn_name+"님 로그아웃 하셨습니다.", Toast.LENGTH_SHORT).show();
                lgn_name=null; lgn_id=null; lgn_pass=null;
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
