package com.afn.onthego.async;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.afn.onthego.storage.KeyList;
import com.afn.onthego.storage.Storage;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;


/**
 * Created by brian on 4/8/15.
 */
public class LocationRequest extends AsyncTask<Void, Void, String> {

    private static final String LOG_TAG = "LocationRequest";
    private LocationRequestListener callback;
    private Context context;

    // This is initiated in the MainActivity onCreate thread
    public LocationRequest(Context context, LocationRequestListener callback)
    {
        this.context = context;
        if(callback == null)
            throw new IllegalArgumentException("Callback cannot be null!");
        this.callback = callback;
    }

    @Override
    protected String doInBackground(Void... params) {
        //Obtain URL and build request from that
        String url = KeyList.LocationsKeys.URL;
        OkHttpClient client = Storage.getInstance(context).getHttpClient();
        Request request = new Request.Builder().url(url).build();

        //Request data, handling failure cases
        Response response;
        String output = null;
        try {
            response = client.newCall(request).execute();
            if(!response.isSuccessful()) {
                Log.e(LOG_TAG, "Response not successful");
                return null;
            }
            else if(!request.url().equals(response.request().url()))
            {
                Log.e(LOG_TAG, "Request URL is not the same as Response URL");
                return null;
            }

            output = response.body().string();
            response.body().close();
        } catch (Exception e) {
            Log.e(LOG_TAG, "Error getting data!", e);
            return null;
        }

        return output;
    }

    @Override
    protected void onPostExecute(String result) {
        if(result == null)
            callback.onLocationRequestFailure();
        else
            callback.onLocationRequestSuccess(result);
    }

    public interface LocationRequestListener {
        public void onLocationRequestSuccess(String s);
        public void onLocationRequestFailure();
    }
}
