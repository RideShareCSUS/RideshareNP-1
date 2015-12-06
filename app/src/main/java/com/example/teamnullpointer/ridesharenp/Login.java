package com.example.teamnullpointer.ridesharenp;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

//Login page
public class Login extends AppCompatActivity {
    //Textfields & buttons
    private Button loginbut, regibut;
    private EditText emaillogintxt, passtxt;
    private Context mContext;
    private CheckBox remembermebox;
    private DataBaseOperation mydb; //Local DB

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Rideshare");
        mContext = this.getApplicationContext(); //Gets context for start new activities.
        mydb = new DataBaseOperation(this);

        loginRun();
        rememberMeFill();
    }

    //Runs login
    private void loginRun(){
        loginLayout();
        userTouch();
    }

    //Handle back button
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            moveTaskToBack(true);
        }
        return super.onKeyDown(keyCode, event);
    }

    //Handles user text field & button action
    private void userTouch(){
        loginbut.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(remembermebox.isChecked()){
                    mydb.restart();
                    mydb.insertData(emaillogintxt.getText().toString(), passtxt.getText().toString(), "ACPT", "YES", "12345");
                }
                startBackgroundTask();
            }

        });

        regibut.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(mContext, Register.class));
            }
        });

    }

    //Use local database to determine if user wants to be remembered.
    public void rememberMeFill(){
        Cursor res = mydb.getAllData();
        Boolean contains = res.moveToNext();
        if(contains == true && res.getString(4).equals("YES")) { //Fills in text fields and keep remember me checked.
            emaillogintxt.setText(res.getString(1));
            passtxt.setText(res.getString(2));
            remembermebox.setChecked(true);
        }
    }


    //Extract user login info and checks database
    private void startBackgroundTask(){
        String username = emaillogintxt.getText().toString();
        String password = passtxt.getText().toString();
        String method = "login";

        MYSQLBackgroundTask backgroundTask = new MYSQLBackgroundTask(this);
        backgroundTask.execute(method, username, password);

        if(!remembermebox.isChecked())
            passtxt.setText("");
    }

    //Login layout
    private void loginLayout() {
        loginbut = (Button) findViewById(R.id.loginbutid);
        regibut = (Button) findViewById(R.id.regibutid);
        emaillogintxt = (EditText) findViewById(R.id.emaillogintxtid);
        passtxt = (EditText) findViewById(R.id.passtxtid);
        remembermebox = (CheckBox) findViewById(R.id.remembermeid);

        Login.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ImageView logo = (ImageView) findViewById(R.id.imageviewid);
                logo.setImageResource(R.drawable.sacstatelogo_login);
            }
        });

        loginbut.setText("Login");
        regibut.setText("Register");
        emaillogintxt.setHint("E-mail");
        passtxt.setHint("Password");
        remembermebox.setText("Remember Me");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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
