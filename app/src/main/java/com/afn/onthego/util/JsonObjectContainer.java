package com.afn.onthego.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Kyle on 5/3/2015.
 */
public abstract class JsonObjectContainer {
    protected SharedPreferences prefs;
    protected String json;
    protected Context context;
    protected Type listType;
    protected Gson gson;
    protected String prefsLocation;

    public static String LOG_TAG;

    /**
     * Sets up the JsonObjectContainer.
     *
     * @param context    Context for the container to use
     */
    public JsonObjectContainer(Context context)
    {
        // this context will be used for prefs and toasts
        this.context = context;

        // prefs will set/get locations from prefs
        prefs = PreferenceManager.getDefaultSharedPreferences(context);

        // This will convert string json to the correct type
        this.listType = new TypeToken<ArrayList<HashMap<String, String>>>() {
        }.getType();
        this.gson = new Gson();


    }

    /**
     * Gets the json from prefs
     *
     * @return
     */
    public boolean setJsonFromPrefs() {
        json = prefs.getString(prefsLocation, "[]");
        return true;
    }

    /**
     * Takes in new json string and attempts to update it.  First it must check if
     * the json is valid, then it sets the objects to that and updates the prefs.
     *
     * @param newJson    json to update
     */
    public void update(String newJson) {
        if(checkValidJsonType(newJson)) {
            json = newJson;
            setJsonObjects();
            setJsonToPrefs();
        }
        else
        {
            Toast.makeText(context, "Failed updating", Toast.LENGTH_SHORT).show();
            Log.e(LOG_TAG, "Failed to update because JSON is not valid");
        }
    }

    /**
     * Takes the json and sets it to the prefs location
     */
    public void setJsonToPrefs() {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(prefsLocation, json);
        editor.apply();
    }

    /**
     * Using gson it validates if the creations is valid
     *
     * @param json    json string
     * @return        success of validation
     */
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

    /**
     * Take the json and assign it to the objects.
     */
    public abstract void setJsonObjects();

}
