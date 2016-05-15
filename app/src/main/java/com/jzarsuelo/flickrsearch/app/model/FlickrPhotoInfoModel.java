package com.jzarsuelo.flickrsearch.app.model;

/**
 * Created by JanPaolo on 5/15/2016.
 */
public class FlickrPhotoInfoModel {
    private String mTitle;
    private String mOwner;
    private String mDatePosted;

    public FlickrPhotoInfoModel() {}

    public FlickrPhotoInfoModel(String datePosted, String owner, String title) {
        mDatePosted = datePosted;
        mOwner = owner;
        mTitle = title;
    }

    public String getDatePosted() {
        return mDatePosted;
    }

    public void setDatePosted(String datePosted) {
        mDatePosted = datePosted;
    }

    public String getOwner() {
        return mOwner;
    }

    public void setOwner(String owner) {
        mOwner = owner;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }
}
