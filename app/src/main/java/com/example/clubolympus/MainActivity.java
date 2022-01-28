package com.example.clubolympus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.clubolympus.data.ClubOlympusContract.MemberEntry;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    TextView dataTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataTextView = findViewById(R.id.dataTextView);


        FloatingActionButton floatingActionButton = findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddMemberActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayData();
    }

    private void displayData() {
        String[] projection = {
                MemberEntry.KEY_ID,
                MemberEntry.KEY_FIRST_NAME,
                MemberEntry.KEY_LAST_NAME,
                MemberEntry.KEY_GENDER,
                MemberEntry.KEY_SPORT_TYPE
        };

        Cursor cursor = getContentResolver().query(
                MemberEntry.CONTENT_URI,
                projection,
                null,null, null
        );

        dataTextView.setText("All members\n\n");
        dataTextView.append(MemberEntry.KEY_ID + " " +
                MemberEntry.KEY_FIRST_NAME + " " +
                MemberEntry.KEY_LAST_NAME + " " +
                MemberEntry.KEY_GENDER + " " +
                MemberEntry.KEY_SPORT_TYPE);

        int idColumnIndex = cursor.getColumnIndex(MemberEntry.KEY_ID);
        int firstNameColumnIndex = cursor.getColumnIndex(MemberEntry.KEY_FIRST_NAME);
        int lastNameColumnIndex = cursor.getColumnIndex(MemberEntry.KEY_LAST_NAME);
        int genderColumnIndex = cursor.getColumnIndex(MemberEntry.KEY_GENDER);
        int sportTypeColumnIndex = cursor.getColumnIndex(MemberEntry.KEY_SPORT_TYPE);

        while (cursor.moveToNext()) {
            int currentId = cursor.getInt(idColumnIndex);
            String currentFirstName = cursor.getString(firstNameColumnIndex);
            String currentLastName = cursor.getString(lastNameColumnIndex);
            int currentGender = cursor.getInt(genderColumnIndex);
            String currentSportType = cursor.getString(sportTypeColumnIndex);

            dataTextView.append("\n" + currentId + " " +
                    currentFirstName + " " +
                    currentLastName + " " +
                    currentGender + " " +
                    currentSportType);
        }
        cursor.close();
    }
}