package com.example.kai.locallore;

import android.content.Intent;
import android.database.Cursor;
import android.location.Location;
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
import com.example.kai.locallore.data.Lore;
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

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MapsFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>,
        OnMapReadyCallback,
        GoogleMap.OnMapClickListener,
        OnMapLongClickListener {

    // Identifies a particular Loader being used in this component
    private static final int LORE_LOADER = 0;
    private final String LOG_TAG = MapsFragment.class.getSimpleName();

    private GoogleMap mMap;
    private LatLng mCurrentLatLng;
    private Marker mAddMarker;
    private ArrayList<Lore> mLoreList = new ArrayList<>();
    private ArrayList<MarkerOptions> mLoreMarkers = new ArrayList<>();

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

    @Override
    public void onResume() {
        super.onResume();

        // Remove previously added marker
        if (mAddMarker != null) {
            mAddMarker.remove();
        }
        getLoaderManager().restartLoader(LORE_LOADER, null, this);
    }

    @OnClick(R.id.add_lore_fab)
    public void onFabClick() {
        Intent intent = new Intent(getActivity(), AddLoreActivity.class);
        double[] latLng = {mAddMarker.getPosition().latitude, mAddMarker.getPosition().longitude};

        intent.putExtra("LATLNG", latLng);
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

        mMap.getUiSettings().setMapToolbarEnabled(false);
        mMap.setOnMapClickListener(this);
        mMap.setOnMapLongClickListener(this);
    }

    @Override
    public void onMapClick(LatLng latLng) {
        // Remove most recent marker placed but not added to lore database
        if (mAddMarker != null) {
            mAddMarker.remove();
        }
        addLoreFab.setVisibility(View.GONE);
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
        mLoreList.clear();
        mLoreMarkers.clear();

        // Handle the results
        // http://developer.android.com/training/load-data-background/handle-results.html
        if (data.moveToFirst()){
            do {
                String id = data.getString(data.getColumnIndex(LoreColumns._ID));
                String title = data.getString(data.getColumnIndex(LoreColumns.TITLE));
                String lore = data.getString(data.getColumnIndex(LoreColumns.LORE));
                double latitude = data.getLong(data.getColumnIndex(LoreColumns.LATITUDE));
                double longitude = data.getLong(data.getColumnIndex(LoreColumns.LONGITUDE));
                LatLng latLng = new LatLng(latitude, longitude);

                MarkerOptions marker = new MarkerOptions()
                        .position(latLng)
                        .title(title)
                        .snippet(lore);

                mLoreList.add(new Lore(id, title, lore, latitude, longitude));
                mLoreMarkers.add(marker);

                // Uncomment this line to delete all rows from lore table
                // getActivity().getContentResolver().delete(LoreProvider.Lore.withId(Long.parseLong(id)), null, null);

            } while(data.moveToNext());
        }
        refreshMarkers();
        refreshLocation();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    public void refreshMarkers() {
        if (!mLoreMarkers.isEmpty() && mMap != null) {
            for (MarkerOptions loreMarker : mLoreMarkers) {
                mMap.addMarker(loreMarker);
            }
        }
    }

    public void refreshLocation() {
        Lore firstLore;
        LatLng myLocation;

        if (!mLoreList.isEmpty()) {
            firstLore = mLoreList.get(0);
            myLocation = new LatLng(firstLore.getLatitude(), firstLore.getLongitude());

            if (mMap != null) {
                mMap.moveCamera(CameraUpdateFactory.newLatLng(myLocation));
            }
        }
    }
}
