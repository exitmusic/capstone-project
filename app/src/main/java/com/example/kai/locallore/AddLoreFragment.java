package com.example.kai.locallore;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddLoreFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddLoreFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddLoreFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_LATLNG = "LATLNG";

    private double[] mLatLng;
    private double mLatitude;
    private double mLongitude;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_lore, container, false);
    }
    
}
