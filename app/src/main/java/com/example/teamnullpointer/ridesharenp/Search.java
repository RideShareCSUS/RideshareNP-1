package com.example.teamnullpointer.ridesharenp;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by Eric Wong on 12/15/2015.
 */

/*
    1.Need to search user to work. When button is click, takes the field and searches
    the database for the name and displays it.

    2.The display can be clicked to go to the user's profile???
    
 */
public class Search extends AppCompatActivity {
    private Context ctx;
    private Button searchBut;
    private EditText searchField;
    private ListView listview;
    private List<String> list_file;
    private ListView listView;
    private String json_string;
    private JSONObject jsonObject;
    private JSONArray jsonArray;
    private PostDatabaseAdapter postdatabaseadapter;
    private DataBaseOperation mydb; //Local DB


    //Retrieve database info
    private String url = "http://athena.ecs.csus.edu/~wonge/rideshare/.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_users);
        setTitle("Search Users");
        searchBut = (Button) findViewById(R.id.searchBut);
        searchBut.setText("Search User");
        searchField = (EditText) findViewById(R.id.searchUserText);
        searchField.setHint("Enter name");
        run();
    }

    private void run(){
        layout();
        onSearchClick();
    }

    private void onSearchClick() {
        searchBut.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                new startBackgroundTask().execute();
            }
            class startBackgroundTask extends AsyncTask<Void, Void, String> {
                String json_url;
                String json_string;
                @Override

                //EDIT THIS FOR SEARCHING USERS
                protected void onPreExecute() {
                    json_url = "http://athena.ecs.csus.edu/~wonge/rideshare/json_get_data_driver.php";
                }

                @Override
                protected String doInBackground(Void... voids) {
                    String JSON_STRING;
                    try {
                        URL url = new URL(json_url);
                        HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                        InputStream inputStream = httpURLConnection.getInputStream();
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                        StringBuilder stringBuilder = new StringBuilder();
                        while ((JSON_STRING = bufferedReader.readLine()) != null) {
                            stringBuilder.append(JSON_STRING+"\n");
                        }
                        bufferedReader.close();
                        inputStream.close();
                        httpURLConnection.disconnect();
                        return stringBuilder.toString().trim();
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return null;
                }
                @Override
                protected void onProgressUpdate (Void...values){
                    super.onProgressUpdate(values);
                }

                @Override
                protected void onPostExecute (String result){
                    // TextView textView = (TextView) findViewById(R.id.textView);
                    // textView.setText(result);
                    json_string = result;
                    Intent intent = new Intent(ctx, ShowDriverPosts.class);
                    intent.putExtra("json_data", json_string);
                    startActivity(intent);
                }
            }
        });
    }

    private void layout(){
        listView = (ListView) findViewById(R.id.listviewid);
        postdatabaseadapter = new PostDatabaseAdapter(this,R.layout.row_layout);
        listView.setAdapter(postdatabaseadapter);
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
