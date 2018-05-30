package com.example.bojun.test;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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

import static com.example.bojun.test.Menu.lgn_id;
import static com.example.bojun.test.Menu.lgn_name;
import static com.example.bojun.test.Message_send.rec;

public class Message_receive extends AppCompatActivity {
    String myJSON;
    ListView message_list;
    private static final String TAG_RESULTS="result";
    private static final String TAG_NAME = "name"; // name이 sender
    private static final String TAG_MESSAGE = "message";
    private static final String TAG_RECEIVER = "receiver";
    JSONArray peoples = null;

    ArrayList<HashMap<String, String>> personList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_receive);

        ActionBar actionBar = getSupportActionBar();
        setTitle("메세지 수신함");
        message_list = (ListView) findViewById(R.id.message_list);
        personList = new ArrayList<HashMap<String,String>>();
        getData("http://210.115.229.176:80/getdata1.php"); //http://접속할 주소/php파일
    }
    protected void showMessage(){
        try {
            JSONObject jsonObj = new JSONObject(myJSON);
            peoples = jsonObj.getJSONArray(TAG_RESULTS);

            for(int i=0;i<peoples.length();i++){
                JSONObject c = peoples.getJSONObject(i);
                String name = c.getString(TAG_NAME);
                String message = c.getString(TAG_MESSAGE);
                String receiver = c.getString(TAG_RECEIVER);
                if(lgn_name.equals(receiver)) {
                    HashMap<String, String> persons = new HashMap<String, String>();
                    persons.put(TAG_NAME, "발신인 : "+name);
                    persons.put(TAG_MESSAGE, "메세지 : "+message);
                    persons.put(TAG_RECEIVER, "수신인 : "+receiver);
                    personList.add(persons);
                }
            }

            ListAdapter adapter = new SimpleAdapter(
                    Message_receive.this, personList, R.layout.item,
                    new String[]{TAG_NAME,TAG_MESSAGE},
                    new int[]{R.id.name, R.id.message}
            );
            message_list.setAdapter(adapter);
            message_list.setOnItemClickListener(

                    new ListView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String tv = message_list.getAdapter().getItem(position).toString();
                            String token[] = tv.split(",");
                            String tok[] = token[1].split("=");
                            String to[] = tok[1].split(" : ");
                            rec = to[1];
                            Intent intent = new Intent(Message_receive.this, Message_send.class);
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
                showMessage();
            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute(url);
    }
}
