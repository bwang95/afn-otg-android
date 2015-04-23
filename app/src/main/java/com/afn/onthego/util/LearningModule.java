package com.afn.onthego.util;

import android.util.Log;

import java.io.File;

/**
 * Created by Kyle on 4/14/2015.
 */
public class LearningModule implements Comparable<LearningModule> {

    public String name;
    public String type;
    public String data;
    public String position;
    public String fileName;

    public static final String LOG_TAG = "LearningModule";

    public LearningModule() {
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /*
    If File exists return filename, else empty string
     */
    public String getFileName() {
        if (fileName == null) {
            return "";
        }

        if (new File(fileName).exists()) {
            return fileName;
        } else {
            return "";
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getData() {
        return data;
    }

    public String getPosition() {
        return position;
    }

    public int compareTo(LearningModule another) {
        try {
            return new Integer(Integer.parseInt(position))
                    .compareTo(Integer.parseInt(another.position));
        } catch (Exception e) {
            Log.e(LOG_TAG, "Integer exception caught!", e);
            return 0;
        }
    }
}
