package com.example.bojun.test;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import static com.example.bojun.test.Menu.lgn_id;
import static com.example.bojun.test.Menu.lgn_name;

import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;


public class Matching_write extends AppCompatActivity {
    String sp1,sp2,timesp;
    String text=null;
    EditText editText;
    String[] list_menu = {"택시", "운동", "식사", "과팅"};
    String[] list_people = {"1명", "2명", "3명", "4명", "5명"};
    String[] list_time={"12pm","1pm","2pm","3pm","4pm","5pm","6pm","7pm","8pm","9pm","10pm", "11pm","12am","1am","2am","3am","4am","5am","6am","7am","8am","9am","10am","11am"};

  private void addList()
  {
      if(lgn_id!=null) {
          if (sp1.equals(list_menu[0])) {
              insertToDatabase(lgn_name, sp2, timesp, text);
              Toast.makeText(getApplicationContext(), "택시 게시판에 글을 등록하였습니다", Toast.LENGTH_SHORT).show();
          } else if (sp1.equals(list_menu[1])) {
              insertToDatabase2(lgn_name, sp2, timesp, text);
              Toast.makeText(getApplicationContext(), "운동 게시판에 글을 등록하였습니다", Toast.LENGTH_SHORT).show();
          } else if (sp1.equals(list_menu[2])) {
              insertToDatabase3(lgn_name, sp2, timesp, text);
              Toast.makeText(getApplicationContext(), "식사 게시판에 글을 등록하였습니다", Toast.LENGTH_SHORT).show();
          } else if (sp1.equals(list_menu[3])) {
              insertToDatabase4(lgn_name, sp2, timesp, text);
              Toast.makeText(getApplicationContext(), "과팅 게시판에 글을 등록하였습니다", Toast.LENGTH_SHORT).show();
          }
      }
      else {
          Toast.makeText(getApplicationContext(), "로그인을 먼저 해주세요.", Toast.LENGTH_SHORT).show();
          Intent intent = new Intent(getApplicationContext(), Menu.class);
          startActivity(intent);
      }
    }
    public void click(View v)
    {
        text=editText.getText().toString();
        addList();
        finish();
    }
    public void exit(View v)
    {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matching_write);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(0xFF90EE90));
        setTitle("게시글 작성");

            editText = (EditText) findViewById(R.id.taxi_edit);


            final Spinner spineer1 = (Spinner) findViewById(R.id.spinner);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list_menu);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spineer1.setAdapter(adapter);
            spineer1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int postition, long id) {
                    sp1 = (String) spineer1.getSelectedItem();
                }

                public void onNothingSelected(AdapterView<?> parent) {
                }
            });

            final Spinner spineer2 = (Spinner) findViewById(R.id.spinner2);
            ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list_people);
            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spineer2.setAdapter(adapter2);
            spineer2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int postition, long id) {
                    sp2 = (String) spineer2.getSelectedItem();
                }

                public void onNothingSelected(AdapterView<?> parent) {
                }
            });

            final Spinner time_spinner = (Spinner) findViewById(R.id.time_spinnerr);
            ArrayAdapter<String> time_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list_time);
            time_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            time_spinner.setAdapter(time_adapter);
            time_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int postition, long id) {
                    timesp = (String) time_spinner.getSelectedItem();
                }

                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        }
    private void insertToDatabase(String name, String number, String time, String other){

        class InsertData extends AsyncTask<String, Void, String>{
            ProgressDialog loading;


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Matching_write.this, "Please Wait", null, true, true); //다이어로그 생성
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
            }

            @Override // 데이터베이스로 데이터를 옮겨주는 메소드
            protected String doInBackground(String... params) {

                try{
                    String name = (String)params[0];
                    String number = (String)params[1];
                    String time = (String)params[2];
                    String other = (String)params[3];

                    String link="http://210.115.229.176:80/insert_php1.php"; //http://접속할 주소:포트번호/php파일
                    String data  = URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8"); // name필드에 있는 값 가지고 오기
                    data += "&" + URLEncoder.encode("number", "UTF-8") + "=" + URLEncoder.encode(number, "UTF-8");
                    data += "&" + URLEncoder.encode("time", "UTF-8") + "=" + URLEncoder.encode(time, "UTF-8");
                    data += "&" + URLEncoder.encode("other", "UTF-8") + "=" + URLEncoder.encode(other, "UTF-8");

                    URL url = new URL(link); // URL에 내가 지정해준 주소로 간다.
                    URLConnection conn = url.openConnection(); //주소를 통해서 열어준다.

                    conn.setDoOutput(true);
                    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream()); //데이터 쓰기

                    wr.write( data );
                    wr.flush();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                    StringBuilder sb = new StringBuilder();
                    String line = null;

                    // Read Server Response
                    while((line = reader.readLine()) != null)
                    {
                        sb.append(line);
                        break;
                    }
                    return sb.toString();
                }
                catch(Exception e){
                    return new String("Exception: " + e.getMessage());
                }

            }
        }

        InsertData task = new InsertData();
        task.execute(name,number,time,other); // 데이터를 데이터 베이스에 넣겠다.
    }
    private void insertToDatabase2(String name, String number, String time, String other){

        class InsertData extends AsyncTask<String, Void, String>{
            ProgressDialog loading;


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Matching_write.this, "Please Wait", null, true, true); //다이어로그 생성
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
            }

            @Override // 데이터베이스로 데이터를 옮겨주는 메소드
            protected String doInBackground(String... params) {

                try{
                    String name = (String)params[0];
                    String number = (String)params[1];
                    String time = (String)params[2];
                    String other = (String)params[3];

                    String link="http://210.115.229.176:80/insert_php2.php"; //http://접속할 주소:포트번호/php파일
                    String data  = URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8"); // name필드에 있는 값 가지고 오기
                    data += "&" + URLEncoder.encode("number", "UTF-8") + "=" + URLEncoder.encode(number, "UTF-8");
                    data += "&" + URLEncoder.encode("time", "UTF-8") + "=" + URLEncoder.encode(time, "UTF-8");
                    data += "&" + URLEncoder.encode("other", "UTF-8") + "=" + URLEncoder.encode(other, "UTF-8");

                    URL url = new URL(link); // URL에 내가 지정해준 주소로 간다.
                    URLConnection conn = url.openConnection(); //주소를 통해서 열어준다.

                    conn.setDoOutput(true);
                    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream()); //데이터 쓰기

                    wr.write( data );
                    wr.flush();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                    StringBuilder sb = new StringBuilder();
                    String line = null;

                    // Read Server Response
                    while((line = reader.readLine()) != null)
                    {
                        sb.append(line);
                        break;
                    }
                    return sb.toString();
                }
                catch(Exception e){
                    return new String("Exception: " + e.getMessage());
                }

            }
        }

        InsertData task = new InsertData();
        task.execute(name,number,time,other); // 데이터를 데이터 베이스에 넣겠다.
    }
    private void insertToDatabase3(String name, String number, String time, String other){

        class InsertData extends AsyncTask<String, Void, String>{
            ProgressDialog loading;


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Matching_write.this, "Please Wait", null, true, true); //다이어로그 생성
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
            }

            @Override // 데이터베이스로 데이터를 옮겨주는 메소드
            protected String doInBackground(String... params) {

                try{
                    String name = (String)params[0];
                    String number = (String)params[1];
                    String time = (String)params[2];
                    String other = (String)params[3];

                    String link="http://210.115.229.176:80/insert_php3.php"; //http://접속할 주소:포트번호/php파일
                    String data  = URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8"); // name필드에 있는 값 가지고 오기
                    data += "&" + URLEncoder.encode("number", "UTF-8") + "=" + URLEncoder.encode(number, "UTF-8");
                    data += "&" + URLEncoder.encode("time", "UTF-8") + "=" + URLEncoder.encode(time, "UTF-8");
                    data += "&" + URLEncoder.encode("other", "UTF-8") + "=" + URLEncoder.encode(other, "UTF-8");

                    URL url = new URL(link); // URL에 내가 지정해준 주소로 간다.
                    URLConnection conn = url.openConnection(); //주소를 통해서 열어준다.

                    conn.setDoOutput(true);
                    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream()); //데이터 쓰기

                    wr.write( data );
                    wr.flush();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                    StringBuilder sb = new StringBuilder();
                    String line = null;

                    // Read Server Response
                    while((line = reader.readLine()) != null)
                    {
                        sb.append(line);
                        break;
                    }
                    return sb.toString();
                }
                catch(Exception e){
                    return new String("Exception: " + e.getMessage());
                }

            }
        }

        InsertData task = new InsertData();
        task.execute(name,number,time,other); // 데이터를 데이터 베이스에 넣겠다.
    }
    private void insertToDatabase4(String name, String number, String time, String other){

        class InsertData extends AsyncTask<String, Void, String>{
            ProgressDialog loading;


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Matching_write.this, "Please Wait", null, true, true); //다이어로그 생성
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
            }

            @Override // 데이터베이스로 데이터를 옮겨주는 메소드
            protected String doInBackground(String... params) {

                try{
                    String name = (String)params[0];
                    String number = (String)params[1];
                    String time = (String)params[2];
                    String other = (String)params[3];

                    String link="http://210.115.229.176:80/insert_php4.php"; //http://접속할 주소:포트번호/php파일
                    String data  = URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8"); // name필드에 있는 값 가지고 오기
                    data += "&" + URLEncoder.encode("number", "UTF-8") + "=" + URLEncoder.encode(number, "UTF-8");
                    data += "&" + URLEncoder.encode("time", "UTF-8") + "=" + URLEncoder.encode(time, "UTF-8");
                    data += "&" + URLEncoder.encode("other", "UTF-8") + "=" + URLEncoder.encode(other, "UTF-8");

                    URL url = new URL(link); // URL에 내가 지정해준 주소로 간다.
                    URLConnection conn = url.openConnection(); //주소를 통해서 열어준다.

                    conn.setDoOutput(true);
                    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream()); //데이터 쓰기

                    wr.write( data );
                    wr.flush();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                    StringBuilder sb = new StringBuilder();
                    String line = null;

                    // Read Server Response
                    while((line = reader.readLine()) != null)
                    {
                        sb.append(line);
                        break;
                    }
                    return sb.toString();
                }
                catch(Exception e){
                    return new String("Exception: " + e.getMessage());
                }

            }
        }

        InsertData task = new InsertData();
        task.execute(name,number,time,other); // 데이터를 데이터 베이스에 넣겠다.
    }
}




