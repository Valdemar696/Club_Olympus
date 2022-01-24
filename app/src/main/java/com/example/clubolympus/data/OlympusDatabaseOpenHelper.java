package com.example.clubolympus.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.clubolympus.data.ClubOlympusContract.MemberEntry;


public class OlympusDatabaseOpenHelper extends SQLiteOpenHelper {
    public OlympusDatabaseOpenHelper(Context context) {
        super(context, ClubOlympusContract.DATABASE_NAME, null, ClubOlympusContract.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_MEMBERS_TABLE = "CREATE TABLE " + MemberEntry.TABLE_NAME + " (" +
                MemberEntry.KEY_ID + " INTEGER PRIMARY KEY, " +
                MemberEntry.KEY_FIRST_NAME + " TEXT, " +
                MemberEntry.KEY_LAST_NAME + " TEXT, " +
                MemberEntry.KEY_GENDER + " INTEGER NOT NULL, " +
                MemberEntry.KEY_SPORT_TYPE + " TEXT " + ")";
        sqLiteDatabase.execSQL(CREATE_MEMBERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MemberEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}