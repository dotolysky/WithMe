package com.example.bojun.test;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import static com.example.bojun.test.Message_send.rec;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;


public class Matching_exercise extends AppCompatActivity {
    public static ListView exercise_list;
    String myJSON;

    private static final String TAG_RESULTS="result";
    private static final String TAG_NAME = "name";
    private static final String TAG_NUMBER = "number";
    private static final String TAG_TIME = "time";
    private static final String TAG_OTHER = "other";
    JSONArray peoples = null;

    ArrayList<HashMap<String, String>> personList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matching_exercise);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(0xFF90EE90));
        setTitle("운동 게시판");

        Button write_btn = (Button) findViewById(R.id.taxi_write);
        exercise_list = (ListView) findViewById(R.id.taxi_listview);
        personList = new ArrayList<HashMap<String,String>>();
        getData("http://210.115.229.176:80/getdata_php2.php"); //http://접속할 주소/php파일
        write_btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(Matching_exercise.this, Matching_write.class);
                startActivity(intent);   // 다른게시판에서 다른게시판 목록골라서 등록하면 오류걸리는거 해결하기
            }
        });
    }
    protected void showList(){
        try {
            JSONObject jsonObj = new JSONObject(myJSON);
            peoples = jsonObj.getJSONArray(TAG_RESULTS);

            for(int i=0;i<peoples.length();i++){
                JSONObject c = peoples.getJSONObject(i);
                String name = c.getString(TAG_NAME);
                String number = c.getString(TAG_NUMBER);
                String time = c.getString(TAG_TIME);
                String other = c.getString(TAG_OTHER);

                HashMap<String,String> persons = new HashMap<String,String>();
                persons.put(TAG_NAME,name);
                persons.put(TAG_NUMBER,number);
                persons.put(TAG_TIME,time);
                persons.put(TAG_OTHER,other);

                personList.add(persons);
            }

            ListAdapter adapter = new SimpleAdapter(
                    Matching_exercise.this, personList, R.layout.itme,
                    new String[]{TAG_NAME,TAG_NUMBER,TAG_TIME,TAG_OTHER},
                    new int[]{R.id.name, R.id.number, R.id.time, R.id.other}
            );
            exercise_list.setAdapter(adapter);
            exercise_list.setOnItemClickListener(
                    new ListView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String tv = exercise_list.getAdapter().getItem(position).toString();
                            String token[] = tv.split(",");
                            String tok[] = token[2].split("=");
                            rec = tok[1];
                            Intent intent = new Intent(Matching_exercise.this, Message_send.class);
                            startActivity(intent);
                        }
                    });
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
            protected void onPostExecute(String result){
                myJSON=result;
                showList();
            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute(url);
    }
}
