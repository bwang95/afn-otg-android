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
}
