package com.example.teamnullpointer.ridesharenp;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;


public class MYSQLBackgroundTask extends AsyncTask<String,Void,String> {
    private Context ctx;

    //MYSQL server url
    private String reg_url =  "http://athena.ecs.csus.edu/~wonge/rideshare/register.php";
    private String login_url = "http://athena.ecs.csus.edu/~wonge/rideshare/login.php";
    private String post_url = "http://athena.ecs.csus.edu/~wonge/rideshare/post.php";

    //LOCAL server url
    //private String reg_url = "http://10.0.2.2/RideshareMysql/register.php";
    //private String login_url= "http://10.0.2.2/RideshareMysql/login.php";

    MYSQLBackgroundTask(Context ctx){
        this.ctx= ctx;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {

        String method = params[0];
        //Register
        if(method.equals("register")){
            String email = params[1];
            String password = params[2];
            String firstName = params[3];
            String lastName = params[4];
            String zipcode = params[5];
            String gender = params[6];
            String studentorfaculty = params[7];
            String specialNeeds = params[8];
            //  String dayOfBirth = params[9];

            //System.out.println(email + " " + password + " " + firstName + " " + lastName + " " + zipcode + " " + gender + " " + studentorfaculty + " " + specialNeeds);

            try {
                URL url = new URL(reg_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream OS = httpURLConnection.getOutputStream();
                BufferedWriter bufferWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
                String dataReg = URLEncoder.encode("Email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8") + "&" +
                        URLEncoder.encode("Password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8") + "&" +
                        URLEncoder.encode("First_Name", "UTF-8") + "=" + URLEncoder.encode(firstName, "UTF-8") + "&" +
                        URLEncoder.encode("Last_Name", "UTF-8") + "=" + URLEncoder.encode(lastName, "UTF-8") + "&" +
                        URLEncoder.encode("Zip", "UTF-8") + "=" + URLEncoder.encode(zipcode, "UTF-8") + "&" +
                        URLEncoder.encode("Gender", "UTF-8") + "=" + URLEncoder.encode(gender, "UTF-8") + "&" +
                        URLEncoder.encode("SSM", "UTF-8") + "=" + URLEncoder.encode(studentorfaculty, "UTF-8") + "&" +
                        URLEncoder.encode("Special", "UTF-8") + "=" + URLEncoder.encode(specialNeeds, "UTF-8");
                //+ "&" + URLEncoder.encode("Day_Of_Birth", "UTF-8") + "=" + URLEncoder.encode(dayOfBirth, "UTF-8"

                bufferWriter.write(dataReg);
                bufferWriter.flush();
                bufferWriter.close();
                OS.close();
                InputStream IS = httpURLConnection.getInputStream();
                IS.close();
                return "Registration Success";
            } catch (MalformedURLException e){
                e.printStackTrace();
            } catch (IOException e){
                e.printStackTrace();
            }
        } else if(method.equals("login")){
            //Login
            String name = params[1];
            String pass = params[2];

            try {
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream OS = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS,"UTF-8"));
                String dataLogin = URLEncoder.encode("Email", "UTF-8") +"="+URLEncoder.encode(name, "UTF-8") +"&"+
                        URLEncoder.encode("Password", "UTF-8") +"="+URLEncoder.encode(pass, "UTF-8");
                bufferedWriter.write(dataLogin);
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String response = "";
                String line = "";
                while((line = bufferedReader.readLine()) != null){
                    response += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return response;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else if (method.equals("post")) {
            //Post
            String description = params[1];
            String rdtype = params[2];

            try {
                URL url = new URL(post_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream OS = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS,"UTF-8"));
                String dataLogin = URLEncoder.encode("Description", "UTF-8") +"="+URLEncoder.encode(description, "UTF-8")+"&"+
                        URLEncoder.encode("RDType", "UTF-8") + "=" +URLEncoder.encode(rdtype, "UTF-8");
                bufferedWriter.write(dataLogin);
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String response = "";
                String line = "";
                while((line = bufferedReader.readLine()) != null){
                    response += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return response;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return "fail";
    }


    protected void onProgessUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    //Register & login post execute
    @Override
    protected void onPostExecute(String result) {
        if (result.equals("Registration Success")) {
            Toast.makeText(ctx, result, Toast.LENGTH_LONG).show();
        } else {
            if(result.equals("Login Success")) {
                ctx.startActivity(new Intent(ctx, CenteralHub.class));
            } else {
                Toast.makeText(ctx, result,Toast.LENGTH_LONG).show();
            }
        }

    }

}
