package com.example.kai.locallore.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import net.simonvt.schematic.annotation.Database;
import net.simonvt.schematic.annotation.ExecOnCreate;
import net.simonvt.schematic.annotation.OnConfigure;
import net.simonvt.schematic.annotation.OnCreate;
import net.simonvt.schematic.annotation.OnUpgrade;
import net.simonvt.schematic.annotation.Table;

/**
 * Created by kchang on 5/1/16.
 */
@Database(version = LoreDatabase.VERSION)
public class LoreDatabase {

    public static final int VERSION = 1;

    @Table(LoreColumns.class)
    public static final String LORE = "lore";

    @OnCreate
    public static void onCreate(Context context, SQLiteDatabase db) {

    }

    @OnUpgrade
    public static void onUpgrade(Context context, SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @OnConfigure
    public static void onConfigure(SQLiteDatabase db) {

    }

    @ExecOnCreate
    public static final String EXEC_ON_CREATE = "SELECT * FROM " + LORE;

}
