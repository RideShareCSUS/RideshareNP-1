package com.example.teamnullpointer.ridesharenp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MyProfile extends AppCompatActivity {
    private TextView title, spec, gender, ssm;

    private RatingBar ratebar;

    private ListView comments;

    private Context ctx;

    String json_string, userEmail;
    JSONObject jsonObject;
    JSONArray jsonArray;

    //Retieve these form database
    private String firstnameGet, lastnameGet, specGet, genderGet, ssmGet;
    private float user_ratingGet = 4;


    //Retrieve database info
    private String profile_url = "http://athena.ecs.csus.edu/~wonge/rideshare/json_get_data_profile.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        ctx = getApplicationContext();
        setTitle("Profile");

        //GET PROFILE DATA
        json_string = getIntent().getExtras().getString("json_data");

        try {
            jsonObject = new JSONObject(json_string);
            int count = 0;
            jsonArray = jsonObject.getJSONArray("server_response");


            while(count<jsonArray.length()) {
                JSONObject JO = jsonArray.getJSONObject(count);

                firstnameGet = JO.getString("first");
                lastnameGet = JO.getString("last");
                genderGet = JO.getString("gender");
                ssmGet = JO.getString("ssm");
                specGet = JO.getString("special");

                count++;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        registerRun();

    }

    //Runs register
    private void registerRun() {
        layout();
    }




    private void layout(){
        title = (TextView) findViewById(R.id.usernametitleid);
        spec = (TextView) findViewById(R.id.spectxtid);
        gender = (TextView) findViewById(R.id.gendertxtid);
        ssm = (TextView) findViewById(R.id.csusstatusid);

        ratebar = (RatingBar) findViewById(R.id.ratingbarid);
        comments = (ListView) findViewById(R.id.commentsid);

        title.setText(firstnameGet + " " + lastnameGet);
        spec.setText("Special Accommodations - " + specGet);
        gender.setText("Gender - " + genderGet);
        ssm.setText("CSUS Status - " + ssmGet);

        ratebar.setRating(user_ratingGet);

        //TEST
        ArrayList<String> myStringArray1 = new ArrayList<String>();
        myStringArray1.add("somethingsomethingsomething");
        myStringArray1.add("somethingsomethingsomethingsomething");
        myStringArray1.add("somethingsomethingsomethingsomethingsomething");
        myStringArray1.add("somethingsomethingsomethingsomethingsomethingsomething");
        myStringArray1.add("somethingsomethingsomethingsomethingsomethingsomething");
        myStringArray1.add("somethingsomethingsomethingsomethingsomethingsomething");
        myStringArray1.add("somethingsomethingsomethingsomethingsomethingsomething");
        myStringArray1.add("somethingsomethingsomethingsomethingsomethingsomething");
        myStringArray1.add("somethingsomethingsomethingsomethingsomethingsomethingsomethinggggggggggggggggggggggggg");

        ArrayAdapter adapter =  new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, myStringArray1);
        comments.setAdapter(adapter);
        comments.setClickable(false);

    }

    //Handle back button
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
