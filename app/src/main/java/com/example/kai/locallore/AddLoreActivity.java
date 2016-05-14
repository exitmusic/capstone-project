package com.example.kai.locallore;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class AddLoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_lore);

        Toolbar toolbar = (Toolbar) findViewById(R.id.add_lore_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        double[] latLng = intent.getDoubleArrayExtra("LATLNG");

        if (savedInstanceState == null) {
            AddLoreFragment addLoreFragment = new AddLoreFragment();
            addLoreFragment.newInstance(latLng);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.add_lore_container, addLoreFragment)
                    .commit();
        }
    }

}
