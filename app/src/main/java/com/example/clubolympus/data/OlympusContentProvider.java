package com.example.clubolympus.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import androidx.annotation.NonNull;

public class OlympusContentProvider extends ContentProvider {

    OlympusDatabaseOpenHelper databaseOpenHelper;

    private static final int MEMBERS = 1;
    private static final int MEMBER_ID = 2;

    // Creates a UriMatcher object.
    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {

        uriMatcher.addURI(ClubOlympusContract.AUTHORITY, ClubOlympusContract.PATH_MEMBERS, MEMBERS);
        uriMatcher.addURI(ClubOlympusContract.AUTHORITY, ClubOlympusContract.PATH_MEMBERS + "/#", MEMBER_ID);

    }


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
    public int delete(Uri uri, String s, String[] strings) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }
}

// URI - Unified Resource Identifier
// content://com.example.clubolympus/members
//URL - Unified Resource Locator
//http://google.com
//content://com.example.clubolympus/members/69
//content://com.example.clubolympus/members
//content://com.android.calendar/events
//content://user_dictionary/words
//content:// -scheme
//com.example.contacts - content authority
//contacts - type of data