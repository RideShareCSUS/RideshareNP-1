package com.example.teamnullpointer.ridesharenp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MyCarpools extends AppCompatActivity {
    private ListView carpoollist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_carpools);

        run();
    }

    private void run(){
        layout();
        clicks();
    }

    private void layout(){
        carpoollist = (ListView) findViewById(R.id.carpoollistid);

        //TEST
        ArrayList<String> myStringArray1 = new ArrayList<String>();
        myStringArray1.add(0, "somethingsomethingsomethingsomething");
        myStringArray1.add(0, "John's Carpool Group");

        ArrayAdapter adapter =  new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, myStringArray1);
        carpoollist.setAdapter(adapter);

    }

    private void clicks(){
        carpoollist.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int i, long l) {
                startActivity(new Intent(getApplicationContext(), ViewCarpool.class));
            }
        });
    }

}
