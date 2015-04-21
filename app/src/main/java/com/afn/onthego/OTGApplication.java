package com.afn.onthego;

import android.app.Application;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

import java.util.HashMap;

/**
 * Created by brian on 4/20/15.
 */
public class OTGApplication extends Application {

    // The following line should be changed to include the correct property id.
    private static final String PROPERTY_ID = "UA-61798957-2";
    private GoogleAnalytics analytics;
    Tracker appTracker = null;

    @Override
    public void onCreate() {
        super.onCreate();
        getTracker();
        appTracker.enableAdvertisingIdCollection(true);
    }

    public synchronized Tracker getTracker() {
        if(analytics == null)
            analytics = GoogleAnalytics.getInstance(this);
        if(appTracker == null)
            appTracker = analytics.newTracker(R.xml.analytics_tracker);
        return appTracker;
    }
}