package com.markosolutions.googleplace.restaurant.data.entity;


import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class LocationEntity extends RealmObject {

    @SerializedName("lat")
    private double mLatitude;

    @SerializedName("lng")
    private double mLongitude;

    public double getLatitude() {
        return mLatitude;
    }

    public double getLongitude() {
        return mLongitude;
    }
}
