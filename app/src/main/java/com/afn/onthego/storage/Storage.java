package com.afn.onthego.storage;

import android.content.Context;

import com.afn.onthego.async.LearnRequest;
import com.afn.onthego.async.LocationRequest;
import com.afn.onthego.util.LearningModules;
import com.afn.onthego.util.Location;
import com.afn.onthego.util.LocationsContainer;
import com.squareup.okhttp.OkHttpClient;


/**
 * Created by brian on 4/8/15.
 */
public class Storage implements LearnRequest.LearnRequestListener, LocationRequest.LocationRequestListener{
    private static Storage instance;

    private Context context;
    private LearningModules learningModules;
    private LocationsContainer locationsContainer;
    private OkHttpClient httpClient;

    private Storage(Context context) {
        this.context = context;
        learningModules = new LearningModules(context);
        locationsContainer = new LocationsContainer(context);
        httpClient = new OkHttpClient();

        new LearnRequest(context, this).execute();
        new LocationRequest(context, this).execute();
    }

    public LearningModules getLearningModules()
    {
        return learningModules;
    }

    public LocationsContainer getLocationsContainer()
    {
        return locationsContainer;
    }

    public static Storage getInstance(Context context) {
        return instance == null ? instance = new Storage(context) : instance;
    }

    @Override
    public void onLearnRequestSuccess(String s) {
        learningModules.updateModules(s);
    }

    @Override
    public void onLearnRequestFailure() {

    }

    public OkHttpClient getHttpClient() {
        return httpClient;
    }

    @Override
    public void onLocationRequestSuccess(String s) {
        locationsContainer.updateModules(s);
    }

    @Override
    public void onLocationRequestFailure() {

    }
}
