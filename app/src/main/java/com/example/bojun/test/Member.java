package com.example.bojun.test;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;


public class Member extends AppCompatActivity {

    EditText NAME, PASS, PASSSIGN, NUM;
    String Tname, Tpass, Tpasssign, Tnum;
    String myJSON;
    boolean dupid = true;
    private static final String TAG_RESULTS="result";
    private static final String TAG_NAME = "name";
    private static final String TAG_PASSWORD = "password";
    private static final String TAG_NUM = "num";
    JSONArray peoples = null;
    ArrayList<HashMap<String, String>> personList;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member);
        setTitle("회원가입");


        NAME = (EditText) findViewById(R.id.name);
        PASS = (EditText) findViewById(R.id.password);
        PASSSIGN = (EditText) findViewById(R.id.passsign);
        NUM = (EditText) findViewById(R.id.num);


        Button join = (Button) findViewById(R.id.join);
        join.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Tname = NAME.getText().toString();
                Tpass = PASS.getText().toString();
                Tpasssign = PASSSIGN.getText().toString();
                Tnum = NUM.getText().toString();


                if(Tname.length()<2)
                {
                    Toast.makeText(getApplicationContext(), "이름을 정확하게 입력해주세요.",
                            Toast.LENGTH_SHORT).show();
                }
                else if(Tpass.length()>8)
                {
                    Toast.makeText(getApplicationContext(), "비밀번호는 8자리 이하로 설정해주세요",
                            Toast.LENGTH_SHORT).show();
                }
                else if(Tnum.length()>8)
                {
                    Toast.makeText(getApplicationContext(), "학번은 8자리 이상으로 설정해주세요.",
                            Toast.LENGTH_SHORT).show();
                }
                else if(dupid==false)
                {
                    Toast.makeText(getApplicationContext(), "중복된 학번입니다.",
                            Toast.LENGTH_SHORT).show();
                }
                else if(Tpass==Tpasssign)
                {
                    Toast.makeText(getApplicationContext(), "비밀번호와 확인이 일치하지 않습니다.",
                            Toast.LENGTH_SHORT).show();
                }
                else
                {
                    insertToDatabase(Tname,Tpass,Tnum);
                    Toast.makeText(getApplication(), Tname + "님 회원가입을 축하합니다.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplication(), Menu.class);
                    startActivity(intent);
                }
        }
    });
    }
    public void login() {
        try{
            JSONObject jsonobj = new JSONObject(myJSON);
            peoples = jsonobj.getJSONArray(TAG_RESULTS);

            for(int i=0;i<peoples.length();i++)
            {
                JSONObject c = peoples.getJSONObject(i);
                String name = c.getString(TAG_NAME);
                String password = c.getString(TAG_PASSWORD);
                String num = c.getString(TAG_NUM);
                if(Tnum.equals(num))
                {
                    dupid = false;
                }
            }



        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void getData(String url){
        class GetDataJSON extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {

                String uri = params[0];

                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL(uri);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();

                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String json;
                    while((json = bufferedReader.readLine())!= null){
                        sb.append(json+"\n");
                    }

                    return sb.toString().trim();

                }catch(Exception e){
                    return null;
                }
            }
            @Override
            protected void onPostExecute(String result)
            {
                myJSON=result;
                login();
            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute(url);
    }
    private void insertToDatabase(String name, String password, String num){

        class InsertData extends AsyncTask<String, Void, String> {
            ProgressDialog loading;


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Member.this, "Please Wait", null, true, true); //다이어로그 생성
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
                    String password = (String)params[1];
                    String num = (String)params[2];

                    String link="http://210.115.229.176:80/insert.php"; //http://접속할 주소:포트번호/php파일
                    String data  = URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8"); // name필드에 있는 값 가지고 오기
                    data += "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
                    data += "&" + URLEncoder.encode("num", "UTF-8") + "=" + URLEncoder.encode(num, "UTF-8");

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
        task.execute(name,password,num); // 데이터를 데이터 베이스에 넣겠다.
    }
}


