package com.jzarsuelo.flickrsearch.app;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.jzarsuelo.flickrsearch.app.model.FlickrPhotoModel;

public class PhotoDetailActivity extends ActionBarActivity {

    public static final String EXTRA_PHOTO = "photo";

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

        Intent i = getIntent();
        Bundle b = i.getExtras();
        FlickrPhotoModel flickrPhotoModel = b.getParcelable(EXTRA_PHOTO);

        PhotoDetailFragment photoDetailFragment = (PhotoDetailFragment)
                getFragmentManager().findFragmentById(R.id.photo_detail_fragment);
        photoDetailFragment.setFlickrPhotoModel(flickrPhotoModel);

    }

}
