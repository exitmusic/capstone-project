package com.example.kai.locallore;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

public class AddLoreActivity extends AppCompatActivity {

    private final String LOG_TAG = AddLoreActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_lore);

        Intent intent = getIntent();
        double[] latLng = intent.getDoubleArrayExtra("LATLNG");

        if (savedInstanceState == null) {
            AddLoreFragment addLoreFragment = new AddLoreFragment().newInstance(latLng);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.add_lore_container, addLoreFragment)
                    .commit();
        }
    }

}
