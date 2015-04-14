package com.afn.onthego.storage;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.afn.onthego.util.LearningModule;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;


/**
 * Created by brian on 4/8/15.
 */
public class Storage {
    private static Storage instance;

    private boolean webGetSuccess;

    SharedPreferences prefs;

    ArrayList<LearningModule> learningModules;

    private Storage(Context context) {
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        webGetSuccess = false;

        learningModules = new ArrayList<LearningModule>();

        String learningModulesString = getLearningModulesStringFromPreferences();

        setLearningModules(learningModulesString);
    }

    private String getLearningModulesStringFromPreferences()
    {
        return prefs.getString(KeyList.Storage.PREFS_LEARNING_MODULES, "");
    }

    public void storeAndSetLearningModules(String learningModulesString)
    {
        // webGetSuccess is true
        webGetSuccess = true;

        // store learning modules string in prefs
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(KeyList.Storage.PREFS_LEARNING_MODULES, learningModulesString);
        editor.commit();

        // set learning modules
        setLearningModules(learningModulesString);
    }

    private void setLearningModules(String learningModulesString)
    {
        if(learningModulesString == null || learningModulesString.equals(""))
        {
            return;
        }
        learningModules.clear();

        // convert from json
        Type listType = new TypeToken<ArrayList< HashMap<String, String>>>(){}.getType();
        Gson gson = new Gson();

        ArrayList<HashMap<String, String>> myList = gson.fromJson(learningModulesString, listType);

        // iterate through list and add modules
        for(HashMap<String, String> m : myList)
        {
            LearningModule learningModule = new LearningModule();
            learningModule.setName(m.get(KeyList.Storage.LEARNING_MODULE_NAME));
            learningModule.setType(m.get(KeyList.Storage.LEARNING_MODULE_TYPE));
            learningModule.setData(m.get(KeyList.Storage.LEARNING_MODULE_DATA));
            learningModule.setPosition(m.get(KeyList.Storage.LEARNING_MODULE_POSITION));
            learningModules.add(learningModule);
        }

        // sort learning modules
        Collections.sort(learningModules);
    }

    public ArrayList<LearningModule> getLearningModules()
    {
        return learningModules;
    }

    public boolean isWebGetSuccess()
    {
        return webGetSuccess;
    }


    public static Storage getInstance(Context context) {
        return instance == null ? instance = new Storage(context) : instance;
    }
}
