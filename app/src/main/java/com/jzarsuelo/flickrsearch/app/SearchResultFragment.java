package com.jzarsuelo.flickrsearch.app;

import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class SearchResultFragment extends Fragment {

    private final String TAG = SearchResultFragment.class.getSimpleName();

    private Context mContext;

    public SearchResultFragment() {
        // Required empty public constructor
    }

    /**
     * Execute {@link FetchSearchResultTask}
     *
     * @param searchText text to search in Flickr
     */
    public void performSearch(String searchText){
        new FetchSearchResultTask().execute(searchText);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View viewRoot = inflater.inflate(R.layout.fragment_search_result, container, false);

        mContext = viewRoot.getContext();

        return viewRoot;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private class FetchSearchResultTask extends AsyncTask<String, Void, Void>{

        private final String TAG_TASK = FetchSearchResultTask.class.getSimpleName();

        @Override
        protected Void doInBackground(String... params) {
            String flickrPhotoSearchMethod = mContext.getString(R.string.flickr_photo_search);
            String flickrApiKey = mContext.getString(R.string.flickr_key);
            int flickrSearchResultLimit = getResources().getInteger(R.integer.flickr_result_per_page);
            int page = 1;

            String searchPhotoUri = String.format(getString(R.string.flickr_search_uri),
                    flickrPhotoSearchMethod,
                    flickrApiKey,
                    params[0],
                    flickrSearchResultLimit,
                    page);

            Log.d(TAG_TASK, searchPhotoUri);

            return null;
        }
    }


}
