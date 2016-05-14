package com.jzarsuelo.flickrsearch.app.model;

/**
 * Created by JanPaolo on 5/13/2016.
 */
public class FlickrPhotoModel {
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
}
