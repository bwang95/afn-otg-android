package com.afn.onthego.activities;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.afn.onthego.R;
import com.afn.onthego.storage.Storage;
import com.afn.onthego.util.LinksContainer;

public class DonateActivity extends ActionBarActivity {

    private View.OnClickListener donateListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent webIntent = new Intent(Intent.ACTION_VIEW);
            webIntent.setData(Uri.parse(Storage.getInstance(getBaseContext()).getLinks().getURLS().get(LinksContainer.DONATE)));
            startActivity(webIntent);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FrameLayout donateLayout = (FrameLayout) findViewById(R.id.fl_donate_button);
        donateLayout.setOnClickListener(donateListener);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_donate, menu);
        return true;
    }

    public void openAfn(MenuItem item) {
        Intent webIntent = new Intent(Intent.ACTION_VIEW);
        webIntent.setData(Uri.parse(Storage.getInstance(getBaseContext()).getLinks().getURLS().get(LinksContainer.AFN)));
        startActivity(webIntent);
    }

    public void openAfn(View view) {
        Intent webIntent = new Intent(Intent.ACTION_VIEW);
        webIntent.setData(Uri.parse(Storage.getInstance(getBaseContext()).getLinks().getURLS().get(LinksContainer.AFN)));
        startActivity(webIntent);
    }
}
