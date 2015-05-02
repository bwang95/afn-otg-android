package com.afn.onthego.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.afn.onthego.storage.KeyList;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Kyle on 5/2/2015.
 */
public class Links {
    public static final String QUESTION = "QUESTION";
    public static final String VOLUNTEER_SIGN_UP = "VOLUNTEER_SIGN_UP";
    public static final String VOLUNTEER_SIGN_IN = "VOLUNTEER_SIGN_IN";
    public static final String DONATE = "DONATE";
    public static final String AFN = "AFN";


    private HashMap<String, String> URLS;

    private SharedPreferences prefs;
    private String json;
    private Context context;
    private Type listType;
    private Gson gson;

    public static final String LOG_TAG = "Links";

    public Links(Context context)
    {
        this.context = context;

        // prefs will set/get locations from prefs
        prefs = PreferenceManager.getDefaultSharedPreferences(context);

        // This will convert string json to the correct type
        this.listType = new TypeToken<ArrayList<HashMap<String, String>>>() {
        }.getType();
        this.gson = new Gson();

        URLS = new HashMap<String, String>();
    }

    public boolean setJsonFromPrefs() {
        json = prefs.getString(KeyList.LinkKeys.PREFS_LINKS, "[]");
        return true;
    }

    public void updateLinks(String newJson) {
        if(checkValidJsonType(newJson)) {
            json = newJson;
            setLinks();
            setJsonToPrefs();
        }
        else
        {
            Toast.makeText(context, "Failed updating links", Toast.LENGTH_SHORT).show();
            Log.e(LOG_TAG, "Failed to updateLinks because JSON is not valid");
        }
    }

    public void setJsonToPrefs() {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(KeyList.LinkKeys.PREFS_LINKS, json);
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

    public void setLinks()
    {
        if (json == null || json.equals("")) {
            return;
        }
        URLS.clear();

        ArrayList<HashMap<String, String>> myList = gson.fromJson(json, listType);

        // iterate through list and add modules
        for (HashMap<String, String> m : myList) {
            for (Map.Entry<String, String> entry : m.entrySet()) {
                Log.d(LOG_TAG, entry.getKey() + " " + entry.getValue());
            }

            // put links into the map of urls
            URLS.put(m.get(KeyList.LinkKeys.LINK_NAME), m.get(KeyList.LinkKeys.LINK_URL));
        }

        if (URLS.isEmpty()) {
            Log.d(LOG_TAG, "URLS is Empty");
        }
    }

    public HashMap<String, String> getURLS()
    {
        return URLS;
    }
}
