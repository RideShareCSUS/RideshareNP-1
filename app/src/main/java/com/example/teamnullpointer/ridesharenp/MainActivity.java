package com.example.teamnullpointer.ridesharenp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;


public class MainActivity extends AppCompatActivity {
    private DataBaseOperation myDB;
    private Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDB = new DataBaseOperation(this);
        ctx = getApplicationContext();
       // myDB.restart();   //restarting table testing purposes (LOCAL)
        tosChecker();

    }

    //Checks to start login or tos.
    private void tosChecker(){
        Cursor res = myDB.getAllData();
        Boolean contains = res.moveToNext();
        if(contains == true && res.getString(3).equals("ACPT")) {
            startActivity(new Intent(ctx, Login.class));
        }  else {
            startActivity(new Intent(ctx, TermsOfService.class));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
