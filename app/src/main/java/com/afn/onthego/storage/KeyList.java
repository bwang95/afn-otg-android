package com.afn.onthego.storage;

import com.google.android.gms.maps.model.LatLng;

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

    public static class LearningModulesKeys {
        public static final String URL = "http://austinfreenet.pythonanywhere.com/";
        public static final String PREFS_LEARNING_MODULES = "learning_modules";
        public static final String LEARNING_MODULE_NAME = "name";
        public static final String LEARNING_MODULE_TYPE = "type";
        public static final String LEARNING_MODULE_DATA = "data";
        public static final String LEARNING_MODULE_POSITION = "position";
        public static final String TYPE_YOUTUBE = "YouTube";
        public static final String TYPE_WEBSITE = "Website";
        public static final String TYPE_PDF = "PDF";
    }


    public static class LinkKeys {
        public static final String URL = "http://austinfreenet.pythonanywhere.com/mobile_data/links/";
        public static final String PREFS_LINKS = "links";
        public static final String LINK_NAME = "name";
        public static final String LINK_URL = "url";

    }

    public static class LocationsKeys {
        public static final String URL = "http://austinfreenet.pythonanywhere.com/mobile_data/locations/";
        public static final String PREFS_LOCATIONS = "locations";
        public static final String LOCATION_NAME = "name";
        public static final String LOCATION_LONGITUDE = "longitude";
        public static final String LOCATION_LATITUDE = "latitude";
        public static final String LOCATION_POSITION = "position";
    }

    public static class ActivityParams {
        public static final String KEY_URL = "webactivityurl";
        public static final String KEY_FILENAME = "pdfactivityfilename";
        public static final String KEY_TITLE = "activitytitle";
        public static final String KEY_PURGE_COOKIES = "purgecookies";
    }

    public static class Analytics {
        public static final String CATEGORY_VOLUNTEER = "Volunteering";
        public static final String CATEGORY_LEARN = "Learning Modules";
        public static final String CATEGORY_DONATE = "Donations";
        public static final String CATEGORY_CONNECT = "Connect";
        public static final String KEY_OPT_OUT = "optout";

        public static final String ACTION_VOLUNTEER_SIGNIN = "Volunteer Sign In Button Click";
        public static final String LABEL_VOLUNTEER_SIGNIN = "Volunteer Sign In Button Click";
        public static final String ACTION_VOLUNTEER_SIGNUP = "Volunteer Sign Up Button Click";
        public static final String LABEL_VOLUNTEER_SIGNUP = "Volunteer Sign Up Button Click";
        public static final String ACTION_LEARNING_MODULE = "Learning Module View";
    }
}
