package com.example.bojun.test;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Matchingoption_anything extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matchingoption_anything);
        AlertDialog.Builder builder = new AlertDialog.Builder(Matchingoption_anything.this);
        builder.setTitle("안내");
        builder.setMessage("선택하신 조건이 있습니다.");
        builder.setPositiveButton("메세지 전송", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(getApplicationContext(), Message_send.class);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("글 등록", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(getApplicationContext(), Matching_write.class);
                startActivity(intent);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
