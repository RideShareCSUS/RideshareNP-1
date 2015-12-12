package com.example.teamnullpointer.ridesharenp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import java.util.Calendar;

public class EditProfile extends AppCompatActivity {
    private Context ctx;

    //Radio button text extract
    private RadioButton chosenButton;

    //Text fields
    private EditText password, zip;

    //Radio group - (GENDER)
    private RadioGroup gender;
    private RadioButton male, female, other;
    private TextView genderRadioTitle;

    //Radio group - (SAC STATE MEMBER)
    private RadioGroup ssm;
    private RadioButton student, faculty;
    private TextView ssmTitle;

    //Radio group - (SPECIAL ACCOMMODATIONS)
    private RadioGroup special;
    private RadioButton specialYes;
    private RadioButton specialNo;
    private TextView specialTitle;

    //Burf
    private NumberPicker month, day, year;
    private TextView birthdayTitle;


    //Sumbit button
    private Button enterbut;


    //Retrieve database info
    private String profile_url = "http://athena.ecs.csus.edu/~wonge/rideshare/json_get_data_profile.php";

    private String json_string;
    private JSONObject jsonObject;
    private JSONArray jsonArray;

    private String  genderGet, ssmGet, specGet, dobGet, monGet, dayGet, yearGet, emailGet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        setTitle("Edit Profile");
        ctx = this.getApplicationContext(); //Gets context for start new activities.

