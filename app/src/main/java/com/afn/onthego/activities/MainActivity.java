package com.afn.onthego.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.afn.onthego.R;
import com.afn.onthego.app.OTGApplication;
import com.afn.onthego.fragments.VolunteerFragment;
import com.afn.onthego.storage.KeyList;
import com.afn.onthego.storage.Storage;
import com.afn.onthego.util.LinksContainer;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;


public class MainActivity extends ActionBarActivity {

    private static final String LOG_TAG = "MainActivity";
    private FrameLayout fragmentContainer;

    private View.OnClickListener aboutListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onNavigationSelection(KeyList.Navigation.ABOUT);
        }
    };

    @Override
    public void onStart() {
        super.onStart();

        // Get tracker.
        Tracker t = ((OTGApplication) getApplication()).getTracker();
        // Set screen name.
        t.setScreenName("Android Home");
        // Send a screen view.
        t.send(new HitBuilders.ScreenViewBuilder().build());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout about = (LinearLayout) findViewById(R.id.ll_home_about);
        about.setOnClickListener(aboutListener);

        Storage.getInstance(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void onNavigationSelection(String key) {
        Log.d(LOG_TAG, "Switching with key " + key);
        switch (key) {
            case KeyList.Navigation.ABOUT:
                Intent aboutIntent = new Intent(this, AboutActivity.class);
                startActivity(aboutIntent);
                break;
            case KeyList.Navigation.DONATE:
                Intent donateIntent = new Intent(this, DonateActivity.class);
                startActivity(donateIntent);
                break;
            case KeyList.Navigation.VOLUNTEER:
                addVolunteerFragment(VolunteerFragment.newInstance());
                break;
            case KeyList.Navigation.LEARN:
                Intent learnIntent = new Intent(this, LearnActivity.class);
                startActivity(learnIntent);
                break;
            case KeyList.Navigation.CONNECT:
                Intent i = new Intent(this, ConnectActivity.class);
                startActivity(i);
                break;
        }
    }

    private void addVolunteerFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_in_bottom, R.anim.slide_out_down, R.anim.slide_in_bottom, R.anim.slide_out_down);
        transaction.add(R.id.fl_main_fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void openAfn(MenuItem item) {
        Intent webIntent = new Intent(Intent.ACTION_VIEW);
        webIntent.setData(Uri.parse(Storage.getInstance(getBaseContext()).getLinks().getURLS().get(LinksContainer.AFN)));
        startActivity(webIntent);
    }

    public void navLearn(View view) {
        onNavigationSelection(KeyList.Navigation.LEARN);
    }

    public void navVolunteer(View view) {
        onNavigationSelection(KeyList.Navigation.VOLUNTEER);
    }

    public void navDonate(View view) {
        onNavigationSelection(KeyList.Navigation.DONATE);
    }

    public void navConnect(View view) {
        onNavigationSelection(KeyList.Navigation.CONNECT);
    }

    public void openAfn(View view) {
        Intent webIntent = new Intent(Intent.ACTION_VIEW);
        webIntent.setData(Uri.parse(Storage.getInstance(getBaseContext()).getLinks().getURLS().get(LinksContainer.AFN)));
        startActivity(webIntent);
    }
}
