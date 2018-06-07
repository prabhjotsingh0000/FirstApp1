package com.example.prabhjot.firstapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ThirdActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        // Fetching data from a parcelable object passed from DataActivity
        Item item = getIntent().getParcelableExtra("item");

        TextView title = (TextView) findViewById(R.id.title);
        TextView rating = (TextView) findViewById(R.id.rating);
        TextView releaseYear = (TextView) findViewById(R.id.release_year);

        title.setText("Title:" + item.title);
        rating.setText(item.rating);
        releaseYear.setText(item.releaseYear);

    }
}
