package com.afn.onthego.async;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.afn.onthego.storage.KeyList;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

/**
 * Created by brian on 4/15/15.
 */
public class PDFRequest extends AsyncTask<Void, Void, String> {

    private static final String LOG_TAG = "PDFRequest";
    private Context context;
    private PDFRequestListener callback;
    private String url;

    public PDFRequest(String url, Context context, PDFRequestListener callback) {
        this.callback = callback;
        this.context = context;
        this.url = url;
    }

    @Override
    protected String doInBackground(Void... params) {
        //Obtain URL and build request from that
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();

        //Request data, handling failure cases
        Response response;
        String output = null;
        try {
            response = client.newCall(request).execute();
            if(!response.isSuccessful())
                return null;


            output = "temp.pdf";//url.substring(url.lastIndexOf('/'));
            File file = File.createTempFile(output, ".pdf", context.getCacheDir());
            output = file.getAbsolutePath();

            //Write a pdf from a stream.
            FileOutputStream out = new FileOutputStream(file);
            InputStream stream = response.body().byteStream();

            byte[] buffer = new byte[128];
            int result;
            while((result = stream.read(buffer)) > 0) {
                out.write(buffer, 0, result);
                out.flush();
            }
            out.close();
            stream.close();

            Log.e(LOG_TAG, output);
        } catch (Exception e) {
            Log.e(LOG_TAG, "Error getting data!", e);
            return null;
        }

        return output;
    }

    @Override
    protected void onPostExecute(String result) {
        if(result == null)
            callback.onPDFRequestFailure();
        else
            callback.onPDFRequestSuccess(result);
    }

    public interface PDFRequestListener {
        public void onPDFRequestSuccess(String filename);
        public void onPDFRequestFailure();
    }
}
