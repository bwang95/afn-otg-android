package com.afn.onthego.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.afn.onthego.R;
import com.afn.onthego.storage.KeyList;
import com.joanzapata.pdfview.PDFView;

import java.io.File;

public class PDFActivity extends ActionBarActivity {

    PDFView pdfView;
    File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);

        pdfView = (PDFView) findViewById(R.id.pdfv_pdf);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String filename = getIntent().getStringExtra(KeyList.ActivityParams.KEY_FILENAME);
        if (filename == null) {
            Toast.makeText(this, "Invalid PDF provided.", Toast.LENGTH_LONG).show();
            finish();
        } else {
            file = new File(filename);
            pdfView.fromFile(file).load();
        }

        String title = getIntent().getStringExtra(KeyList.ActivityParams.KEY_TITLE);
        if(title != null)
            getSupportActionBar().setTitle(title);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pdf, menu);
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
        } else if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
