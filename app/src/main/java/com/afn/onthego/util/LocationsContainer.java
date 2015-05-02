package com.afn.onthego.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.afn.onthego.storage.KeyList;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Kyle on 4/14/2015.
 */
public class LocationsContainer {
    private SharedPreferences prefs;
    private ArrayList<Location> locationsArrays;
    private String json;
    private Context context;
    private Type listType;
    private Gson gson;

    public static final String LOG_TAG = "LocationsContainer";

    public LocationsContainer(Context context) {
        this.context = context;

        // prefs will set/get locations from prefs
        prefs = PreferenceManager.getDefaultSharedPreferences(context);

        // to store locations
        locationsArrays = new ArrayList<Location>();

        // This will convert string json to the correct type
        this.listType = new TypeToken<ArrayList<HashMap<String, String>>>() {
        }.getType();
        this.gson = new Gson();

        // To initialize locations we get json from prefs and set them.
        setJsonFromPrefs();
        setLocations();
    }

    public boolean setJsonFromPrefs() {
        json = prefs.getString(KeyList.LocationsKeys.PREFS_LOCATIONS, "[]");
        return true;
    }

    public void updateLocations(String newJson) {
        if(checkValidJsonType(newJson)) {
            json = newJson;
            setLocations();
            setJsonToPrefs();
        }
        else
        {
            Toast.makeText(context, "Failed updating learning modules", Toast.LENGTH_SHORT).show();
            Log.e(LOG_TAG, "Failed to updateLocations because JSON is not valid");
        }
    }

    public void setJsonToPrefs() {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(KeyList.LocationsKeys.PREFS_LOCATIONS, json);
        editor.apply();
    }

    private boolean checkValidJsonType(String json)
    {
        try
        {
            gson.fromJson(json, listType);
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }

    private void setLocations() {
        if (json == null || json.equals("")) {
            return;
        }
        locationsArrays.clear();


        ArrayList<HashMap<String, String>> myList = gson.fromJson(json, listType);

        // iterate through list and add modules
        for (HashMap<String, String> m : myList) {
            for (Map.Entry<String, String> entry : m.entrySet()) {
                Log.d(LOG_TAG, entry.getKey() + " " + entry.getValue());
            }

            try {
                Location location = new Location();
                double locLat = Double.parseDouble(m.get(KeyList.LocationsKeys.LOCATION_LATITUDE));
                double locLng = Double.parseDouble(m.get(KeyList.LocationsKeys.LOCATION_LONGITUDE));
                LatLng latLng = new LatLng(locLat, locLng);
                location.setName(m.get(KeyList.LocationsKeys.LOCATION_NAME));
                location.setLatLng(latLng);
                location.setPosition(m.get(KeyList.LocationsKeys.LOCATION_POSITION));

                locationsArrays.add(location);
            }
            catch (Exception e)
            {
                Log.e(LOG_TAG, "Double exception caught", e);
            }
        }

        if (locationsArrays.isEmpty()) {
            Log.d(LOG_TAG, "Learning Modules is Empty");
        } else {
            Log.d(LOG_TAG, "Number of modules " + Integer.toString(locationsArrays.size()));
        }
        // sort learning modules
        Collections.sort(locationsArrays);
    }

    public ArrayList<Location> getLocationsArrays() {
        return locationsArrays;
    }

    public ArrayList<String> getLocationsNamesArray() {
        ArrayList<String> modulesNamesArray = new ArrayList<String>();
        for (Location location : locationsArrays) {
            modulesNamesArray.add(location.getName());
        }
        return modulesNamesArray;
    }


}
