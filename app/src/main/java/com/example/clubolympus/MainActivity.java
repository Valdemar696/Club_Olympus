package com.example.clubolympus;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.clubolympus.data.ClubOlympusContract.MemberEntry;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks <Cursor> {

    private static final int MEMBER_LOADER = 123;
    MemberCursorAdapter memberCursorAdapter;

    ListView dataListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataListView = findViewById(R.id.dataListView);


        FloatingActionButton floatingActionButton = findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddMemberActivity.class);
                startActivity(intent);
            }
        });

        memberCursorAdapter = new MemberCursorAdapter(this, null, false);
        dataListView.setAdapter(memberCursorAdapter);
        getSupportLoaderManager().initLoader(MEMBER_LOADER, null, this);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {

        String[] projection = {
                MemberEntry.KEY_ID,
                MemberEntry.KEY_FIRST_NAME,
                MemberEntry.KEY_LAST_NAME,
                MemberEntry.KEY_SPORT_TYPE
        };

        CursorLoader cursorLoader = new CursorLoader(this,
                MemberEntry.CONTENT_URI,
                projection,
                null,null, null
        );

        return cursorLoader;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {

        memberCursorAdapter.swapCursor(cursor);

    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

        memberCursorAdapter.swapCursor(null);

    }
}