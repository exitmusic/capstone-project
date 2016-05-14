package com.example.kai.locallore;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.kai.locallore.data.LoreColumns;
import com.example.kai.locallore.data.LoreProvider;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddLoreFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddLoreFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddLoreFragment extends Fragment {

    private final String LOG_TAG = AddLoreFragment.class.getSimpleName();

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_LATLNG = "LATLNG";

    private double[] mLatLng;
    private double mLatitude;
    private double mLongitude;

    @Bind(R.id.add_lore_location) TextView addLoreLocation;
    @Bind(R.id.add_lore_title) TextInputLayout addLoreTitle;
    @Bind(R.id.add_lore_story) TextInputLayout addLoreStory;
    @Bind(R.id.add_lore_cancel) Button addLoreCancel;
    @Bind(R.id.add_lore_confirm) Button addLoreConfirm;

    public AddLoreFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param latLng Latitude and Longitude of the lore to add
     * @return A new instance of fragment AddLoreFragment.
     */
    public static AddLoreFragment newInstance (double[] latLng) {
        AddLoreFragment fragment = new AddLoreFragment();
        Bundle args = new Bundle();

        args.putDoubleArray(ARG_LATLNG, latLng);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mLatLng = getArguments().getDoubleArray(ARG_LATLNG);
            mLatitude = mLatLng[0];
            mLongitude = mLatLng[1];
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_add_lore, container, false);
        ButterKnife.bind(this, rootView);

        addLoreLocation.setText(String.valueOf(mLatitude) + ", " + String.valueOf(mLongitude));

        return rootView;
    }

    @OnClick(R.id.add_lore_cancel)
    public void onCancelClick() {
        Log.v(LOG_TAG, "Cancelled");
    }

    @OnClick(R.id.add_lore_confirm)
    public void onConfirmClick() {
        Log.v(LOG_TAG, "Lore Added");

        new Thread(new Runnable() {
            @Override
            public void run() {
                ContentValues cv = new ContentValues();
                cv.put(LoreColumns.TITLE, addLoreTitle.getEditText().toString());
                cv.put(LoreColumns.LORE, addLoreStory.getEditText().toString());
                cv.put(LoreColumns.LATITUDE, mLatitude);
                cv.put(LoreColumns.LONGITUDE, mLongitude);
                getActivity().getContentResolver().insert(LoreProvider.Lore.CONTENT_URI, cv);
            }
        }).start();
    }
}
