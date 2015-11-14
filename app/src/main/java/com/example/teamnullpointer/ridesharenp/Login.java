package com.example.teamnullpointer.ridesharenp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

//Login page
public class Login extends AppCompatActivity {
    //Textfields & buttons
    Button loginbut, regibut;
    EditText emaillogintxt, passtxt;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mContext = this.getApplicationContext(); //Gets context for start new activities.

        loginRun();
    }

    //Runs login
    private void loginRun(){
        loginLayout();

        loginbut.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startBackgroundTask();
            }

        });

        regibut.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(mContext,  Register.class));
            }
        });

        //Clearing text fields

        emaillogintxt.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if((emaillogintxt.getText()+"").equals("E-mail"))
                    emaillogintxt.setText("");
            }

        });

        passtxt.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if ((passtxt.getText() + "").equals("Password"))
                    passtxt.setText("");
            }

        });

    }

    //Extract user login info and checks database
    private void startBackgroundTask(){
        String username = emaillogintxt.getText().toString();
        String password = passtxt.getText().toString();
        String method = "login";

        MYSQLBackgroundTask backgroundTask = new MYSQLBackgroundTask(this);
        backgroundTask.execute(method, username, password);
    }

    //Login layout
    private void loginLayout() {
        loginbut = (Button) findViewById(R.id.loginbutid);
        regibut = (Button) findViewById(R.id.regibutid);
        emaillogintxt = (EditText) findViewById(R.id.emaillogintxtid);
        passtxt = (EditText) findViewById(R.id.passtxtid);
        loginbut.setText("Login");
        regibut.setText("Register");
        emaillogintxt.setText("E-mail");
        passtxt.setText("Password");
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
