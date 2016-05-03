package com.example.kai.locallore.data;

import net.simonvt.schematic.annotation.AutoIncrement;
import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.NotNull;
import net.simonvt.schematic.annotation.PrimaryKey;

import static net.simonvt.schematic.annotation.DataType.Type.INTEGER;
import static net.simonvt.schematic.annotation.DataType.Type.TEXT;
import static net.simonvt.schematic.annotation.DataType.Type.REAL;

/**
 * Created by kchang on 5/1/16.
 */
public interface LoreColumns {

    @DataType(INTEGER) @PrimaryKey @AutoIncrement String _ID = "_id";

    @DataType(TEXT) @NotNull String TITLE = "title";

    @DataType(TEXT) @NotNull String LORE = "lore";

    @DataType(REAL) @NotNull String LATITUDE = "latitude";

    @DataType(REAL) @NotNull String LONGITUDE = "longitude";

}
