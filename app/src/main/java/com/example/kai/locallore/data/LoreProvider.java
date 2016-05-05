package com.example.kai.locallore.data;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;

import net.simonvt.schematic.annotation.ContentProvider;
import net.simonvt.schematic.annotation.ContentUri;
import net.simonvt.schematic.annotation.InexactContentUri;
import net.simonvt.schematic.annotation.NotifyInsert;
import net.simonvt.schematic.annotation.TableEndpoint;

/**
 * Created by kchang on 5/1/16.
 */
@ContentProvider(authority = LoreProvider.AUTHORITY, database = LoreDatabase.class)
public class LoreProvider {

    public static final String AUTHORITY = "com.example.kai.locallore.LoreProvider";
    static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    interface Path {
        String LORE = "lore";
    }

    private static Uri buildUri(String... paths) {
        Uri.Builder builder = BASE_CONTENT_URI.buildUpon();

        for (String path : paths) {
            builder.appendPath(path);
        }
        return builder.build();
    }

    @TableEndpoint(table = LoreDatabase.LORE)
    public static class Lore {
        @ContentUri(
                path = Path.LORE,
                type = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + Path.LORE
        )
        public static final Uri CONTENT_URI = buildUri(Path.LORE);

        @InexactContentUri(
                name = "LORE_ID",
                path = Path.LORE + "/#",
                type = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + Path.LORE,
                whereColumn = LoreColumns._ID,
                pathSegment = 1
        )
        public static Uri withId(long id) {
            return buildUri(Path.LORE, String.valueOf(id));
        }

//        @NotifyInsert(paths = Path.LORE)
//        public static Uri[] onInsert(ContentValues values) {
//
//        }
    }
}
