package com.afn.onthego.storage;

/**
 * Created by brian on 4/8/15.
 */
public class Storage {
    Storage instance;

    private Storage() {
    }

    public Storage getInstance() {
        return instance == null ? instance = new Storage() : instance;
    }
}
