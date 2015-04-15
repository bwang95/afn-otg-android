package com.afn.onthego.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.afn.onthego.storage.KeyList;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.OkHttpClient;

/**
 * Created by Kyle on 4/14/2015.
 */
public class LearningModules {
    private SharedPreferences prefs;
    private ArrayList<LearningModule> learningModulesArray;
    private boolean webGetSuccess;
    private String json;

    public LearningModules(Context context)
    {
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        learningModulesArray = new ArrayList<LearningModule>();

        try {
            webGetSuccess = setJsonFromWeb();
        }
        catch (IOException e)
        {
            json = "";
            webGetSuccess = false;
        }

        if(webGetSuccess)
        {
            setJsonToPrefs();
        }
        else
        {
            setJsonFromPrefs();
        }

        setLearningModules();
    }

    public boolean setJsonFromPrefs()
    {
        json = prefs.getString(KeyList.LearningModulesKeys.PREFS_LEARNING_MODULES, "");
        return true;
    }

    public boolean setJsonFromWeb() throws IOException
    {
        // get url from keylist
        String url = KeyList.LearningModulesKeys.URL;

        // setup client
        OkHttpClient client = new OkHttpClient();

        // request building
        Request request = new Request.Builder().url(url).build();

        // send request, get response
        Response response = client.newCall(request).execute();

        if(response.isSuccessful())
        {
            json = response.body().string();
            return true;
        }
        else
        {
            return false;
        }
    }

    /*
    Returns true if webGet was successful and if the json has changed
    Else returns false
     */
    public boolean webUpdate() throws IOException
    {
        String old_json = json;
        try
        {
            webGetSuccess = setJsonFromWeb();
        }
        catch (IOException e)
        {
            webGetSuccess = false;
        }

        if(webGetSuccess && !old_json.equals(json))
        {
            setJsonToPrefs();
            setLearningModules();
            return true;
        }
        else
        {
            return false;
        }
    }

    public String getJson()
    {
        return json;
    }

    public void setJsonToPrefs()
    {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(KeyList.LearningModulesKeys.PREFS_LEARNING_MODULES, json);
        editor.commit();
    }

    private void setLearningModules()
    {
        if(json == null || json.equals(""))
        {
            return;
        }
        learningModulesArray.clear();

        // convert from json
        Type listType = new TypeToken<ArrayList<HashMap<String, String>>>(){}.getType();
        Gson gson = new Gson();

        ArrayList<HashMap<String, String>> myList = gson.fromJson(json, listType);

        // iterate through list and add modules
        for(HashMap<String, String> m : myList)
        {
            for(Map.Entry<String, String> entry : m.entrySet())
            {
                Log.d("LearningModules", entry.getKey() + " " + entry.getValue());
            }
            LearningModule learningModule = new LearningModule();
            learningModule.setName(m.get(KeyList.LearningModulesKeys.LEARNING_MODULE_NAME));
            learningModule.setType(m.get(KeyList.LearningModulesKeys.LEARNING_MODULE_TYPE));
            learningModule.setData(m.get(KeyList.LearningModulesKeys.LEARNING_MODULE_DATA));
            learningModule.setPosition(m.get(KeyList.LearningModulesKeys.LEARNING_MODULE_POSITION));
            learningModulesArray.add(learningModule);
            Log.d("LearningModules", "Position: " + learningModule.getPosition());
        }

        if(learningModulesArray.isEmpty())
        {
            Log.d("LearningModules", "Learning Modules is Empty");
        }
        else
        {
            Log.d("LearningModules", "Number of modules " + Integer.toString(learningModulesArray.size()));
        }
        // sort learning modules
        Collections.sort(learningModulesArray);
    }

    public ArrayList<LearningModule> getLearningModulesArray()
    {
        return learningModulesArray;
    }

    public ArrayList<String> getModulesNamesArray() {
        ArrayList<String> modulesNamesArray = new ArrayList<String>();
        for(LearningModule learningModule : learningModulesArray)
        {
            modulesNamesArray.add(learningModule.getName());
        }
        return modulesNamesArray;
    }

    public boolean isWebGetSuccess()
    {
        return webGetSuccess;
    }


}
