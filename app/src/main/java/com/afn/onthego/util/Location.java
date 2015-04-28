package com.afn.onthego.util;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Kyle on 4/28/2015.
 */
public class Location {


    private String name;
    private String latitude;
    private String longitude;
    private String position;

    private LatLng latLng;

    public Location() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
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

}
