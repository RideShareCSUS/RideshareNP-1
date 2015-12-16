package com.example.teamnullpointer.ridesharenp;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class ShowDriverPosts extends AppCompatActivity {
    private ListView listview;
    private List<String> list_file;
    private ListView listView;
    private String json_string;
    private JSONObject jsonObject;
    private JSONArray jsonArray;
    private PostDatabaseAdapter postdatabaseadapter;
    private DataBaseOperation mydb; //Local DB
    private String userEmail, email2;


    //Retrieve database info
    private String profile_url = "http://athena.ecs.csus.edu/~wonge/rideshare/json_get_data_profile.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_driver_posts);

        mydb = new DataBaseOperation(getApplicationContext());

        run();
    }

    private void run(){
        layout();
        retrievePosts();
        getEmail();
        clicks();
    }

    public void getEmail(){
        Cursor res = mydb.getAllData();
        Boolean contains = res.moveToNext();

        if(contains == true) {
            userEmail = res.getString(1);
        }
    }

    private void layout(){
        listView = (ListView) findViewById(R.id.listviewid);

        postdatabaseadapter = new PostDatabaseAdapter(this,R.layout.row_layout);
        listView.setAdapter(postdatabaseadapter);

        setTitle("Drivers");

    }

    private void retrievePosts(){
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
    }

    private void clicks(){

        listView.setOnItemClickListener(new ListView.OnItemClickListener() {
            String email;
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long l) {

                email = postdatabaseadapter.getRowEmail(position);
                new startBackgroundTask().execute();

            }

            class startBackgroundTask extends AsyncTask<Void, Void, String> {

                String json_url;
                String json_string;

                @Override
                protected void onPreExecute() {
                    json_url = profile_url;
                }

                @Override
                protected String doInBackground(Void... voids) {
                    String email1 = getSQLDB(email);
                    email2 = getSQLDB(userEmail);
                    return email1;
                }

                @Override
                protected void onProgressUpdate(Void... values) {
                    super.onProgressUpdate(values);
                }

                @Override
                protected void onPostExecute(String result) {
                    json_string = result;
                    Intent intent = new Intent(getApplicationContext(), ViewProfile.class);
                    intent.putExtra("json_data", json_string);
                    intent.putExtra("this_email", email2);
                    startActivity(intent);
                }
            }
        });
    }

    private String getSQLDB(String email){
        String JSON_STRING;
        try {
            URL url = new URL(profile_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream OS = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS,"UTF-8"));
            String emailsend = URLEncoder.encode("Email", "UTF-8") +"="+ URLEncoder.encode(email, "UTF-8");

            bufferedWriter.write(emailsend);
            bufferedWriter.flush();
            bufferedWriter.close();
            OS.close();


            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));

            StringBuilder stringBuilder = new StringBuilder();
            while ((JSON_STRING = bufferedReader.readLine()) != null) {
                stringBuilder.append(JSON_STRING + "\n");
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
        return "FAILED";
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