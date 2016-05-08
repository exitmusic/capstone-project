package com.example.kai.locallore;

import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.kai.locallore.data.LoreColumns;
import com.example.kai.locallore.data.LoreProvider;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.SupportMapFragment;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private final String LOG_TAG = MapsFragment.class.getSimpleName();

    protected PackageManager mPackageManager;
    private GoogleApiClient mGoogleApiClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPackageManager = getApplicationContext().getPackageManager();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Insert example lore pins in database
        ContentValues cv = new ContentValues();
        cv.put(LoreColumns.TITLE, "Al Capone's Hangout");
        cv.put(LoreColumns.LORE, "Al Capone and his friends were often found loitering on this corner");
        cv.put(LoreColumns.LATITUDE, 52);
        cv.put(LoreColumns.LONGITUDE, 88);
        getApplicationContext().getContentResolver().insert(LoreProvider.Lore.CONTENT_URI, cv);

        // Initialize CursorLoader
        // http://developer.android.com/training/load-data-background/setup-loader.html
        //getLoaderManager().initLoader(LORE_LOADER, null, this);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
