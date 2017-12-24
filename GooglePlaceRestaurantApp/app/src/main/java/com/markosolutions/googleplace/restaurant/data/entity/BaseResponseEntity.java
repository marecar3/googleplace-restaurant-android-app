package com.markosolutions.googleplace.restaurant.data.entity;


import com.google.gson.annotations.SerializedName;

public class BaseResponseEntity {

    @SerializedName("status")
    private String mStatus;

    public String getStatus() {
        return mStatus;
    }
}
