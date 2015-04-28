package com.afn.onthego.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.afn.onthego.storage.KeyList;
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

    public static final String LOG_TAG = "LearningModules";

    public LocationsContainer(Context context) {
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        locationsArrays = new ArrayList<Location>();
        setJsonFromPrefs();
        setLearningModules();
    }

    public boolean setJsonFromPrefs() {
        json = prefs.getString(KeyList.LearningModulesKeys.PREFS_LEARNING_MODULES, "");
        return true;
    }

    public void updateModules(String newJson) {
        json = newJson;
        setLearningModules();
        setJsonToPrefs();
    }

    public void setJsonToPrefs() {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(KeyList.LearningModulesKeys.PREFS_LEARNING_MODULES, json);
        editor.apply();
    }

    private void setLearningModules() {
        if (json == null || json.equals("")) {
            return;
        }
        locationsArrays.clear();

        // convert from json
        Type listType = new TypeToken<ArrayList<HashMap<String, String>>>() {
        }.getType();
        Gson gson = new Gson();

        ArrayList<HashMap<String, String>> myList = gson.fromJson(json, listType);

        // iterate through list and add modules
        for (HashMap<String, String> m : myList) {
            for (Map.Entry<String, String> entry : m.entrySet()) {
                Log.d(LOG_TAG, entry.getKey() + " " + entry.getValue());
            }
            LearningModule learningModule = new LearningModule();
            learningModule.setName(m.get(KeyList.LearningModulesKeys.LEARNING_MODULE_NAME));
            learningModule.setType(m.get(KeyList.LearningModulesKeys.LEARNING_MODULE_TYPE));
            learningModule.setData(m.get(KeyList.LearningModulesKeys.LEARNING_MODULE_DATA));
            learningModule.setPosition(m.get(KeyList.LearningModulesKeys.LEARNING_MODULE_POSITION));

            if(urlsToPdfs.containsKey(learningModule.getData()))
                learningModule.setFileName(urlsToPdfs.get(learningModule.getData()));

            locationsArrays.add(learningModule);
            Log.d(LOG_TAG, "Position: " + learningModule.getPosition());
        }

        if (locationsArrays.isEmpty()) {
            Log.d(LOG_TAG, "Learning Modules is Empty");
        } else {
            Log.d(LOG_TAG, "Number of modules " + Integer.toString(locationsArrays.size()));
        }
        // sort learning modules
        Collections.sort(locationsArrays);
    }

    public ArrayList<LearningModule> getLocationsArrays() {
        return locationsArrays;
    }

    public ArrayList<String> getModulesNamesArray() {
        ArrayList<String> modulesNamesArray = new ArrayList<String>();
        for (LearningModule learningModule : locationsArrays) {
            modulesNamesArray.add(learningModule.getName());
        }
        return modulesNamesArray;
    }


}
