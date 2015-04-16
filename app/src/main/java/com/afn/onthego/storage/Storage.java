package com.afn.onthego.storage;

import android.content.Context;

import com.afn.onthego.async.LearnRequest;
import com.afn.onthego.util.LearningModules;


/**
 * Created by brian on 4/8/15.
 */
public class Storage implements LearnRequest.LearnRequestListener {
    private static Storage instance;

    private Context context;
    private LearningModules learningModules;

    private Storage(Context context) {
        this.context = context;
        learningModules = new LearningModules(context);
        new LearnRequest(this).execute();
    }

    public LearningModules getLearningModules()
    {
        return learningModules;
    }

    public static Storage getInstance(Context context) {
        return instance == null ? instance = new Storage(context) : instance;
    }

    @Override
    public void onLearnRequestSuccess(String s) {
        learningModules.updateModules(s);
    }

    @Override
    public void onLearnRequestFailure() {

    }
}
