package com.jzarsuelo.flickrsearch.app;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

public class PhotoDetailActivity extends ActionBarActivity {

    public static final String EXTRA_PHOTO_ID = "photoId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getActionBar();

        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        setContentView(R.layout.activity_photo_detail);

    }

}
