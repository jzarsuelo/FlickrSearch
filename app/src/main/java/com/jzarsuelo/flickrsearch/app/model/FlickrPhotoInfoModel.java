package com.jzarsuelo.flickrsearch.app.model;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

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

    public String getFormattedDatedPosted(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        Date d = new Date( Long.parseLong(mDatePosted) * 1000L );

        String formattedDate = sdf.format( d );

        return formattedDate;
    }

    @Override
    public String toString() {
        return "FlickrPhotoInfoModel{" +
                "mDatePosted='" + mDatePosted + '\'' +
                ", mTitle='" + mTitle + '\'' +
                ", mOwner='" + mOwner + '\'' +
                '}';
    }
}
