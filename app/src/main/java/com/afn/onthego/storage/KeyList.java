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

    public static enum Locations {
        AFN_ROSEWOOD(0), ARCH(1), BLACKLAND(2), CG_SENIOR_CTR(3),
        RZ_NHOOD_CTR(4), SAUS_NHOOD_CTR(5), ST_JOHNS_CTR(6), TRINITY_CTR(7);

        public static final LatLng[] LAT_LNGS = {
                new LatLng(30.269932, -97.715876),
                new LatLng(30.26787, -97.737584),
                new LatLng(30.280945, -97.722188),
                new LatLng(30.265868, -97.710984),
                new LatLng(30.265391, -97.710594),
                new LatLng(30.239574, -97.760286),
                new LatLng(30.3328231, -97.6937014),
                new LatLng(30.2685762, -97.7397648)
        };

        public static final String[] NAMES = {
                "DeWitty Center",
                "Austin Resource Center for the Homeless",
                "Blackland Neighborhood Center",
                "Conley-Guerrero Senior Activity Center",
                "Rosewood Zaragosa Neighborhood Center",
                "South Austin Neighborhood Center",
                "St. John’s Neighborhood Center",
                "Trinity Center"
        };

        public int index;

        Locations(int i) {
            index = i;
        }
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

    public static class ActivityParams {
        public static final String KEY_URL = "webactivityurl";
        public static final String KEY_FILENAME = "pdfactivityfilename";
        public static final String KEY_TITLE = "activitytitle";
        public static final String KEY_PURGE_COOKIES = "purgecookies";
    }

    public static final class URL {
        public static final String QUESTION_URL = "http://form.jotform.us/form/50925791023151";
        public static final String VOLUNTEER_SIGN_UP = "https://ctk.apricot.info/auth/autologin/org_id/1643/hash/ec0b5d74087f76195cdeb3a30ab1a6195a17f541";
        public static final String VOLUNTEER_SIGN_IN = "https://ctk.apricot.info";
        public static final String DONATE_URL = "https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=HPV6ZMGRW5VWU";
        public static final String AFN = "http://austinfree.net";
    }
}
