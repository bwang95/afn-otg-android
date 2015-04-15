package com.afn.onthego.async;

import android.content.Context;
import android.os.AsyncTask;

import com.afn.onthego.storage.Storage;


/**
 * Created by brian on 4/8/15.
 */
public class LearnRequest extends AsyncTask<Void, Void, Void> {

    private Storage storage;
    private Context context;

    public LearnRequest(Context context)
    {
        this.context = context;
    }

    @Override
    protected Void doInBackground(Void... params) {

        // this will initialize the LearningModules
        storage = Storage.getInstance(context);
        return null;
    }

}
