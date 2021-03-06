package com.afn.onthego.activities;

import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.afn.onthego.R;
import com.afn.onthego.adapters.ConnectAdapter;
import com.afn.onthego.app.OTGApplication;
import com.afn.onthego.storage.KeyList;
import com.afn.onthego.storage.Storage;
import com.afn.onthego.util.LinksContainer;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class ConnectActivity extends ActionBarActivity implements OnMapReadyCallback {

    private SupportMapFragment mapFragment;
    private GoogleMap map;
    private Marker[] markers;
    private LocationManager locManager;

    private Storage storage;
    private ListView locationList;
    private ArrayList<com.afn.onthego.util.Location> locations;
    private ConnectAdapter locationAdapter;

    private FrameLayout questionLayout;

    private ListView.OnItemClickListener locationListener = new ListView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (map == null || markers == null)
                return;
            if (!(position < markers.length && position >= 0))
                return;

            Marker m = markers[position];
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(m.getPosition(), 15));
            m.showInfoWindow();
        }
    };

    private View.OnClickListener questionListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent webIntent = new Intent(ConnectActivity.this, WebActivity.class);

            webIntent.putExtra(KeyList.ActivityParams.KEY_URL, Storage.getInstance(getBaseContext()).getLinks().getURLS().get(LinksContainer.QUESTION));
            webIntent.putExtra(KeyList.ActivityParams.KEY_TITLE, "Ask a Question");
            startActivity(webIntent);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mf_connect_map);
        mapFragment.getMapAsync(this);

        locationList = (ListView) findViewById(R.id.lv_connect_locations);
        questionLayout = (FrameLayout) findViewById(R.id.fl_connect_questions);
        questionLayout.setOnClickListener(questionListener);


        storage = Storage.getInstance(this);
        locations = storage.getLocationsContainer().getLocationsArrays();

        locationAdapter = new ConnectAdapter(this, locations);
        locationList.setAdapter(locationAdapter);
        locationList.setOnItemClickListener(locationListener);

        locManager = (LocationManager) getSystemService(LOCATION_SERVICE);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_connect, menu);
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        setInitialMapState();
        placeMarkers();

    }

    private void setInitialMapState() {
        map.setMyLocationEnabled(true);


        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setCostAllowed(false);
        criteria.setPowerRequirement(Criteria.NO_REQUIREMENT);

        String provider = locManager.getBestProvider(criteria, true);

        Location location = locManager.getLastKnownLocation(provider);
        int i = 0;

        for (int k = 0; k < locations.size(); k++) {
            if (locations.get(i).getName().equals("Rosewood Zaragosa Neighborhood Center")) {
                i = k;
                break;
            }
        }
        LatLng latLngLocation = locations.get(i).getLatLng();

        if (location != null) {
            latLngLocation = new LatLng(
                    location.getLatitude(),
                    location.getLongitude());
        }
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngLocation, 13));
    }

    private void placeMarkers() {
        MarkerOptions options = new MarkerOptions();

        options.snippet("Tap here for directions");

        markers = new Marker[locations.size()];

        for (int k = 0; k < locations.size(); k++) {
            options.title(locations.get(k).getName());
            options.position(locations.get(k).getLatLng());

            markers[k] = map.addMarker(options);
        }

        map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Tracker t = ((OTGApplication) getApplication()).getTracker();

                t.send(new HitBuilders.EventBuilder()
                        .setCategory(KeyList.Analytics.CATEGORY_CONNECT)
                        .setAction(KeyList.Analytics.ACTION_CONNECT_MAP)
                        .setLabel(marker.getTitle()).build());

                Uri gmmIntentUri = Uri.parse("http://maps.google.com/maps?daddr=" +
                        marker.getPosition().latitude + "," +
                        marker.getPosition().longitude + "&mode=transit");
                Intent navIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                startActivity(navIntent);
            }
        });
    }

    public void openAfn(MenuItem item) {
        Intent webIntent = new Intent(Intent.ACTION_VIEW);
        webIntent.setData(Uri.parse(Storage.getInstance(getBaseContext()).getLinks().getURLS().get(LinksContainer.AFN)));
        startActivity(webIntent);
    }
}
