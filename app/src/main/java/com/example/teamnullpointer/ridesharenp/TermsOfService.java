package com.example.teamnullpointer.ridesharenp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class TermsOfService extends AppCompatActivity {
    private Button accept;
    private Context ctx;
    private DataBaseOperation db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_of_service);
        ctx = this.getApplicationContext();
        setTitle("Terms of Service");
        tosLayout();

        accept.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                db = new DataBaseOperation(ctx);
                db.insertData("User", "Pass", "ACPT", "NO"); //Remembers that users accepts TOS
                startActivity(new Intent(ctx, Login.class));
            }
        });

    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            moveTaskToBack(true);
        }
        return super.onKeyDown(keyCode, event);
    }


    private void tosLayout(){
        accept = (Button) findViewById(R.id.acceptbutid);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_terms_of, menu);
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
