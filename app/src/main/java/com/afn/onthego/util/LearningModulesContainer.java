package com.afn.onthego.util;

import android.content.Context;
import android.util.Log;

import com.afn.onthego.storage.KeyList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Kyle on 4/14/2015.
 */
public class LearningModulesContainer extends JsonObjectContainer {
    private ArrayList<LearningModule> learningModulesArray;

    public static final String LOG_TAG = "LearningModules";

    public LearningModulesContainer(Context context) {
        super(context);

        this.prefsLocation = KeyList.LearningModulesKeys.PREFS_LEARNING_MODULES;

        // Stores learning modules
        learningModulesArray = new ArrayList<LearningModule>();

        // To initialize learning modules we get json from prefs and set them.
        setJsonFromPrefs();
        setJsonObjects();
    }

    @Override
    public void setJsonObjects() {
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

}
