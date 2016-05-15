package com.jzarsuelo.flickrsearch.app.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by JanPaolo on 5/13/2016.
 */
public class FlickrPhotoModel implements Parcelable {
    private String mId;
    private String mOwner;
    private String mSecret;
    private Integer mServer;
    private Integer mFarm;
    private String mTitle;

    public FlickrPhotoModel(String id, String owner, String secret,
                            Integer server, Integer farm, String title) {
        mFarm = farm;
        mId = id;
        mOwner = owner;
        mSecret = secret;
        mServer = server;
        mTitle = title;
    }

    public FlickrPhotoModel(String id, String owner, String secret,
                            String server, String farm, String title) {
        this(id, owner, secret, Integer.parseInt(server), Integer.parseInt(farm), title);
    }

    public FlickrPhotoModel(){}

    public Integer getFarm() {
        return mFarm;
    }

    public void setFarm(Integer farm) {
        mFarm = farm;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getOwner() {
        return mOwner;
    }

    public void setOwner(String owner) {
        mOwner = owner;
    }

    public String getSecret() {
        return mSecret;
    }

    public void setSecret(String secret) {
        mSecret = secret;
    }

    public Integer getServer() {
        return mServer;
    }

    public void setServer(Integer server) {
        mServer = server;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    @Override
    public String toString() {
        return "FlickrPhotoModel{" +
                "mFarm=" + mFarm +
                ", mId='" + mId + '\'' +
                ", mOwner='" + mOwner + '\'' +
                ", mSecret='" + mSecret + '\'' +
                ", mServer=" + mServer +
                ", mTitle='" + mTitle + '\'' +
                '}';
    }

    public static final Parcelable.Creator<FlickrPhotoModel> CREATOR =
            new Creator<FlickrPhotoModel>(){

                @Override
                public FlickrPhotoModel createFromParcel(Parcel source) {
                    FlickrPhotoModel flickrPhotoModel = new FlickrPhotoModel();

                    flickrPhotoModel.setId( source.readString() );
                    flickrPhotoModel.setOwner( source.readString() );
                    flickrPhotoModel.setSecret( source.readString() );
                    flickrPhotoModel.setServer( source.readInt() );
                    flickrPhotoModel.setFarm( source.readInt() );
                    flickrPhotoModel.setTitle( source.readString() );

                    return flickrPhotoModel;
                }

                @Override
                public FlickrPhotoModel[] newArray(int size) {
                    return new FlickrPhotoModel[0];
                }
            };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mId);
        dest.writeString(mOwner);
        dest.writeString(mSecret);
        dest.writeInt(mServer);
        dest.writeInt(mFarm);
        dest.writeString(mTitle);
    }
}
