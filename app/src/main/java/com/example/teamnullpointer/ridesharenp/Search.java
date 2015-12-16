package com.example.teamnullpointer.ridesharenp;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import org.json.JSONArray;
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
import java.util.List;

/**
 * Created by Eric Wong on 12/15/2015.
 */

/*
    1.Need to search user to work.   When button is click, takes the field and searches
    the database for the name and displays it.

    2.The display can be clicked to go to the user's profile???

 */
public class Search extends AppCompatActivity {
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
    private String search1;


    //Retrieve database info
    private String profile_url = "http://athena.ecs.csus.edu/~wonge/rideshare/json_get_data_profile.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_users);
        setTitle("Search Users");
        run();
    }

    private void run(){
        searchLayout();
        buttonPressed();
    }
    private void searchLayout() {
         searchBut = (Button) findViewById(R.id.searchBut);
         searchBut.setText("Search User");
         searchField = (EditText) findViewById(R.id.searchUserText);
         searchField.setHint("Enter Email of User");
    }
    private void buttonPressed() {
        searchBut.setOnClickListener(new View.OnClickListener() {
            String email;
            public void onClick(View v) {
                new startBackgroundTask().execute();
            }

            class startBackgroundTask extends AsyncTask<Void, Void, String> {
                String search = searchField.getText().toString();
                String json_url;

                @Override
                protected void onPreExecute() {
                    json_url = profile_url;
                }

                @Override
                protected String doInBackground(Void... voids) {
                   // String email1 = getSQLDB(email);
                    search1 = getSQLDB(search);
                    return search1;
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
                    intent.putExtra("this_email", search1);
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

    //Maybe add the menu options
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
