package com.afn.onthego.util;

import android.content.Context;
import android.util.Log;

import com.afn.onthego.storage.KeyList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Kyle on 5/2/2015.
 */
public class LinksContainer extends JsonObjectContainer {
    public static final String QUESTION = "QUESTION";
    public static final String VOLUNTEER_SIGN_UP = "VOLUNTEER_SIGN_UP";
    public static final String VOLUNTEER_SIGN_IN = "VOLUNTEER_SIGN_IN";
    public static final String DONATE = "DONATE";
    public static final String AFN = "AFN";


    private HashMap<String, String> URLS;

    public static final String LOG_TAG = "Links";

    public LinksContainer(Context context)
    {
        super(context);

        URLS = new HashMap<String, String>();

        this.prefsLocation = KeyList.LinkKeys.PREFS_LINKS;

        setJsonToPrefs();
        setJsonObjects();
    }

    @Override
    public void setJsonObjects() {
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
