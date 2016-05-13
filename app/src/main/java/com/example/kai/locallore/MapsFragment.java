package com.example.kai.locallore;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kai.locallore.adapter.MapsAdapter;
import com.example.kai.locallore.data.LoreColumns;
import com.example.kai.locallore.data.LoreProvider;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MapsFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>,
        OnMapReadyCallback,
        OnMapLongClickListener {

    // Identifies a particular Loader being used in this component
    private static final int LORE_LOADER = 0;
    private final String LOG_TAG = MapsFragment.class.getSimpleName();

    private GoogleMap mMap;
    private Marker mAddMarker;

    @Bind(R.id.add_lore_fab) FloatingActionButton addLoreFab;

    public MapsFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_maps, container, false);
        ButterKnife.bind(this, rootView);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Initialize CursorLoader
        // http://developer.android.com/training/load-data-background/setup-loader.html
        getLoaderManager().initLoader(LORE_LOADER, null, this);

        return rootView;
    }

    @OnClick(R.id.add_lore_fab)
    public void onFabClick() {
        Intent intent = new Intent(getActivity(), AddLoreActivity.class);
        startActivity(intent);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.setOnMapLongClickListener(this);
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        // Remove most recent marker placed but not added to lore database
        if (mAddMarker != null) {
            mAddMarker.remove();
        }
        // Store the marker that was just added by the user at the location clicked
        mAddMarker = mMap.addMarker(new MarkerOptions().position(latLng).title("Add Lore?"));
        addLoreFab.setVisibility(View.VISIBLE);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getActivity(), LoreProvider.Lore.CONTENT_URI, MapsAdapter.PROJECTION, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        // Handle the results
        // http://developer.android.com/training/load-data-background/handle-results.html
        data.moveToFirst();

        String title = data.getString(data.getColumnIndex(LoreColumns.TITLE));
        LatLng latLng = new LatLng(
                data.getLong(data.getColumnIndex(LoreColumns.LATITUDE)),
                data.getLong(data.getColumnIndex(LoreColumns.LONGITUDE)));
        MarkerOptions marker = new MarkerOptions();
        marker.position(latLng).title(title);

        Log.v(LOG_TAG, Integer.toString(data.getCount()));
        Log.v(LOG_TAG, title);
        Log.v(LOG_TAG, marker.getPosition().toString());
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
