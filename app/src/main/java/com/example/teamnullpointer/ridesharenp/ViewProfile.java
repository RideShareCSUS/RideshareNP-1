package com.example.teamnullpointer.ridesharenp;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

public class ViewProfile extends AppCompatActivity {
    private TextView title, spec, aval, gender, ssm;

    private RatingBar ratebar;

    private ListView comments;

    private Context ctx;

    //Retieve these form database
    private String nameGet, specGet, genderGet, ssmGet;
    private double user_ratingGet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);
        ctx = getApplicationContext();

        setTitle("Profile");
        registerRun();
    }

    //Runs register
    private void registerRun() {
        layout();
    }

    private void layout(){
        title = (TextView) findViewById(R.id.titleid);
        spec = (TextView) findViewById(R.id.spectxtid);
        aval = (TextView) findViewById(R.id.avalitxtid);
        gender = (TextView) findViewById(R.id.gendertxtid);
        ssm = (TextView) findViewById(R.id.csusstatusid);

        ratebar = (RatingBar) findViewById(R.id.ratingbarid);

        comments = (ListView) findViewById(R.id.commentsid);



        //ratebar.setRating(user_rating);

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
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }


}
