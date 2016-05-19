package com.example.kai.locallore.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;

import com.example.kai.locallore.data.LoreColumns;

/**
 * Created by kchang on 5/3/16.
 */
public class MapsAdapter extends CursorAdapter {

    public static final String[] PROJECTION = new String[] {
            LoreColumns._ID, LoreColumns.LATITUDE, LoreColumns.LONGITUDE, LoreColumns.TITLE, LoreColumns.LORE
    };

    public static final String[] WIDGET_PROJECTION = new String[] {
        LoreColumns.TITLE, LoreColumns.LORE
    };

    public MapsAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return null;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

    }
}
