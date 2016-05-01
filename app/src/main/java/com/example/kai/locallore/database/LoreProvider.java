package com.example.kai.locallore.database;

import android.net.Uri;

import net.simonvt.schematic.annotation.ContentProvider;
import net.simonvt.schematic.annotation.ContentUri;
import net.simonvt.schematic.annotation.TableEndpoint;

/**
 * Created by kchang on 5/1/16.
 */
@ContentProvider(authority = LoreProvider.AUTHORITY, database = LoreDatabase.class)
public class LoreProvider {

    public static final String AUTHORITY = "com.example.kai.locallore.LoreProvider";

    @TableEndpoint(table = LoreDatabase.LORE)
    public static class Lore {
        @ContentUri(
                path = "lore",
                type = "locallore/lore",
                defaultSort = LoreColumns.TITLE + " ASC")
        public static final Uri LORE = Uri.parse("content://" + AUTHORITY + "lore");
    }
}
