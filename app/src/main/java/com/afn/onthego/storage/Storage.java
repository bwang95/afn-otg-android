package com.afn.onthego.storage;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by brian on 4/8/15.
 */
public class Storage {
    Storage instance;

    static final String LEARNING_MODULE_KEY_NAME = "name";
    static final String LEARNING_MODULE_KEY_TYPE = "type";
    static final String LEARNING_MODULE_KEY_DATA = "data";
    static final String LEARNING_MODULE_KEY_POSITION = "position";

    ArrayList<HashMap<String, String>> items;


    private Storage() {
    }



    public Storage getInstance() {
        return instance == null ? instance = new Storage() : instance;
    }
}
