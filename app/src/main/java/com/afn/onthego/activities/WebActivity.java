package com.afn.onthego.activities;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.afn.onthego.R;
import com.afn.onthego.storage.KeyList;

public class WebActivity extends ActionBarActivity {

    private ProgressBar loadingBar;
    private WebView webView;

    private String website_url;

    private boolean purgeCookies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        loadingBar = (ProgressBar) findViewById(R.id.pb_web_progress);
        loadingBar.setProgress(0);
        loadingBar.setMax(100);
        loadingBar.setIndeterminate(false);

        purgeCookies = getIntent().getBooleanExtra(KeyList.ActivityParams.KEY_PURGE_COOKIES, false);

        webView = (WebView) findViewById(R.id.wv_web_website);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setBuiltInZoomControls(true);
        settings.setSupportZoom(true);

        webView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                loadingBar.setProgress(progress);
                if (loadingBar.getProgress() >= 100)
                    loadingBar.setVisibility(View.GONE);

            }
        });

        webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // do your handling codes here, which url is the requested url
                // probably you need to open that url rather than redirect:
                view.loadUrl(url);
                return false; // then it is not handled by default action
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
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onBackPressed() {
        if(purgeCookies) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                CookieManager manager = CookieManager.getInstance();
                manager.removeAllCookies(null);
            } else {
                CookieSyncManager.createInstance(this);
                CookieManager manager = CookieManager.getInstance();
                manager.removeAllCookie();
            }
        }
        super.onBackPressed();
    }
}
