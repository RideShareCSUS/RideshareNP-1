package com.example.teamnullpointer.ridesharenp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class CenteralHub extends AppCompatActivity {
    private Button postbut, driverbut, riderbut, mapbut, viewprofilebut, editprofilebut, mycarpoolbut;
    private Context ctx;
   // private DataBaseOperation mydb; //Local DB

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_centeral_hub);
        setTitle("Hello John Smith");
        ctx = this.getApplicationContext();

        centralHubRun();
    }

    private void centralHubRun(){
        centralLayout();
        centralClick();
    }

    private void centralLayout() {
        postbut = (Button) findViewById(R.id.postid);
        driverbut = (Button) findViewById(R.id.driversearchid);
        riderbut = (Button) findViewById(R.id.ridersearchid);
        mapbut = (Button) findViewById(R.id.mapbutid);
        viewprofilebut = (Button) findViewById(R.id.viewprofilebut);
        editprofilebut = (Button) findViewById(R.id.editprofilebut);
        mycarpoolbut = (Button) findViewById(R.id.mycarpoolbutid);

        postbut.setText("Post");
        driverbut.setText("Find Drivers");
        riderbut.setText("Find Riders");
        mapbut.setText("Maps");
        viewprofilebut.setText("View Profile");
        editprofilebut.setText("Edit Profile");
        mycarpoolbut.setText("My Carpools");
    }

    //Handle back button
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    private void centralClick(){
        postbut.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(ctx, Post.class));
            }

        });

        driverbut.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                new startBackgroundTask().execute();
            }

            class startBackgroundTask extends AsyncTask<Void, Void, String> {

                String json_url;
                String json_string;
                @Override
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
        //FOR RIDERS
        riderbut.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                new startBackgroundTask().execute();
            }

            class startBackgroundTask extends AsyncTask<Void, Void, String> {

                String json_url;
                String json_string;
                @Override
                protected void onPreExecute() {
                    json_url = "http://athena.ecs.csus.edu/~wonge/rideshare/json_get_data_rider.php";
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
                    Intent intent = new Intent(ctx, ShowRiderPosts.class);
                    intent.putExtra("json_data",json_string);
                    startActivity(intent);
                }
            }

        });

        mapbut.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //startActivity(new Intent(ctx, Maps.class));

                /*class startBackgroundTask extends AsyncTask<Void, Void, String> {

                    String json_url;
                    String json_string;
                    @Override
                    protected void onPreExecute() {
                        //json_url = "http://athena.ecs.csus.edu/~wonge/rideshare/json_get_data_rider.php";
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
                        //Intent intent = new Intent(ctx, ShowRiderPosts.class);
                        //intent.putExtra("json_data",json_string);
                        //startActivity(intent);


                        String zip1 = "95624";
                        String zip2 = "95824";

                        String uri = "https://www.google.com/maps/dir/" + zip1 + "/" + zip2;
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                        startActivity(intent);
                    }
                }*/


                String zip1 = "95624";
                String zip2 = "95824";

                String uri = "https://www.google.com/maps/dir/" + zip1 + "/" + zip2;
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(intent);

            }

        });

        viewprofilebut.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                /*class startBackgroundTask extends AsyncTask<Void, Void, String> {

                    String json_url;
                    String json_string;
                    @Override
                    protected void onPreExecute() {
                       // json_url = "http://athena.ecs.csus.edu/~wonge/rideshare/json_get_data_rider.php";
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
                        Intent intent = new Intent(ctx, ShowRiderPosts.class);
                        intent.putExtra("json_data",json_string);
                        startActivity(intent);
                    }
                }
                */
                startActivity(new Intent(ctx, ViewProfile.class));

            }

        });

        editprofilebut.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                /*class startBackgroundTask extends AsyncTask<Void, Void, String> {

                    String json_url;
                    String json_string;
                    @Override
                    protected void onPreExecute() {
                       // json_url = "http://athena.ecs.csus.edu/~wonge/rideshare/json_get_data_rider.php";
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
                        Intent intent = new Intent(ctx, ShowRiderPosts.class);
                        intent.putExtra("json_data",json_string);
                        startActivity(intent);
                    }
                }*/

                startActivity(new Intent(ctx, EditProfile.class));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_centeral_hub, menu);
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
