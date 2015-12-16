package com.example.teamnullpointer.ridesharenp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.teamnullpointer.ridesharenp.R;

import callback.CustomCallback;
import fragment.ChatFragment;
import fragment.LoginFragment;

public class Messenger extends AppCompatActivity implements CustomCallback {

    SharedPreferences sharedPreferences;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    private String userEmail, userName, hostEmail, hostuserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.messenger);

        sharedPreferences = getSharedPreferences("details", MODE_PRIVATE);
        fragmentManager = getSupportFragmentManager();
    }

    @Override
    protected void onStart() {
        super.onStart();
        userEmail = getIntent().getExtras().getString("email_user");
        hostuserName = getIntent().getExtras().getString("host_name");
        hostEmail = getIntent().getExtras().getString("host_email");
        userName = getIntent().getExtras().getString("user_name");

        sharedPreferences.edit().putString("userEmail", userEmail).apply();
        sharedPreferences.edit().putString("hostuserName", hostuserName).apply();
        sharedPreferences.edit().putString("hostEmail", hostEmail).apply();
        sharedPreferences.edit().putString("userName", userName).apply();

        changeLogin();
    }

    public void changeLogin() {
      /*  if (sharedPreferences.getString("username", null) == null) {
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container, new LoginFragment(), "Login");
            fragmentTransaction.commit();
        } else {

        */  fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container, new ChatFragment(), "Chat");
            fragmentTransaction.commit();
       // }
    }

    @Override
    public void loginActivity(int LOGIN_STATE) {
        if (LOGIN_STATE == 0) {
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container, new ChatFragment(), "Chat");
            fragmentTransaction.commit();
        }/* else {
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container, new LoginFragment(), "Login");
            fragmentTransaction.commit();
        }*/
    }
}