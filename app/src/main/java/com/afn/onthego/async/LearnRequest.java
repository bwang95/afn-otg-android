package com.afn.onthego.async;

import android.content.Context;
import android.os.AsyncTask;

import com.afn.onthego.storage.Storage;
import com.afn.onthego.storage.KeyList;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.OkHttpClient;

import java.io.IOException;

/**
 * Created by brian on 4/8/15.
 */
public class LearnRequest extends AsyncTask<Void, Void, String> {

    private Request request;
    private Response response;
    private OkHttpClient client;
    private Storage storage;
    private String url;

    Context context;

    public LearnRequest(Context context)
    {
        this.context = context;
    }

    String doGetRequest() throws IOException
    {
        // get url from keylist
        url = KeyList.LearnRequest.URL;

        // setup client
        client = new OkHttpClient();

        // request building
        request = new Request.Builder().url(url).build();

        // send request, get response
        response = client.newCall(request).execute();

        if(response.isSuccessful())
        {
            return response.body().string();
        }
        else
        {
            return "";
        }
    }

    @Override
    protected String doInBackground(Void... params) {
        storage = Storage.getInstance(context);
        String jsonResponse;
        try {
            jsonResponse = doGetRequest();
        }
        catch (IOException e)
        {
            jsonResponse = "";
        }

        if(jsonResponse != null && !jsonResponse.equals(""))
        {
            storage.storeAndSetLearningModules(jsonResponse);
        }

        return null;
    }

}
