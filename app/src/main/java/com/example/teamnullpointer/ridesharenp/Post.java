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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class Post extends AppCompatActivity {
    Context ctx;

    TextView title;

    EditText descript;

    RadioGroup rd;
    RadioButton rider, driver;

    RadioButton chosenButton;
    Button post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        ctx = this.getApplicationContext();

        postRun();
    }

    private void postRun() {
        postLayout();
        postClick();
    }

    private void postClick() {
        post.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startBackgroundTask();
                startActivity(new Intent(ctx, CenteralHub.class));
            }

        });
    }

    private void postLayout() {
        rd = (RadioGroup) findViewById(R.id.postgroupid);
        driver = (RadioButton) findViewById(R.id.driverid);
        rider = (RadioButton) findViewById(R.id.riderid);
        title = (TextView) findViewById(R.id.titleid);
        descript = (EditText) findViewById(R.id.descriptid);
        post = (Button) findViewById(R.id.postid);

        title.setText("Choose to post as...");
        driver.setText("Driver");
        rider.setText("Rider");
        post.setText("Post");
        descript.setHint("Give a description...");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_post, menu);
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
    private void startBackgroundTask(){
        String description = descript.getText().toString();

        int selectedId = rd.getCheckedRadioButtonId();
        chosenButton = (RadioButton) findViewById(selectedId);
        String rdtype = chosenButton.getText().toString();

       /*int selectedId = gender.getCheckedRadioButtonId();
       chosenButton = (RadioButton) findViewById(selectedId);
       String theGender = chosenButton.getText().toString();

       selectedId = ssm.getCheckedRadioButtonId();
       chosenButton = (RadioButton) findViewById(selectedId);
       String theSSM = chosenButton.getText().toString();*/

       /*String theSpecial = "";
       selectedId = special.getCheckedRadioButtonId();
       if(selectedId != -1) {
           chosenButton = (RadioButton) findViewById(selectedId);
           theSpecial = chosenButton.getText().toString();
       } else {
           theSpecial = "No";
       }*/

        //System.out.println(emailLogin + " " + passwordLogin + " " + firstName + " " + lastName + " " + zipcode + " " + theGender + " " + theSSM + " " + theSpecial);


        String method = "post";
        MYSQLBackgroundTask backgroundTask = new MYSQLBackgroundTask(this);
        backgroundTask.execute(method, description,rdtype);

    }
}
