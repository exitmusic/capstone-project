package com.example.kai.locallore;

import android.Manifest;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import com.example.kai.locallore.data.LoreColumns;
import com.example.kai.locallore.data.LoreProvider;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private final String LOG_TAG = MainActivity.class.getSimpleName();
    private final int MY_PERMISSIONS_REQUEST_LOCATION = 3;

    protected PackageManager mPackageManager;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    protected Location mLastLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        mPackageManager = getApplicationContext().getPackageManager();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        if (savedInstanceState == null) {
            MapsFragment mapsFragment = new MapsFragment();

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.maps_container, mapsFragment)
                    .commit();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        mLocationRequest.setInterval(100000);

        ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION},
                MY_PERMISSIONS_REQUEST_LOCATION);
        readLocation();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Taken from http://developer.android.com/training/permissions/requesting.html
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == mPackageManager.PERMISSION_GRANTED) {
                    readLocation();
                }
            }
        }
    }

    public void readLocation() {
        // Taken from http://developer.android.com/training/permissions/requesting.html
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            Log.v(LOG_TAG, "permissions granted");
            Log.v(LOG_TAG, mLastLocation.toString());

            // Add marker for current location
            ContentValues cv = new ContentValues();
            cv.put(LoreColumns.TITLE, "");
            cv.put(LoreColumns.LORE, "");
            cv.put(LoreColumns.LATITUDE, mLastLocation.getLatitude());
            cv.put(LoreColumns.LONGITUDE, mLastLocation.getLongitude());
            getContentResolver().insert(LoreProvider.Lore.CONTENT_URI, cv);
        }

    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.v(LOG_TAG, "connection susupended");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.v(LOG_TAG, "connection failed");
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.v(LOG_TAG, location.toString());

        mLastLocation = location;
        showHome();
    }

    public void showHome() {
        if (mLastLocation != null) {
            LatLng home = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
            //mMap.moveCamera((CameraUpdateFactory.newLatLng(home)));
        }
    }
}
