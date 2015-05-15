package com.afn.onthego.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.afn.onthego.R;
import com.afn.onthego.adapters.LearnAdapter;
import com.afn.onthego.app.OTGApplication;
import com.afn.onthego.async.LearnRequest;
import com.afn.onthego.async.PDFRequest;
import com.afn.onthego.storage.KeyList;
import com.afn.onthego.storage.Storage;
import com.afn.onthego.util.LearningModule;
import com.afn.onthego.util.LinksContainer;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.util.ArrayList;

public class LearnActivity extends ActionBarActivity
        implements PDFRequest.PDFRequestListener,
        SwipeRefreshLayout.OnRefreshListener,
        LearnRequest.LearnRequestListener {

    public ListView listView;
    public LearnAdapter listAdapter;

    public ArrayList<LearningModule> learningModules;

    private SwipeRefreshLayout swipeRefreshLayout;
    private ProgressDialog progressDialog;

    private ListView.OnItemClickListener learnModuleListener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            LearningModule learningModule = learningModules.get(position);

            Tracker t = ((OTGApplication) getApplication()).getTracker();
            t.send(new HitBuilders.EventBuilder()
                    .setCategory(KeyList.Analytics.CATEGORY_LEARN)
                    .setAction(KeyList.Analytics.ACTION_LEARNING_MODULE)
                    .setLabel(learningModule.getName()).build());

            switch (learningModule.getType()) {
                case KeyList.LearningModulesKeys.TYPE_PDF:
                    handlePDF(learningModule);
                    break;
                case KeyList.LearningModulesKeys.TYPE_WEBSITE:
                    handleWebsite(learningModule);
                    break;
                case KeyList.LearningModulesKeys.TYPE_YOUTUBE:
                    handleYouTube(learningModule);
                    break;
                default:
                    Toast.makeText(LearnActivity.this, learningModule.getType() + " not recognized", Toast.LENGTH_LONG).show();
            }
        }
    };

    private String PDFName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.srl_learn_swipe_container);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(
                R.color.afn_blue_bright,
                R.color.afn_green_light,
                R.color.holo_orange_light,
                R.color.red_light);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading PDF, please wait...");
        progressDialog.setCancelable(false);

        listView = (ListView) findViewById(R.id.lv_learn_module_list);
        Storage storage = Storage.getInstance(this);
        learningModules = storage.getLearningModules().getLearningModulesArray();
        listAdapter = new LearnAdapter(this, learningModules);

        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(learnModuleListener);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_learn, menu);
        return true;
    }

    public void handlePDF(LearningModule learningModule) {
        progressDialog.show();

        PDFName = learningModule.getName();
        String fileName = learningModule.getFileName();

        if (fileName == "") {
            PDFRequest request = new PDFRequest(learningModule, this, this);
            request.execute();
        } else {
            onPDFRequestSuccess(fileName);
        }
    }

    public void handleWebsite(LearningModule learningModule) {
        String website_url = learningModule.getData();
        if (URLUtil.isValidUrl(website_url)) {
            Intent webIntent = new Intent(this, WebActivity.class);
            webIntent.putExtra(KeyList.ActivityParams.KEY_URL, website_url);
            webIntent.putExtra(KeyList.ActivityParams.KEY_TITLE, learningModule.getName());
            startActivity(webIntent);
        } else {
            Toast.makeText(this, website_url + " is an invalid URL", Toast.LENGTH_LONG).show();
        }
    }

    public void handleYouTube(LearningModule learningModule) {
        String youtube_url = learningModule.getData();
        if (URLUtil.isValidUrl(youtube_url)) {
            Intent youtube_intent = new Intent(Intent.ACTION_VIEW);
            youtube_intent.setData(Uri.parse(youtube_url));
            startActivity(youtube_intent);
        } else {
            Toast.makeText(this, youtube_url + " is an invalid URL", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onRefresh() {
        new LearnRequest(this, this).execute();
    }

    public void onPDFRequestSuccess(String filename) {
        progressDialog.hide();
        Intent pdfIntent = new Intent(this, PDFActivity.class);
        pdfIntent.putExtra(KeyList.ActivityParams.KEY_FILENAME, filename);
        pdfIntent.putExtra(KeyList.ActivityParams.KEY_TITLE, PDFName);
        startActivity(pdfIntent);
    }

    @Override
    public void onLearnRequestSuccess(String json) {
        Storage storage = Storage.getInstance(this);
        storage.getLearningModules().update(json);

        //TODO I don't think you have to clear the adapter here.
        listAdapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
    }

    public void onPDFRequestFailure() {
        progressDialog.hide();
        Toast.makeText(this, "Something went wrong loading the PDF file.", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onLearnRequestFailure() {
        Toast.makeText(this, "Could not load from web", Toast.LENGTH_LONG).show();
        swipeRefreshLayout.setRefreshing(false);
    }

    public void openAfn(MenuItem item) {
        Intent webIntent = new Intent(Intent.ACTION_VIEW);
        webIntent.setData(Uri.parse(Storage.getInstance(getBaseContext()).getLinks().getURLS().get(LinksContainer.AFN)));
        startActivity(webIntent);
    }
}
