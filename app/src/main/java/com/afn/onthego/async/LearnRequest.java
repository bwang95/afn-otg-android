package com.afn.onthego.async;

import android.os.AsyncTask;

import com.squareup.okhttp.Request;

/**
 * Created by brian on 4/8/15.
 */
public class LearnRequest extends AsyncTask<Void, Void, String> {

    private LearnRequestListener callback;
    private Request request;

    public LearnRequest(LearnRequestListener callback) {
        this.callback = callback;
    }

    @Override
    protected String doInBackground(Void... params) {
        return null;
    }

    public interface LearnRequestListener {
        void onLearnRequestFailure();
        void onLearnRequestSuccess(String result);
    }
}
