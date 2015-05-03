package com.afn.onthego.storage;

import android.content.Context;

import com.afn.onthego.async.LearnRequest;
import com.afn.onthego.async.LinkRequest;
import com.afn.onthego.async.LocationRequest;
import com.afn.onthego.util.LearningModulesContainer;
import com.afn.onthego.util.LinksContainer;
import com.afn.onthego.util.LocationsContainer;
import com.squareup.okhttp.OkHttpClient;


/**
 * Created by brian on 4/8/15.
 */
public class Storage implements LearnRequest.LearnRequestListener,
        LocationRequest.LocationRequestListener,
        LinkRequest.LinkRequestListener{
    private static Storage instance;

    private Context context;
    private LearningModulesContainer learningModules;
    private LocationsContainer locationsContainer;
    private LinksContainer links;
    private OkHttpClient httpClient;

    private Storage(Context context) {
        this.context = context;
        learningModules = new LearningModulesContainer(context);
        locationsContainer = new LocationsContainer(context);
        links = new LinksContainer(context);
        httpClient = new OkHttpClient();

        new LearnRequest(context, this).execute();
        new LocationRequest(context, this).execute();
        new LinkRequest(context, this).execute();
    }

    public LearningModulesContainer getLearningModules()
    {
        return learningModules;
    }

    public LocationsContainer getLocationsContainer()
    {
        return locationsContainer;
    }

    public LinksContainer getLinks() { return links; }

    public static Storage getInstance(Context context) {
        return instance == null ? instance = new Storage(context) : instance;
    }

    @Override
    public void onLearnRequestSuccess(String s) {
        learningModules.update(s);
    }

    @Override
    public void onLearnRequestFailure() {

    }

    public OkHttpClient getHttpClient() {
        return httpClient;
    }

    @Override
    public void onLocationRequestSuccess(String s) {
        locationsContainer.update(s);
    }

    @Override
    public void onLocationRequestFailure() {

    }

    @Override
    public void onLinkRequestSuccess(String s) {
        links.update(s);
    }

    @Override
    public void onLinkRequestFailure() {

    }
}
