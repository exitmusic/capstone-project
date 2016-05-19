package com.example.kai.locallore.service;

import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.os.IBinder;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

import com.example.kai.locallore.adapter.MapsAdapter;
import com.example.kai.locallore.data.LoreProvider;

public class LoreService extends Service implements Loader.OnLoadCompleteListener<Cursor> {

    private static final int LORE_LOADER = 1;

    CursorLoader mCursorLoader;

    public LoreService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mCursorLoader = new CursorLoader(getApplicationContext(), LoreProvider.Lore.CONTENT_URI,
                MapsAdapter.WIDGET_PROJECTION, null, null, null);
        mCursorLoader.registerListener(LORE_LOADER, this);
        mCursorLoader.startLoading();
    }

    @Override
    public void onLoadComplete(Loader<Cursor> loader, Cursor data) {
        // Bind data to UI, etc
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        // Stop the cursor loader
        if (mCursorLoader != null) {
            mCursorLoader.unregisterListener(this);
            mCursorLoader.cancelLoad();
            mCursorLoader.stopLoading();
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
