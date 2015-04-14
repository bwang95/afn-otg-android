package com.afn.onthego.storage;

/**
 * Created by brian on 4/13/15.
 */
public class KeyList {
    public static class Navigation {
        public static final String ABOUT = "about_fragment";
        public static final String DONATE = "donate_fragment";
        public static final String HOME = "home_fragment";
        public static final String LEARN = "learn_fragment";
        public static final String VOLUNTEER = "volunteer_fragment";
        public static final String CONNECT = "connect_activity";
    }

    public static class LearnRequest {
        public static final String URL = "http://austinfreenet.pythonanywhere.com/";
    }

    public static class Storage {
        public static final String PREFS_LEARNING_MODULES = "learning_modules";
        public static final String LEARNING_MODULE_NAME = "learning_module_name";
        public static final String LEARNING_MODULE_TYPE = "learning_module_type";
        public static final String LEARNING_MODULE_DATA = "learning_module_data";
        public static final String LEARNING_MODULE_POSITION = "learning_module_name";
    }
}
