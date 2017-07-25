package com.example.databasesample;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.example.databasesample.data.GroceryDbHelper;


public class MyApp extends Application {

    private SQLiteDatabase db;

    @Override
    public void onCreate() {
        super.onCreate();
        GroceryDbHelper dbHelper = new GroceryDbHelper(this);
        db = dbHelper.getWritableDatabase();
    }

    public SQLiteDatabase getDb() {
        return db;
    }
}
