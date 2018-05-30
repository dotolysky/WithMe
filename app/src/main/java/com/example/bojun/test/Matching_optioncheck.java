package com.example.bojun.test;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
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
import static com.example.bojun.test.Message_send.rec;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;

public class Matching_optioncheck extends AppCompatActivity {
    static String sp1,sp2,timesp;
    String[] list_menu = {"택시", "운동", "식사", "과팅"};
    String[] list_people = {"1명", "2명", "3명", "4명", "5명"};
    String[] list_time={"12pm","1pm","2pm","3pm","4pm","5pm","6pm","7pm","8pm","9pm","10pm", "11pm","12am","1am","2am","3am","4am","5am","6am","7am","8am","9am","10am","11am"};
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

        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(0xFF90EE90));
        setTitle("매칭 조건");

        setContentView(R.layout.activity_matching_optioncheck);
        if(lgn_id!=null) {
            final Spinner boardoption_spineer1 = (Spinner) findViewById(R.id.option_spinner);
            ArrayAdapter<String> boardoption_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list_menu);
            boardoption_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            boardoption_spineer1.setAdapter(boardoption_adapter);
            boardoption_spineer1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int postition, long id) {
                    sp1 = (String) boardoption_spineer1.getSelectedItem();
                }

                public void onNothingSelected(AdapterView<?> parent) {
                }
            });

            final Spinner peopleoption_spineer2 = (Spinner) findViewById(R.id.option_spinner2);
            ArrayAdapter<String> peopleoption_adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list_people);
            peopleoption_adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            peopleoption_spineer2.setAdapter(peopleoption_adapter2);
            peopleoption_spineer2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int postition, long id) {
                    sp2 = (String) peopleoption_spineer2.getSelectedItem();
                }

                public void onNothingSelected(AdapterView<?> parent) {
                }
            });

            final Spinner timeoption_spinner = (Spinner) findViewById(R.id.option_spinner3);
            ArrayAdapter<String> timeoption_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list_time);
            timeoption_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            timeoption_spinner.setAdapter(timeoption_adapter);
            timeoption_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int postition, long id) {
                    timesp = (String) timeoption_spinner.getSelectedItem();
                }

                public void onNothingSelected(AdapterView<?> parent) {
                }
            });


            Button matching_start_btn = (Button) findViewById(R.id.start_button);
            matching_start_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        if (sp1.equals("택시")) {
                            getData("http://210.115.229.176:80/getdata_php1.php");
                        } else if (sp1.equals("운동"))
                            getData("http://210.115.229.176:80/getdata_php2.php");
                        else if (sp1.equals("식사"))
                            getData("http://210.115.229.176:80/getdata_php3.php");
                        else
                            getData("http://210.115.229.176:80/getdata_php4.php");
                }
            });
        }
        else{
            Toast.makeText(getApplicationContext(), "로그인을 먼저 해주세요.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), Menu.class);
            startActivity(intent);
        }

    }


    protected void showMessage() {

            AlertDialog.Builder builder = new AlertDialog.Builder(Matching_optioncheck.this);
            builder.setTitle("안내");
            builder.setMessage("선택하신 매칭 조건이 맞습니까?");
            builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    try {
                        JSONObject jsonObj = new JSONObject(myJSON);
                        peoples = jsonObj.getJSONArray(TAG_RESULTS);
                        boolean flag=false;
                        for(int i=0;i<peoples.length();i++){
                            JSONObject c = peoples.getJSONObject(i);
                            String name = c.getString(TAG_NAME);
                            String number = c.getString(TAG_NUMBER);
                            String time = c.getString(TAG_TIME);
                            String other = c.getString(TAG_OTHER);

                            if(sp2.equals(number))
                            {
                                if(timesp.equals(time))
                                {
                                    rec = name;
                                    flag=true;
                                }
                            }
                        }

                        if(flag==true)
                        {
                            Intent intent = new Intent(getApplicationContext(), Matchingoption_anything.class);
                            startActivity(intent);
                        }
                        else
                        {
                            Intent intent = new Intent(getApplicationContext(), Matchingoption_nothing.class);
                            startActivity(intent);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
            builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();

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

                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream(),"utf8"));

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




