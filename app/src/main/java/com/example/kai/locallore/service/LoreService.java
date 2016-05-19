package com.example.kai.locallore.service;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.database.Cursor;
import android.os.IBinder;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.widget.RemoteViews;

import com.example.kai.locallore.R;
import com.example.kai.locallore.adapter.MapsAdapter;
import com.example.kai.locallore.data.LoreColumns;
import com.example.kai.locallore.data.LoreProvider;

public class LoreService extends Service implements Loader.OnLoadCompleteListener<Cursor> {

    private static final int LORE_LOADER = 1;

    CursorLoader mCursorLoader;
    int[] mAppWidgetIds;

    public LoreService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mAppWidgetIds = intent.getIntArrayExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS);

        return super.onStartCommand(intent, flags, startId);
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
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(getApplicationContext());

        // Bind data to UI, etc
        for (int widgetId : mAppWidgetIds) {
            // Construct the RemoteViews object
            RemoteViews remoteViews = new RemoteViews(getApplicationContext().getPackageName(), R.layout.add_lore_widget);

            if (data.moveToLast()) {
                String title = data.getString(data.getColumnIndex(LoreColumns.TITLE));
                String lore = data.getString(data.getColumnIndex(LoreColumns.LORE));

                remoteViews.setTextViewText(R.id.appwidget_title, title);
                remoteViews.setTextViewText(R.id.appwidget_lore, lore);
            }
            appWidgetManager.updateAppWidget(widgetId, remoteViews);
        }
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
