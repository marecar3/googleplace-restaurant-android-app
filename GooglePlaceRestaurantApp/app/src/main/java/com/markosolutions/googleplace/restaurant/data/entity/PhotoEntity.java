package com.markosolutions.googleplace.restaurant.data.entity;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class PhotoEntity extends RealmObject {

    @SerializedName("height")
    private int mHeight;

    @SerializedName("width")
    private int mWidth;

    @SerializedName("photo_reference")
    private String mPhotoReference;

    public int getHeight() {
        return mHeight;
    }

    public int getWidth() {
        return mWidth;
    }

    public String getPhotoReference() {
        return mPhotoReference;
    }
}
