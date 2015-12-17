package com.example.teamnullpointer.ridesharenp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
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
import android.widget.Button;
import android.widget.TextView;

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

public class CenteralHub extends AppCompatActivity {
    private Button postbut, driverbut, riderbut, myprofilebut, editprofilebut, mycarpoolbut, msgbut, searchbut;
    private Context ctx;
    private String userEmail;
    private DataBaseOperation mydb; //Local DB

    //Retrieve database info
    private String profile_url = "http://athena.ecs.csus.edu/~wonge/rideshare/json_get_data_profile.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_centeral_hub);

        ctx = this.getApplicationContext();
        mydb = new DataBaseOperation(ctx);

        centralHubRun();
        setTitle(userEmail);
    }

    private void centralHubRun(){
        centralLayout();
        getEmail();
        centralClick();
    }

    public void getEmail(){
        Cursor res = mydb.getAllData();
        Boolean contains = res.moveToNext();

        if(contains == true) {
            userEmail = res.getString(1);
        }
    }


    private void centralLayout() {
        postbut = (Button) findViewById(R.id.postid);
        driverbut = (Button) findViewById(R.id.driversearchid);
        riderbut = (Button) findViewById(R.id.ridersearchid);
        myprofilebut = (Button) findViewById(R.id.myprofilebut);
        editprofilebut = (Button) findViewById(R.id.editprofilebut);
        mycarpoolbut = (Button) findViewById(R.id.mycarpoolbutid);
        msgbut = (Button) findViewById(R.id.messengerbut);
        searchbut = (Button) findViewById(R.id.searchprofilebut);

        postbut.setText("Post");
        driverbut.setText("Find Drivers");
        riderbut.setText("Find Riders");
        myprofilebut.setText("My Profile");
        editprofilebut.setText("Edit Profile");
        mycarpoolbut.setText("Carpools");
        msgbut.setText("Messenger");
        searchbut.setText("Search");

    }

    //Handle back button
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            moveTaskToBack(true);
        }
        return super.onKeyDown(keyCode, event);
    }

    private void centralClick(){

        postbut.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(ctx, Post.class));
            }
        });

        searchbut.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(ctx, Search.class));
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

            //new startBackgroundTask().execute();

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


        editprofilebut.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    getEmail();
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
                        String JSON_STRING;
                        try {
                            URL url = new URL(profile_url);
                            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                            httpURLConnection.setRequestMethod("POST");
                            httpURLConnection.setDoOutput(true);
                            httpURLConnection.setDoInput(true);
                            OutputStream OS = httpURLConnection.getOutputStream();
                            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS,"UTF-8"));
                            String emailsend = URLEncoder.encode("Email", "UTF-8") +"="+ URLEncoder.encode(userEmail, "UTF-8");

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

                    @Override
                    protected void onProgressUpdate(Void... values) {
                        super.onProgressUpdate(values);
                    }

                    @Override
                    protected void onPostExecute(String result) {
                        json_string = result;
                        Intent intent = new Intent(getApplicationContext(), EditProfile.class);
                        intent.putExtra("json_data", json_string);
                        startActivity(intent);
                    }
                }

         });

        myprofilebut.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new startBackgroundTask().execute();
            }

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
                        URL url = new URL(profile_url);
                        HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                        httpURLConnection.setRequestMethod("POST");
                        httpURLConnection.setDoOutput(true);
                        httpURLConnection.setDoInput(true);
                        OutputStream OS = httpURLConnection.getOutputStream();
                        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS,"UTF-8"));
                        String emailsend = URLEncoder.encode("Email", "UTF-8") +"="+ URLEncoder.encode(userEmail, "UTF-8");

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
                @Override
                protected void onProgressUpdate (Void...values){
                    super.onProgressUpdate(values);
                }

                @Override
                protected void onPostExecute (String result){
                    // TextView textView = (TextView) findViewById(R.id.textView);
                    // textView.setText(result);
                    json_string = result;
                    Intent intent = new Intent(ctx, MyProfile.class);
                    intent.putExtra("json_data",json_string);
                    startActivity(intent);
                }
            }

        });



        mycarpoolbut.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(ctx, MyCarpools.class));
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
        if (id == R.id.logoutmenuid) {
            finish();
            startActivity(new Intent(ctx, Login.class));
        }

        return super.onOptionsItemSelected(item);
    }
}
