package com.example.teamnullpointer.ridesharenp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

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
    ContactAdapter contactAdapter;
    ListView listView;
    Button refreshbut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_rider_posts);
        listView = (ListView) findViewById(R.id.listviewid);
        refreshbut = (Button) findViewById(R.id.refreshbutid);
        refreshbut.setText("Refresh");
        contactAdapter = new ContactAdapter(this,R.layout.row_layout);
        listView.setAdapter(contactAdapter);
        json_string = getIntent().getExtras().getString("json_data");

        try {
            jsonObject = new JSONObject(json_string);
            int count = 0;
            jsonArray = jsonObject.getJSONArray("server_response");
            String description;
            while(count<jsonArray.length()) {
                JSONObject JO = jsonArray.getJSONObject(count);
                description = JO.getString("description");
                Contacts contacts = new Contacts(description);
                contactAdapter.add(contacts);
                count++;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        //listview = (ListView) findViewById(R.id.listviewid);
        //list_file = new ArrayList<String>();
        //ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1 ,list_file);

      /*list_file.add("123 Need a ride! Days needed: Mo Tue wen Thur Fri");
      list_file.add("123");
      list_file.add("123");
      list_file.add("123");
      list_file.add("123");
      list_file.add("123");
      list_file.add("123");
      list_file.add("123");
      list_file.add("123");
      list_file.add("123");
      list_file.add("123");
      list_file.add("123");
      list_file.add("123");
      list_file.add("123");*/

        //listview.setAdapter(arrayAdapter);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_show_rider_posts, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
