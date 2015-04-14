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

import com.afn.onthego.R;
import com.afn.onthego.storage.KeyList;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class ConnectActivity extends ActionBarActivity implements OnMapReadyCallback {

    SupportMapFragment mapFragment;
    GoogleMap map;
    LocationManager locManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mf_connect_map);
        mapFragment.getMapAsync(this);

        locManager = (LocationManager) getSystemService(LOCATION_SERVICE);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_connect, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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

        int i = KeyList.Locations.AFN_ROSEWOOD.index;

        LatLng latLngLocation = KeyList.Locations.LAT_LNGS[i];

        if (location != null) {
            latLngLocation = new LatLng(
                    location.getLatitude(),
                    location.getLongitude());
        }
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngLocation, 13));
    }

    private void placeMarkers() {
        MarkerOptions options = new MarkerOptions();

        options.snippet("Tap for directions");

        for(int k = 0; k < KeyList.Locations.LAT_LNGS.length; k++) {
            options.title(KeyList.Locations.NAMES[k]);
            options.position(KeyList.Locations.LAT_LNGS[k]);
            map.addMarker(options);
        }

        map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Uri gmmIntentUri = Uri.parse("http://maps.google.com/maps?daddr=" +
                        marker.getPosition().latitude + "," +
                        marker.getPosition().longitude);
                Intent navIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                startActivity(navIntent);
            }
        });
    }
}
