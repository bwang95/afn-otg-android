package com.afn.onthego.activities;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.afn.onthego.R;
import com.afn.onthego.async.LearnRequest;
import com.afn.onthego.fragments.AboutFragment;
import com.afn.onthego.fragments.DonateFragment;
import com.afn.onthego.fragments.HomeFragment;
import com.afn.onthego.fragments.LearnFragment;
import com.afn.onthego.fragments.VolunteerFragment;
import com.afn.onthego.storage.KeyList;

import java.util.ArrayList;
import java.util.Collections;


public class MainActivity extends ActionBarActivity
        implements HomeFragment.OnFragmentInteractionListener,
        AboutFragment.OnFragmentInteractionListener,
        DonateFragment.OnFragmentInteractionListener,
        LearnFragment.OnFragmentInteractionListener,
        VolunteerFragment.OnFragmentInteractionListener{

    private static final String LOG_TAG = "MainActivity";
    private FrameLayout fragmentContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentContainer = (FrameLayout) findViewById(R.id.ll_main_fragment_container);

        HomeFragment home = HomeFragment.newInstance();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.ll_main_fragment_container, home);
        transaction.commit();

        // start the async activity to get the learning modules
        LearnRequest learnRequest = new LearnRequest(this);
        learnRequest.execute();
    }

    private void addFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.ll_main_fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();

        Log.d(LOG_TAG, "Added fragment " + fragment.getClass().getName());
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
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onHomeFragmentSelection(String key) {
        Log.d(LOG_TAG, "Switching fragments with key " + key);
        switch(key) {
            case KeyList.Navigation.ABOUT :
                addFragment(AboutFragment.newInstance());
                break;
            case KeyList.Navigation.DONATE :
                addFragment(DonateFragment.newInstance());
                break;
            case KeyList.Navigation.VOLUNTEER :
                addFragment(VolunteerFragment.newInstance());
                break;
            case KeyList.Navigation.LEARN :
                addFragment(LearnFragment.newInstance("", ""));
                break;
            case KeyList.Navigation.CONNECT :
                Intent i = new Intent(this, ConnectActivity.class);
                startActivity(i);
                break;
        }
    }
}
