package com.jzarsuelo.flickrsearch.app;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jzarsuelo.flickrsearch.app.helper.FlickrXmlParser;

import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;


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
            String flickrPhotoSearchMethod = getString(R.string.flickr_photo_search);
            String flickrApiKey = getString(R.string.flickr_key);
            String searchText = params[0];
            int flickrSearchResultLimit = getResources().getInteger(R.integer.flickr_result_per_page);
            int page = 1;


            String searchPhotoUriString = String.format(getString(R.string.flickr_search_uri),
                    flickrPhotoSearchMethod,
                    flickrApiKey,
                    searchText,
                    flickrSearchResultLimit,
                    page);

            loadXmlFromNetwork(searchPhotoUriString);


            return null;
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
                    // TODO no result
                    Log.e(TAG, "No response. Check internet connectivity");
                }

                FlickrXmlParser xmlParser = new FlickrXmlParser();
                xmlParser.parse(inputStream);

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
