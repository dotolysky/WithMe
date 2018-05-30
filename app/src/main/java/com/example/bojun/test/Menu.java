package com.example.bojun.test;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;

public class Menu extends AppCompatActivity {
    EditText idText;
    EditText passText;
    String Id;
    String Pass;
    static String lgn_id ;
    static String lgn_pass;
    static String lgn_name;
    String myJSON;

    private static final String TAG_RESULTS="result";
    private static final String TAG_NAME = "name";
    private static final String TAG_PASSWORD = "password";
    private static final String TAG_NUM = "num";
    JSONArray peoples = null;
    ArrayList<HashMap<String, String>> personList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        setTitle("로그인");


        idText = (EditText) findViewById(R.id.id);
        passText = (EditText) findViewById(R.id.pass);
        Button login_btn = (Button) findViewById(R.id.login);
        login_btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Id= idText.getText().toString();
                Pass = passText.getText().toString();
                personList = new ArrayList<HashMap<String, String>>();
                getData("http://210.115.229.176:80/getdata.php");
            }
        });
        Button register_btn = (Button) findViewById(R.id.register);
        register_btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(),Member.class);
                startActivity(intent);
            }
        });
        Button help_btn = (Button) findViewById(R.id.help);
        help_btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(),Help.class);
                startActivity(intent);
            }
        });
    }
    public void login() {
        try{
            JSONObject jsonobj = new JSONObject(myJSON);
            peoples = jsonobj.getJSONArray(TAG_RESULTS);
            boolean flag = false;
            for(int i=0;i<peoples.length();i++){
                JSONObject c = peoples.getJSONObject(i);
                String name = c.getString(TAG_NAME);
                String password = c.getString(TAG_PASSWORD);
                String num = c.getString(TAG_NUM);


                if(Id.equals(num)){
                    if(Pass.equals(password)){
                        lgn_id = num; lgn_name = name; lgn_pass = password;
                        flag=true;
                    }
                }
            }
            if(flag==true){
                Toast.makeText(getApplicationContext(), lgn_name + "님 환영합니다.", Toast.LENGTH_SHORT).show();
                Intent main = new Intent(getApplication(), MainActivity.class);
                startActivity(main);
            }
            else{
                Toast.makeText(getApplicationContext(), "ID나 비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                idText.setText(""); passText.setText("");
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
}
