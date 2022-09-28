package com.dhimas.cashbook.database;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class Helper extends SQLiteAssetHelper {
    public static final String DB_Name = "mycashbook.db";
    public static final int DB_Version = 3;
    public Helper(Context context) {
        super(context, DB_Name, null, DB_Version);
    }
}
