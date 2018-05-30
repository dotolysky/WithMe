package com.example.bojun.test;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import static com.example.bojun.test.Menu.lgn_id;
import static com.example.bojun.test.Menu.lgn_name;

public class Message_send extends AppCompatActivity {
    Button send;
    EditText msge,rec_name;
    TextView receiver;

    static String rec;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_send);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(0xFF90EE90));
        setTitle("메세지 전송");
        send = (Button)findViewById(R.id.send);
        msge = (EditText)findViewById(R.id.msg);
        rec_name = (EditText)findViewById(R.id.receiver_name);
        rec_name.setText(rec);
        send.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                String msg = msge.getText().toString();
                insertToDatabase5(lgn_name,msg,rec);
                finish();
            }
        });
    }
    private void insertToDatabase5(String name, String message, String recevier) {

        class InsertData extends AsyncTask<String, Void, String> {
            ProgressDialog loading;


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Message_send.this, "Please Wait", null, true, true); //다이어로그 생성
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
            }

            @Override // 데이터베이스로 데이터를 옮겨주는 메소드
            protected String doInBackground(String... params) {

                try {
                    String name = (String) params[0];
                    String message = (String) params[1];
                    String receiver = (String) params[2];

                    String link = "http://210.115.229.176:80/insert1.php"; //http://접속할 주소:포트번호/php파일
                    String data = URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8"); // name필드에 있는 값 가지고 오기
                    data += "&" + URLEncoder.encode("message", "UTF-8") + "=" + URLEncoder.encode(message, "UTF-8");
                    data += "&" + URLEncoder.encode("receiver", "UTF-8") + "=" + URLEncoder.encode(receiver, "UTF-8");

                    URL url = new URL(link); // URL에 내가 지정해준 주소로 간다.
                    URLConnection conn = url.openConnection(); //주소를 통해서 열어준다.

                    conn.setDoOutput(true);
                    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream()); //데이터 쓰기

                    wr.write(data);
                    wr.flush();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                    StringBuilder sb = new StringBuilder();
                    String line = null;

                    // Read Server Response
                    while ((line = reader.readLine()) != null) {
                        sb.append(line);
                        break;
                    }
                    return sb.toString();
                } catch (Exception e) {
                    return new String("Exception: " + e.getMessage());
                }

            }
        }
        InsertData task = new InsertData();
        task.execute(name,message,recevier);
    }
}
