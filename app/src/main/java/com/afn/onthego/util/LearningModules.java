package com.afn.onthego.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.afn.onthego.storage.KeyList;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.security.KeyException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Kyle on 4/14/2015.
 */
public class LearningModules {
    private SharedPreferences prefs;
    private ArrayList<LearningModule> learningModulesArray;
    private String json;
    private Context context;
    private Type listType;
    private Gson gson;

    public static final String LOG_TAG = "LearningModules";

    public LearningModules(Context context) {
        this.context = context;

        // Prefs will get/set learning modules to memory
        prefs = PreferenceManager.getDefaultSharedPreferences(context);

        // Stores learning modules
        learningModulesArray = new ArrayList<LearningModule>();

        // This will convert string json to the correct type
        this.listType = new TypeToken<ArrayList<HashMap<String, String>>>() {
        }.getType();
        this.gson = new Gson();

        // To initialize learning modules we get json from prefs and set them.
        setJsonFromPrefs();
        setLearningModules();
    }

    public boolean setJsonFromPrefs() {
        json = prefs.getString(KeyList.LearningModulesKeys.PREFS_LEARNING_MODULES, "[]");
        return true;
    }

    public void updateModules(String newJson) {
        if(checkValidJsonType(newJson)) {
            json = newJson;
            setLearningModules();
            setJsonToPrefs();
        }
        else
        {
            Toast.makeText(context, "Failed updating learning modules", Toast.LENGTH_SHORT).show();
            Log.e(LOG_TAG, "Failed to updateModules because JSON is not valid");
        }
    }

    public void setJsonToPrefs() {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(KeyList.LearningModulesKeys.PREFS_LEARNING_MODULES, json);
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

    private void setLearningModules() {
        if (json == null || json.equals("")) {
            return;
        }
        HashMap<String, String> urlsToPdfs = new HashMap<>();
        for(LearningModule module : learningModulesArray) {
            if (module.getType().toLowerCase().equals("pdf") &&
                    module.getFileName() != null &&
                    !module.getFileName().isEmpty()) {
                urlsToPdfs.put(module.getData(), module.getFileName());
            }
        }
        // convert from json

        ArrayList<HashMap<String,String>> myList;

        myList = gson.fromJson(json, listType);

        learningModulesArray.clear();

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

            learningModulesArray.add(learningModule);
            Log.d(LOG_TAG, "Position: " + learningModule.getPosition());
        }

        if (learningModulesArray.isEmpty()) {
            Log.d(LOG_TAG, "Learning Modules is Empty");
        } else {
            Log.d(LOG_TAG, "Number of modules " + Integer.toString(learningModulesArray.size()));
        }
        // sort learning modules
        Collections.sort(learningModulesArray);
    }

    public ArrayList<LearningModule> getLearningModulesArray() {
        return learningModulesArray;
    }

    public ArrayList<String> getModulesNamesArray() {
        ArrayList<String> modulesNamesArray = new ArrayList<String>();
        for (LearningModule learningModule : learningModulesArray) {
            modulesNamesArray.add(learningModule.getName());
        }
        return modulesNamesArray;
    }


}
