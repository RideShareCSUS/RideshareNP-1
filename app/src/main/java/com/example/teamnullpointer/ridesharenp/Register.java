package com.example.teamnullpointer.ridesharenp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
    private RadioButton male, female;
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

    //Sumbit button
    private Button enterbut;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ctx = this.getApplicationContext(); //Gets context for start new activities.

        registerRun();
    }

    //Runs register
    private void registerRun() {
        registerLayout();
        userTouch();
    }

    //Handles user text field & button action
    private void userTouch() {
        //Sumbit button listener
        enterbut.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startBackgroundTask();
                startActivity(new Intent(ctx, Login.class));
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
        genderRadioTitle.setText("Gender");
        gender = (RadioGroup) findViewById(R.id.genderradioid);
        male = (RadioButton) findViewById(R.id.radiomaleid);
        male.setText("Male");
        female = (RadioButton) findViewById(R.id.radiofemaleid);
        female.setText("Female");

        ssmTitle = (TextView) findViewById(R.id.ssmembertxtid);
        ssmTitle.setText("Sac State Memeber");
        ssm = (RadioGroup) findViewById(R.id.ssmradioid);
        student = (RadioButton) findViewById(R.id.radiostudentid);
        student.setText("Student");
        faculty = (RadioButton) findViewById(R.id.radiofacid);
        faculty.setText("Faculty");

        specialTitle = (TextView) findViewById(R.id.specialtitleid);
        specialTitle.setText("Special Accommodations");
        special = (RadioGroup) findViewById(R.id.specialradioid);
        specialYes = (RadioButton) findViewById(R.id.radioyesspecialid);
        specialYes.setText("Yes");
        specialNo = (RadioButton) findViewById(R.id.radionospecialid);
        specialNo.setText("No");


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

        int selectedId = gender.getCheckedRadioButtonId();
        chosenButton = (RadioButton) findViewById(selectedId);
        String theGender = chosenButton.getText().toString();

        selectedId = ssm.getCheckedRadioButtonId();
        chosenButton = (RadioButton) findViewById(selectedId);
        String theSSM = chosenButton.getText().toString();

       // String theSpecial = "";
        selectedId = special.getCheckedRadioButtonId();
       // if(selectedId != -1) {
        chosenButton = (RadioButton) findViewById(selectedId);
        String theSpecial = chosenButton.getText().toString();
        //} else {
          //  theSpecial = "No";
        //}

        //System.out.println(emailLogin + " " + passwordLogin + " " + firstName + " " + lastName + " " + zipcode + " " + theGender + " " + theSSM + " " + theSpecial);


        String method = "register";
        MYSQLBackgroundTask backgroundTask = new MYSQLBackgroundTask(this);
        backgroundTask.execute(method, emailLogin, passwordLogin, firstName, lastName, zipcode, theGender, theSSM, theSpecial);

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
