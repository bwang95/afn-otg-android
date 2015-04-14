package com.afn.onthego.storage;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.HashMap;



/**
 * Created by brian on 4/8/15.
 */
public class Storage {
    private static Storage instance;

    private boolean webGetSuccess;

    SharedPreferences prefs;

    static final String LEARNING_MODULE_KEY_NAME = "name";
    static final String LEARNING_MODULE_KEY_TYPE = "type";
    static final String LEARNING_MODULE_KEY_DATA = "data";
    static final String LEARNING_MODULE_KEY_POSITION = "position";



    ArrayList<HashMap<String, String>> items;


    private Storage() {
        //prefs = PreferenceManager.getDefaultSharedPreferences();
        webGetSuccess = false;

        items = getItemsFromPreferences();
    }

    public void setWebGetSuccess(boolean success)
    {
        webGetSuccess = success;
    }

    private ArrayList<HashMap<String, String>> getItemsFromPreferences()
    {
        return null;
    }


    public static Storage getInstance() {
        return instance == null ? instance = new Storage() : instance;
    }
}
