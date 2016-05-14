package com.jzarsuelo.flickrsearch.app.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.jzarsuelo.flickrsearch.app.R;
import com.jzarsuelo.flickrsearch.app.model.FlickrPhotoModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JanPaolo on 5/14/2016.
 */
public class SearchResultPhotoAdapter extends BaseAdapter {

    private static final String TAG = SearchResultPhotoAdapter.class.getSimpleName();

    private Context mContext;
    private List<FlickrPhotoModel> mFlickrPhotoModelList = new ArrayList<FlickrPhotoModel>();

    public SearchResultPhotoAdapter(Context context, List<FlickrPhotoModel> flickrPhotoModelList) {
        mContext = context;
        mFlickrPhotoModelList = flickrPhotoModelList;
    }

    @Override
    public int getCount() {
        return mFlickrPhotoModelList.size();
    }

    @Override
    public Object getItem(int position) {
        int positionToReturn = position;
        if (position >= mFlickrPhotoModelList.size()) {
            positionToReturn = mFlickrPhotoModelList.size() - 1;
        } else if(positionToReturn < 0) {
            positionToReturn = 0;
        }

        return mFlickrPhotoModelList.get(positionToReturn);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if(convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
            imageView.setAdjustViewBounds(true);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }

        FlickrPhotoModel photoModel = mFlickrPhotoModelList.get(position);
        String photoUrl = String.format(mContext.getString(R.string.flickr_photo_uri),
                photoModel.getFarm(),
                photoModel.getServer(),
                photoModel.getId(),
                photoModel.getSecret());

        Picasso.with(mContext).load(photoUrl).into(imageView);

        return imageView;
    }
}
