package com.example.clubolympus.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.example.clubolympus.data.ClubOlympusContract.*;

public class OlympusContentProvider extends ContentProvider {

    OlympusDatabaseOpenHelper databaseOpenHelper;

    private static final int MEMBERS = 1;
    private static final int MEMBER_ID = 2;

    // Creates a UriMatcher object.
    private static final UriMatcher uriMatcher =
            new UriMatcher(UriMatcher.NO_MATCH);

    static { //Статический блок. Используется для добавления ЮРИ в переменную ЮриМэтчер

        uriMatcher.addURI(ClubOlympusContract.AUTHORITY,
                ClubOlympusContract.PATH_MEMBERS, MEMBERS);
        uriMatcher.addURI(ClubOlympusContract.AUTHORITY,
                ClubOlympusContract.PATH_MEMBERS +
                        "/#", MEMBER_ID);

    }

    @Override
    public boolean onCreate() {
        databaseOpenHelper = new OlympusDatabaseOpenHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) { //read method
        SQLiteDatabase sqLiteDatabase = databaseOpenHelper.getReadableDatabase();
        Cursor cursor;

        int match = uriMatcher.match(uri);

        switch (match) {
            case MEMBERS:
                cursor = sqLiteDatabase.query(MemberEntry.TABLE_NAME, projection,
                        selection, selectionArgs, null, null, sortOrder);
                break;

            case MEMBER_ID:
                    selection= MemberEntry.KEY_ID + "=?";
                    selectionArgs = new String[]
                            {String.valueOf(ContentUris.parseId(uri))};
                    cursor = sqLiteDatabase.query(MemberEntry.TABLE_NAME,
                            projection, selection, selectionArgs,
                            null, null, sortOrder);
                break;

            default:
                Toast.makeText(getContext(), "Incorrect URI", Toast.LENGTH_LONG).show();
                throw new IllegalArgumentException("Can't query incorrect URI" + uri);

        }
        return cursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) { //create method

        String firstName = values.getAsString(MemberEntry.KEY_FIRST_NAME);
        if (firstName == null) {
            throw new IllegalArgumentException("You have to input correct first name" + uri);
        }

        String lastName = values.getAsString(MemberEntry.KEY_LAST_NAME);
        if (lastName == null) {
            throw new IllegalArgumentException("You have to input correct last name" + uri);
        }

        Integer gender = values.getAsInteger(MemberEntry.KEY_GENDER);
        if (gender == null || !(gender == MemberEntry.GENDER_UNKNOWN ||
                gender == MemberEntry.GENDER_FEMALE ||
                gender == MemberEntry.GENDER_MALE)) {
            throw new IllegalArgumentException("You have to input correct gender" + uri);
        }

        String sportType = values.getAsString(MemberEntry.KEY_SPORT_TYPE);
        if (sportType == null) {
            throw new IllegalArgumentException("You have to input correct sport type" + uri);
        }

        SQLiteDatabase sqLiteDatabase = databaseOpenHelper.getWritableDatabase();

        int match = uriMatcher.match(uri);

        switch (match) {
            case MEMBERS:
                long id = sqLiteDatabase.insert(MemberEntry.TABLE_NAME, null, values);
                if (id == -1) {
                    Log.e("insertMethod", "Insertion of data in the table failed for " + uri);
                    return null;
                }

                return ContentUris.withAppendedId(uri, id);

            default:
                throw new IllegalArgumentException("Insertion of data in the table failed for " + uri);

        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        SQLiteDatabase sqLiteDatabase = databaseOpenHelper.getWritableDatabase();

        int match = uriMatcher.match(uri);

        switch (match) {
            case MEMBERS:
                return sqLiteDatabase.delete(MemberEntry.TABLE_NAME, selection, selectionArgs);

            case MEMBER_ID:
                selection= MemberEntry.KEY_ID + "=?";
                selectionArgs = new String[]
                        {String.valueOf(ContentUris.parseId(uri))};
                return sqLiteDatabase.delete(MemberEntry.TABLE_NAME, selection, selectionArgs);

            default:
                Toast.makeText(getContext(), "Incorrect URI", Toast.LENGTH_LONG).show();
                throw new IllegalArgumentException("Can't delete this URI" + uri);

        }
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        if (values.containsKey(MemberEntry.KEY_FIRST_NAME)) {
            String firstName = values.getAsString(MemberEntry.KEY_FIRST_NAME);
            if (firstName == null) {
                throw new IllegalArgumentException("You have to input correct first name" + uri);
            }
        }

        if (values.containsKey(MemberEntry.KEY_LAST_NAME)) {
            String lastName = values.getAsString(MemberEntry.KEY_LAST_NAME);
            if (lastName == null) {
                throw new IllegalArgumentException("You have to input correct last name" + uri);
            }
        }

        if (values.containsKey(MemberEntry.KEY_GENDER)) {
            Integer gender = values.getAsInteger(MemberEntry.KEY_GENDER);
            if (gender == null || !(gender == MemberEntry.GENDER_UNKNOWN ||
                    gender == MemberEntry.GENDER_FEMALE ||
                    gender == MemberEntry.GENDER_MALE)) {
                throw new IllegalArgumentException("You have to input correct gender" + uri);
            }
        }

        if (values.containsKey(MemberEntry.KEY_SPORT_TYPE)) {
            String sportType = values.getAsString(MemberEntry.KEY_SPORT_TYPE);
            if (sportType == null) {
                throw new IllegalArgumentException("You have to input correct sport type" + uri);
            }
        }

        SQLiteDatabase sqLiteDatabase = databaseOpenHelper.getWritableDatabase();

        int match = uriMatcher.match(uri);

        switch (match) {
            case MEMBERS:
                return sqLiteDatabase.update(MemberEntry.TABLE_NAME, values, selection, selectionArgs);

            case MEMBER_ID:
                selection= MemberEntry.KEY_ID + "=?";
                selectionArgs = new String[]
                        {String.valueOf(ContentUris.parseId(uri))};
                return sqLiteDatabase.update(MemberEntry.TABLE_NAME, values, selection, selectionArgs);

            default:
                Toast.makeText(getContext(), "Incorrect URI", Toast.LENGTH_LONG).show();
                throw new IllegalArgumentException("Can't update this URI" + uri);

        }
    }

    @Override
    public String getType(Uri uri) {
        int match = uriMatcher.match(uri);

        switch (match) {
            case MEMBERS:
                return MemberEntry.CONTENT_MULTIPLE_ITEMS;

            case MEMBER_ID:
                return MemberEntry.CONTENT_SINGLE_ITEM;

            default:
                Toast.makeText(getContext(), "Incorrect URI", Toast.LENGTH_LONG).show();
                throw new IllegalArgumentException("Unknown URI:" + uri);
        }
    }
}

// URI - Unified Resource Identifier
// content://com.example.clubolympus/members
//URL - Unified Resource Locator
//http://google.com
//content://com.example.clubolympus/members/69  1
//content://com.example.clubolympus/members     2
//content://com.android.calendar/events
//content://user_dictionary/words
//content:// -scheme
//com.example.contacts - content authority
//contacts - type of data