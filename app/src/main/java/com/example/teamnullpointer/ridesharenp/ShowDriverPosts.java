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

public class ShowDriverPosts extends AppCompatActivity {
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
        setContentView(R.layout.activity_show_driver_posts);
        setTitle("Drivers");

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

        listView.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long l) {

                String method = "Profile";
                MYSQLBackgroundTask backgroundTask = new MYSQLBackgroundTask(getApplicationContext());
                backgroundTask.execute(method, postdatabaseadapter.getRowEmail(position));


              //  startBackgroundTask;



                class startBackgroundTask extends AsyncTask<Void, Void, String> {

                    String json_url;
                    String json_string;
                    @Override
                    protected void onPreExecute() {
                        json_url = "http://athena.ecs.csus.edu/~wonge/rideshare/json_get_data_profile.php";
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
                        return "FAILED";

                    }
                    @Override
                    protected void onProgressUpdate (Void...values){
                        super.onProgressUpdate(values);
                    }

                    @Override
                    protected void onPostExecute (String result){
                        // TextView textView = (TextView) findViewById(R.id.textView);
                        // textView.setText(result);

                        Toast toast = Toast.makeText(getApplicationContext(), "hi", Toast.LENGTH_SHORT);
                        toast.show();

                        json_string = result;
                        Intent intent = new Intent(getApplicationContext(), ViewProfile.class);
                        intent.putExtra("json_data",json_string);
                        startActivity(intent);
                    }
                }

               // Toast toast = Toast.makeText(getApplicationContext(), postdatabaseadapter.getRowInfo(position) + " " + postdatabaseadapter.getRowEmail(position), Toast.LENGTH_SHORT);
               // toast.show();
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
