package com.afn.onthego.util;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Kyle on 4/28/2015.
 */
public class Location implements Comparable<Location> {
    public String name;
    public String position;
    public LatLng latLng;

    public static final String LOG_TAG = "Location";

    public Location() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    @Override
    public int compareTo(Location another) {
        try {
            return new Integer(Integer.parseInt(position))
                    .compareTo(Integer.parseInt(another.position));
        } catch (Exception e) {
            Log.e(LOG_TAG, "Integer exception caught!", e);
            return 0;
        }
    }
}
