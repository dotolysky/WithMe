package com.example.bojun.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import static com.example.bojun.test.Menu.lgn_name;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
/**
 * Created by dotol on 2017-06-09.
 */

public class Help extends AppCompatActivity{
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        setTitle("도움말");
    }
}
