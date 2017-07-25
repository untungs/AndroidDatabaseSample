package com.example.databasesample.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.databasesample.model.Grocery;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;


public class GroceryDbHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "grocery.db";
    private static final int DB_VERSION = 1;

    static {
        cupboard().register(Grocery.class);
    }

    public GroceryDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        cupboard().withDatabase(sqLiteDatabase).createTables();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        cupboard().withDatabase(sqLiteDatabase).upgradeTables();
    }
}
