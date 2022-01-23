package com.example.clubolympus.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.widget.Toast;

import com.example.clubolympus.data.ClubOlympusContract.*;

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
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) { //read method
        SQLiteDatabase sqLiteDatabase = databaseOpenHelper.getReadableDatabase();
        Cursor cursor;

        int match = uriMatcher.match(uri);

        switch (match) {
            case MEMBERS:
                cursor = sqLiteDatabase.query(MemberEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;

            case MEMBER_ID:
                    selection= MemberEntry.KEY_ID + "=?";
                    selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
                    cursor = sqLiteDatabase.query (MemberEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;

            default:
                Toast.makeText(getContext(), "Incorrect URI", Toast.LENGTH_LONG).show();
                throw new IllegalArgumentException("Can't query incorrect URI" + uri);

        }
        return cursor;
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