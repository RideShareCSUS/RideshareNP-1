package com.example.teamnullpointer.ridesharenp;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

//Registration page

public class Register extends AppCompatActivity {
    private Context ctx;

    //Radio button text extract
    private RadioButton chosenButton;

    //Text fields
    private EditText email, password, firstname, lastname, zip;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setTitle("Register");
        ctx = this.getApplicationContext(); //Gets context for start new activities.

        registerRun();
    }

    //Runs register
    private void registerRun() {
        registerLayout();
        userTouch();
    }

    //Handle back button
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            finish();
        }
        return super.onKeyDown(keyCode, event);
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

    //Set up Registration page layout
    private void registerLayout(){

        email = (EditText) findViewById(R.id.emaillogintxtid);
        password = (EditText) findViewById(R.id.passwordtxtid);
        firstname = (EditText) findViewById(R.id.firstnametxtid);
        lastname = (EditText) findViewById(R.id.lastnametxtid);
        zip = (EditText) findViewById(R.id.zipcodetxtid);

        email.setHint("E-Mail");
        password.setHint("Password");
        firstname.setHint("First Name");
        lastname.setHint("Last Name");
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
        month.setWrapSelectorWheel(false);
        day.setWrapSelectorWheel(false);
        year.setWrapSelectorWheel(false);

        enterbut = (Button) findViewById(R.id.enterbutid);
        enterbut.setText("Enter");
    }

    //Extract user info and send to server
    private void startBackgroundTask(){
        String emailLogin = email.getText().toString();
        String  passwordLogin = password.getText().toString();
        String firstName = firstname.getText().toString();
        String lastName = lastname.getText().toString();
        String  zipcode = zip.getText().toString();
        String theGender = "NA";
        String theSSM = "NA";
        String theSpecial = "NA";
        String theBirthday = month.getValue() + "/"+ day.getValue() + "/" +  year.getValue();

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
       if( emailLogin.equals("") || passwordLogin.equals("") || firstName.equals("") || lastName.equals("") || zipcode.equals("") || theSSM.equals("NA")) {

           AlertDialog.Builder builder1 = new AlertDialog.Builder(Register.this);
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
       } else {
           String method = "register";
           MYSQLBackgroundTask backgroundTask = new MYSQLBackgroundTask(this);
           backgroundTask.execute(method, emailLogin, passwordLogin, firstName, lastName, zipcode, theGender, theSSM, theSpecial); //,theBirthday
           finish();
       }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register, menu);
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
