package com.example.teamnullpointer.ridesharenp;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class ViewCarpool extends AppCompatActivity {
    private Button mapbut, viewprofilebut;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_carpool);

        run();

    }

    private void layout(){
        mapbut = (Button) findViewById(R.id.mapbutid);
        viewprofilebut = (Button) findViewById(R.id.viewprofilebutid);

        mapbut.setText("Map");
        viewprofilebut.setText("View Profile");

    }

    private void run(){
        layout();
        clicks();
    }

    private void clicks(){
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
                startActivity(new Intent(getApplicationContext(), ViewProfile.class));

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

    }


}
