package com.afn.onthego.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.afn.onthego.R;
import com.afn.onthego.adapters.AboutAdapter;
import com.afn.onthego.app.OTGApplication;
import com.afn.onthego.storage.Storage;
import com.afn.onthego.util.LinksContainer;
import com.google.android.gms.analytics.GoogleAnalytics;

import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

public class AboutActivity extends ActionBarActivity {

    private StickyListHeadersListView aboutList;
    private AboutAdapter listAdapter;
    private MenuItem demographics;

    private final ListView.OnItemClickListener itemListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(listAdapter.getAction(position));
            startActivity(i);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        aboutList = (StickyListHeadersListView) findViewById(R.id.lv_about_contributors);

        listAdapter = new AboutAdapter(this);
        aboutList.setAdapter(listAdapter);
        aboutList.setOnItemClickListener(itemListener);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_about, menu);

        demographics = menu.findItem(R.id.mi_about_analytics);
        demographics.setChecked(!Storage.getInstance(this).getAnalyticsOptOut());

        return true;
    }

    public void openAfn(MenuItem item) {
        Intent webIntent = new Intent(Intent.ACTION_VIEW);
        webIntent.setData(Uri.parse(Storage.getInstance(getBaseContext()).getLinks().getURLS().get(LinksContainer.AFN)));
        startActivity(webIntent);
    }

    public void analyticsOptOut(MenuItem item) {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setMessage(getResources().getString(R.string.analytics_message))
                .setCancelable(true)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        demographics.setChecked(true);
                        dialog.dismiss();
                        Storage.getInstance(AboutActivity.this).setAnalyticsOptOut(false);
                        GoogleAnalytics.getInstance(getApplicationContext()).setAppOptOut(false);
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        demographics.setChecked(false);
                        dialog.dismiss();
                        Storage.getInstance(AboutActivity.this).setAnalyticsOptOut(true);
                        GoogleAnalytics.getInstance(getApplicationContext()).setAppOptOut(true);
                    }
                }).create();
        dialog.show();
    }
}
