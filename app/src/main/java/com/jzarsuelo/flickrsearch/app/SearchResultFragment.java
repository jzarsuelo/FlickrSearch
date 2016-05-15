package com.jzarsuelo.flickrsearch.app;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.jzarsuelo.flickrsearch.app.adapter.SearchResultPhotoAdapter;
import com.jzarsuelo.flickrsearch.app.helper.ConnectivityStatusChecker;
import com.jzarsuelo.flickrsearch.app.helper.FlickrSearchResultXmlParser;
import com.jzarsuelo.flickrsearch.app.model.FlickrPhotoModel;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;

import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


public class SearchResultFragment extends Fragment {

    private final String TAG = SearchResultFragment.class.getSimpleName();

    private Context mContext;

    private List<FlickrPhotoModel> mFlickrPhotoModelList = new ArrayList<FlickrPhotoModel>();
    private int mPageToLoad;
    private String mSearchText;

    private GridView mSearchResultGridView;

    private SearchResultPhotoAdapter mAdapter;
    private SwipyRefreshLayout mSwipeRefreshContainer;
    private boolean mIsLoadNextResult;

    public SearchResultFragment() {
        // Required empty public constructor
    }

    /**
     * Execute {@link FetchSearchResultTask}
     *
     * @param searchText text to search in Flickr
     */
    public void performSearch(String searchText){
        mPageToLoad = 1;
        mSearchText = searchText;

        mFlickrPhotoModelList.clear();
        mSwipeRefreshContainer.setRefreshing(true);

        new FetchSearchResultTask().execute();
    }

    /**
     * Load the succeeding search result
     */
    private void loadNextSearchResult() {
        mPageToLoad++;

        new FetchSearchResultTask().execute();
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
        mAdapter = new SearchResultPhotoAdapter(mContext, mFlickrPhotoModelList);

        mSwipeRefreshContainer = (SwipyRefreshLayout) viewRoot
                .findViewById(R.id.search_result_swiperefreshcontainer);
        mSwipeRefreshContainer.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh(SwipyRefreshLayoutDirection swipyRefreshLayoutDirection) {
                loadNextSearchResult();
            }

        });

        mSearchResultGridView = (GridView) viewRoot.findViewById(R.id.search_result_gridview);
        mSearchResultGridView.setAdapter(mAdapter);
        mSearchResultGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent i = new Intent(getActivity(), PhotoDetailActivity.class);
                i.putExtra(PhotoDetailActivity.EXTRA_PHOTO, mFlickrPhotoModelList.get(position));

                startActivity(i);
            }
        });

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

    /**
     * Task that performs the search in Flickr then map
     * the result to this fragment
     */
    private class FetchSearchResultTask extends AsyncTask<Void, Void, Void>{

        private final String TAG_TASK = FetchSearchResultTask.class.getSimpleName();
        private boolean isConnected;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            ConnectivityStatusChecker connectivityStatusChecker = new ConnectivityStatusChecker(mContext);
            isConnected = connectivityStatusChecker.isConnected();
            if ( !isConnected ) {
                Toast.makeText(mContext, getString(R.string.error_no_internet), Toast.LENGTH_SHORT)
                        .show();
                return;
            }
        }

        @Override
        protected Void doInBackground(Void... params) {
            if( !isConnected ){
                return null;
            }

            String flickrPhotoSearchMethod = getString(R.string.flickr_photo_search);
            String flickrApiKey = getString(R.string.flickr_key);
            int flickrSearchResultLimit = getResources().getInteger(R.integer.flickr_result_per_page);

            try {
                String searchPhotoUriString = String.format(getString(R.string.flickr_search_uri),
                        flickrPhotoSearchMethod,
                        flickrApiKey,
                        URLEncoder.encode(mSearchText, "UTF-8"),
                        flickrSearchResultLimit,
                        mPageToLoad);

                loadXmlFromNetwork(searchPhotoUriString);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.d(TAG, "mAdapterCount: " + mSearchResultGridView.getAdapter().getCount());
            mAdapter.notifyDataSetChanged();
            mIsLoadNextResult = true;
            mSwipeRefreshContainer.setRefreshing(false);
        }

        private void loadXmlFromNetwork(String searchPhotoUriString) {

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            try {
                URL searchPhotoUrl = new URL(searchPhotoUriString);

                Log.d(TAG_TASK, searchPhotoUrl.toString());

                // create the request, and open the connection
                urlConnection = (HttpURLConnection) searchPhotoUrl.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();

                if( inputStream == null) {
                    Log.e(TAG, "No response.");
                }

                FlickrSearchResultXmlParser xmlParser = new FlickrSearchResultXmlParser();
                mFlickrPhotoModelList.addAll(xmlParser.parse(inputStream));

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }


}