        registerRun();
        fill();
    }

    private void fill(){
        json_string = getIntent().getExtras().getString("json_data");


        try {
            jsonObject = new JSONObject(json_string);
            int count = 0;
            jsonArray = jsonObject.getJSONArray("server_response");

            while(count<jsonArray.length()) {
                JSONObject JO = jsonArray.getJSONObject(count);

                password.setText(JO.getString("pass"));
                zip.setText(JO.getString("zip"));

                genderGet = JO.getString("gender");
                if(!genderGet.equals("NA")){
                    if(genderGet.equals("Male")) {
                        gender.check(R.id.radiomaleid);
                    } else if(genderGet.equals("Female")){
                        gender.check(R.id.radiofemaleid);
                    } else {
                        gender.check(R.id.radiootherid);
                    }
                }

                ssmGet = JO.getString("ssm");
                if(!ssmGet.equals("NA")) {
                    if (ssmGet.equals("Student")) {
                        ssm.check(R.id.radiostudentid);
                    } else if (ssmGet.equals("Faculty")) {
                        ssm.check(R.id.radiofacid);
                    }
                }

                specGet = JO.getString("special");
                if(!specGet.equals("NA")) {
                    if (specGet.equals("Yes")) {
                        special.check(R.id.radioyesspecialid);
                    } else if (specGet.equals("No")) {
                        special.check(R.id.radionospecialid);
                    }
                }

                dobGet = JO.getString("DOB");
                extractDOB(dobGet);

                emailGet = JO.getString("email");

                count++;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }



    }

    //Runs register
    private void registerRun() {
        registerLayout();
        userTouch();
    }

    private void registerLayout(){
        password = (EditText) findViewById(R.id.passwordtxtid);
        zip = (EditText) findViewById(R.id.zipcodetxtid);

        password.setHint("Password");
        zip.setHint("Zip Code");

        genderRadioTitle = (TextView) findViewById(R.id.gendertitleid);
        genderRadioTitle.setText("Gender:");
        genderRadioTitle.setTypeface(null, Typeface.BOLD);
        genderRadioTitle.setTextColor(Color.parseColor("#000000"));
        gender = (RadioGroup) findViewById(R.id.genderradioid);
        male = (RadioButton) findViewById(R.id.radiomaleid);
        male.setText("Male");
        female = (RadioButton) findViewById(R.id.radiofemaleid);
        female.setText("Female");
        other = (RadioButton) findViewById(R.id.radiootherid);
        other.setText("Other");

        ssmTitle = (TextView) findViewById(R.id.ssmembertxtid);
        ssmTitle.setText("Sac State Member:");
        ssmTitle.setTypeface(null, Typeface.BOLD);
        ssmTitle.setTextColor(Color.parseColor("#000000"));
        ssm = (RadioGroup) findViewById(R.id.ssmradioid);
        student = (RadioButton) findViewById(R.id.radiostudentid);
        student.setText("Student");
        faculty = (RadioButton) findViewById(R.id.radiofacid);
        faculty.setText("Faculty");

        specialTitle = (TextView) findViewById(R.id.specialtitleid);
        specialTitle.setText("Special Accommodations:");
        specialTitle.setTypeface(null, Typeface.BOLD);
        specialTitle.setTextColor(Color.parseColor("#000000"));
        special = (RadioGroup) findViewById(R.id.specialradioid);
        specialYes = (RadioButton) findViewById(R.id.radioyesspecialid);
        specialYes.setText("Yes");
        specialNo = (RadioButton) findViewById(R.id.radionospecialid);
        specialNo.setText("No");

        birthdayTitle = (TextView) findViewById(R.id.birthdaytitleid);
        birthdayTitle.setTypeface(null, Typeface.BOLD);
        birthdayTitle.setTextColor(Color.parseColor("#000000"));
        month = (NumberPicker) findViewById(R.id.monthpickid);
        day = (NumberPicker) findViewById(R.id.daypickid);
        year =(NumberPicker) findViewById(R.id.yearpickid);

        month.setMinValue(1);
        month.setMaxValue(12);
        day.setMinValue(1);
        day.setMaxValue(31);
        year.setMinValue(1);
        year.setMaxValue(2500);

        month.setValue(2);
        day.setValue(2);
        year.setValue(2015);

        month.setWrapSelectorWheel(false);
        day.setWrapSelectorWheel(false);
        year.setWrapSelectorWheel(false);


        enterbut = (Button) findViewById(R.id.enterbutid);
        enterbut.setText("Enter");
    }


    //Handle back button
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }


    public void extractDOB(String dobGet ){

        int ct = 0;
        int sep = 0;
        String ch = "";
        String appen = "";
        while(ct < dobGet.length()){
            ch = dobGet.charAt(ct)+"";
            if(ch.equals("/")){
                sep++;
            }

            if(sep == 1 && ch.equals("/")){
                monGet = appen;
                appen = "";
                ch = "";
            } else if(sep == 2 && ch.equals("/")){
                dayGet = appen;
                appen = "";
                ch = "";
            }

            appen += ch;
            ct++;
        }

        yearGet = appen;

        month.setValue(Integer.parseInt(monGet));
        day.setValue(Integer.parseInt(dayGet));
        year.setValue(Integer.parseInt(yearGet));
    }

    //Handles user text field & button action
    private void userTouch() {
        //Sumbit button listener
        enterbut.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startBackgroundTask();
            }
        });
    }


    private void startBackgroundTask(){
        String  passwordLogin = password.getText().toString();
        String  zipcode = zip.getText().toString();
        String theGender = "NA";
        String theSSM = "NA";
        String theSpecial = "NA";
        String theBirthday = month.getValue() + "/"+ day.getValue() + "/" +  year.getValue();

        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.set(year.getValue(), month.getValue(), day.getValue());

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
        int day = today.get(Calendar.DAY_OF_MONTH) - dob.get(Calendar.DAY_OF_MONTH);

        int selectedId = gender.getCheckedRadioButtonId();
        chosenButton = (RadioButton) findViewById(selectedId);
        if(selectedId != -1){
            theGender = chosenButton.getText().toString();
        }


        selectedId = ssm.getCheckedRadioButtonId();
        chosenButton = (RadioButton) findViewById(selectedId);
        if(selectedId != -1){
            theSSM = chosenButton.getText().toString();
        }

        selectedId = special.getCheckedRadioButtonId();
        chosenButton = (RadioButton) findViewById(selectedId);
        if(selectedId != -1){
            theSpecial = chosenButton.getText().toString();
        }

        //Required
        if (passwordLogin.equals("") ||  (zipcode).equals("") || theSSM.equals("NA")) {

            AlertDialog.Builder builder1 = new AlertDialog.Builder(EditProfile.this);
            builder1.setMessage("Please Complete Required Fields:" + "\n\n" + "E-mail" + "\n" + "Password" + "\n" + "First Name" + "\n" + "Last Name" + "\n" + "Zip Code" + "\n" + "Sac State Member" + "\n" + "Day of Birth");
            builder1.setCancelable(true);
            builder1.setPositiveButton("Ok",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            AlertDialog alert11 = builder1.create();
            alert11.show();
        } else if (age == 17 && day < 0) {  //Check for day in month to be negative (Which means they are still 17, if day >= 0 then they are 18)
            AlertDialog.Builder builder1 = new AlertDialog.Builder(EditProfile.this);
            builder1.setMessage("Must 18 & older to sign up!");
            builder1.setCancelable(true);
            builder1.setPositiveButton("Ok",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            AlertDialog alert11 = builder1.create();
            alert11.show();
        } else if(age < 18 && age != 17) { //If less then 18 then deny access.
            AlertDialog.Builder builder1 = new AlertDialog.Builder(EditProfile.this);
            builder1.setMessage("Must 18 & older to sign up!");
            builder1.setCancelable(true);
            builder1.setPositiveButton("Ok",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            AlertDialog alert11 = builder1.create();
            alert11.show();
        } else {

            //NEED TO FILL WHEN OPEN
            String method = "edit";
            MYSQLBackgroundTask backgroundTask = new MYSQLBackgroundTask(this);
            backgroundTask.execute(method, passwordLogin, zipcode, theGender, theSSM, theSpecial, theBirthday, emailGet + "");
            finish();
        }
    }

}
