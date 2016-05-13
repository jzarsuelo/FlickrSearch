package com.jzarsuelo.flickrsearch.app;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

public class MainActivity extends ActionBarActivity
        implements SearchFragment.OnSearchListener{

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    /**
     * Implemented method from {@link SearchFragment.OnSearchListener}
     * which runs when user perform the search
     * @param searchText
     */
    @Override
    public void onSearch(String searchText) {
        Log.d(TAG, searchText);

        SearchResultFragment srf = (SearchResultFragment) getFragmentManager()
                .findFragmentById(R.id.search_fragment_result);

        srf.performSearch(searchText);
    }
}
