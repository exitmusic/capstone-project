package com.example.kai.locallore.database;

import net.simonvt.schematic.annotation.Database;
import net.simonvt.schematic.annotation.Table;

/**
 * Created by kchang on 5/1/16.
 */
@Database(version = LoreDatabase.VERSION)
public class LoreDatabase {

    public static final int VERSION = 1;

    @Table(LoreDatabase.class) public static final String LORE = "lore";
}
