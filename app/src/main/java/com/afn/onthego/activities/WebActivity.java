package com.afn.onthego.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.afn.onthego.R;
import com.afn.onthego.storage.KeyList;

public class WebActivity extends ActionBarActivity {

    private ProgressBar loadingBar;
    private WebView webView;

    private String website_url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        loadingBar = (ProgressBar) findViewById(R.id.pb_web_progress);
        loadingBar.setProgress(0);
        loadingBar.setMax(100);
        loadingBar.setIndeterminate(false);

        webView = (WebView) findViewById(R.id.wv_web_website);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);

        webView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                loadingBar.setProgress(progress);
                if(loadingBar.getProgress() >= 100)
                    loadingBar.setVisibility(View.GONE);
            }
        });



        website_url = getIntent().getStringExtra(KeyList.ActivityParams.KEY_URL);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (website_url == null) {
            Toast.makeText(this, "No URL provided.", Toast.LENGTH_LONG).show();
            finish();
        } else
            webView.loadUrl(website_url);

        String title = getIntent().getStringExtra(KeyList.ActivityParams.KEY_TITLE);
        if (title != null)
            getSupportActionBar().setTitle(title);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_web, menu);
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
