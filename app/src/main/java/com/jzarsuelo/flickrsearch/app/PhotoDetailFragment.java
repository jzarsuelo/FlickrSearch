package com.jzarsuelo.flickrsearch.app;

import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jzarsuelo.flickrsearch.app.helper.ConnectivityStatusChecker;
import com.jzarsuelo.flickrsearch.app.helper.FlickrPhotoInfoXmlParser;
import com.jzarsuelo.flickrsearch.app.model.FlickrPhotoInfoModel;
import com.jzarsuelo.flickrsearch.app.model.FlickrPhotoModel;
import com.squareup.picasso.Picasso;

import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * A placeholder fragment containing a simple view.
 */
public class PhotoDetailFragment extends Fragment {

    private FlickrPhotoModel mFlickrPhotoModel;

    private Context mContext;

    private ImageView mPhoto;
    private TextView mPhotoTitle;
    private TextView mPhotoOwner;
    private TextView mPhotoDatePosted;

    private FlickrPhotoInfoModel mFlickrPhotoInfoModel;

    public PhotoDetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View viewRoot = inflater.inflate(R.layout.fragment_photo_detail, container, false);

        mContext = viewRoot.getContext();
        mPhoto = (ImageView) viewRoot.findViewById(R.id.photo_detail_image);
        mPhotoTitle = (TextView) viewRoot.findViewById(R.id.photo_detail_title);
        mPhotoOwner = (TextView) viewRoot.findViewById(R.id.photo_detail_owner);
        mPhotoDatePosted = (TextView) viewRoot.findViewById(R.id.photo_detail_date_posted);

        return viewRoot;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        new LoadPhotoInfoTask().execute();
    }

    public void setFlickrPhotoModel(FlickrPhotoModel flickrPhotoModel){
        this.mFlickrPhotoModel = flickrPhotoModel;
    }

    private class LoadPhotoInfoTask extends AsyncTask<Void, Void, Void> {

        private final String TAG_TASK = LoadPhotoInfoTask.class.getSimpleName();
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

            String flickrApiKey = getString(R.string.flickr_key);

            String photoInfoUriString = String.format(
                    getString(R.string.flick_photo_info_uri),
                    mFlickrPhotoModel.getId(),
                    flickrApiKey
            );

            loadXmlFromNetwork(photoInfoUriString);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            String photoUrl = String.format(mContext.getString(R.string.flickr_photo_uri),
                    mFlickrPhotoModel.getFarm(),
                    mFlickrPhotoModel.getServer(),
                    mFlickrPhotoModel.getId(),
                    mFlickrPhotoModel.getSecret());

            Picasso.with(mContext).load(photoUrl).into(mPhoto);

            String photoOwner = String.format(getString(R.string.photo_detail_owner),
                    mFlickrPhotoInfoModel.getOwner());

            mPhotoOwner.setText(photoOwner);
            mPhotoTitle.setText(mFlickrPhotoInfoModel.getTitle());
            mPhotoDatePosted.setText(mFlickrPhotoInfoModel.getFormattedDatedPosted());


        }

        private void loadXmlFromNetwork(String photoInfoUriString) {

            ConnectivityStatusChecker connectivityStatusChecker = new ConnectivityStatusChecker(mContext);
            if ( !connectivityStatusChecker.isConnected() ) {
                Toast.makeText(mContext, getString(R.string.error_no_internet), Toast.LENGTH_SHORT)
                        .show();
                return;
            }

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            try {
                URL photoInfoUrl = new URL(photoInfoUriString);

                Log.d(TAG_TASK, photoInfoUrl.toString());

                // create the request, and open the connection
                urlConnection = (HttpURLConnection) photoInfoUrl.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();

                if( inputStream == null) {
                    Log.e(TAG_TASK, "No response.");
                }

                FlickrPhotoInfoXmlParser parser = new FlickrPhotoInfoXmlParser();
                mFlickrPhotoInfoModel = parser.parse(inputStream);

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
