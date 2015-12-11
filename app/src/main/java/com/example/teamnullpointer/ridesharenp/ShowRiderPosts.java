package com.example.teamnullpointer.ridesharenp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ShowRiderPosts extends AppCompatActivity {
    private ListView listview;
    private List<String> list_file;
    String json_string;
    JSONObject jsonObject;
    JSONArray jsonArray;
    PostDatabaseAdapter postdatabaseadapter;
    ListView listView;
    Button refreshbut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_rider_posts);
        setTitle("Riders");

        listView = (ListView) findViewById(R.id.listviewid);
        refreshbut = (Button) findViewById(R.id.refreshbutid);
        refreshbut.setText("Refresh");
        postdatabaseadapter = new PostDatabaseAdapter(this,R.layout.row_layout);
        listView.setAdapter(postdatabaseadapter);
        json_string = getIntent().getExtras().getString("json_data");

        try {
            jsonObject = new JSONObject(json_string);
            int count = 0;
            jsonArray = jsonObject.getJSONArray("server_response");
            String description;
            String email;
            while(count<jsonArray.length()) {
                JSONObject JO = jsonArray.getJSONObject(count);
                description = JO.getString("description");
                email = JO.getString("email");
                PostDatabase postdatabase = new PostDatabase(description, email);
                postdatabaseadapter.add(postdatabase);
                count++;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        clicks();
    }


    private void clicks(){
        listView.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long l) {
                String method = "Profile";
                MYSQLBackgroundTask backgroundTask = new MYSQLBackgroundTask(getApplicationContext());
                backgroundTask.execute(postdatabaseadapter.getRowEmail(position));

                Toast toast = Toast.makeText(getApplicationContext(), postdatabaseadapter.getRowInfo(position) + " " + postdatabaseadapter.getRowEmail(position), Toast.LENGTH_SHORT);
                toast.show();
            }
        });
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
