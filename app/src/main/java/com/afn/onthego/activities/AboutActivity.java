package com.afn.onthego.activities;

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
import com.afn.onthego.storage.KeyList;
import com.afn.onthego.storage.Storage;
import com.afn.onthego.util.Links;

import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

public class AboutActivity extends ActionBarActivity {

    private StickyListHeadersListView aboutList;
    private AboutAdapter listAdapter;

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
        return true;
    }

    public void openAfn(MenuItem item) {
        Intent webIntent = new Intent(Intent.ACTION_VIEW);
        webIntent.setData(Uri.parse(Storage.getInstance(getBaseContext()).getLinks().getURLS().get(Links.AFN)));
        startActivity(webIntent);
    }
}
