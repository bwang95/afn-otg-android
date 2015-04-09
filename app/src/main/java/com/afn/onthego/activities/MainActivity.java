package com.afn.onthego.activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.afn.onthego.R;

import java.util.ArrayList;
import java.util.Collections;


public class MainActivity extends ActionBarActivity implements AdapterView.OnItemClickListener {

    ListView activitySelector;

    ArrayAdapter<String> activityAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] activityArray = {
                "Learn",
                "About"
        };

        ArrayList<String> activityList = new ArrayList<>();
        Collections.addAll(activityList, activityArray);

        activitySelector = (ListView) findViewById(R.id.lv_main_activity_selector);
        activityAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, activityList);

        activitySelector.setAdapter(activityAdapter);
        activitySelector.setOnItemClickListener(this);
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String item = activityAdapter.getItem(position);
        if(item.equals("Learn")){
            Intent i = new Intent(this, LearnActivity.class);
            startActivity(i);
        } else if (item.equals("About"))
            startActivity(new Intent(this, AboutActivity.class));
    }
}
