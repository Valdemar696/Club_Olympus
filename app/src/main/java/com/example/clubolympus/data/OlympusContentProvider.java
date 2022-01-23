package com.example.clubolympus.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import androidx.annotation.NonNull;

public class OlympusContentProvider extends ContentProvider {

    OlympusDatabaseOpenHelper databaseOpenHelper;

    @Override
    public boolean onCreate() {
        databaseOpenHelper = new OlympusDatabaseOpenHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] strings, String s, String[] strings1, String s1) { //read method
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) { //create method
        return null;
    }

    @Override
    public int delete( Uri uri, String s, String[] strings) {
        return 0;
    }

    @Override
    public int update( Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }

    @Override
    public String getType( Uri uri) {
        return null;
    }
}
