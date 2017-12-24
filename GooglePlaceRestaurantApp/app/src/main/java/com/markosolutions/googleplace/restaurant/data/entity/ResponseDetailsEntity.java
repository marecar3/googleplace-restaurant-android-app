package com.markosolutions.googleplace.restaurant.data.entity;

import com.google.gson.annotations.SerializedName;

public class ResponseDetailsEntity extends BaseResponseEntity {

    @SerializedName("result")
    private GooglePlaceDetailsEntity mGooglePlaceDetailsEntity;

    public GooglePlaceDetailsEntity getGooglePlaceDetailsEntity() {
        return mGooglePlaceDetailsEntity;
    }
}
