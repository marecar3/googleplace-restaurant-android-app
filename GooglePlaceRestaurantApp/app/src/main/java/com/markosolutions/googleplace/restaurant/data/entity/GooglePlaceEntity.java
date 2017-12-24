package com.markosolutions.googleplace.restaurant.data.entity;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;



public class GooglePlaceEntity extends RealmObject {

    @SerializedName("name")
    private String mName;

    @SerializedName("geometry")
    private GeometryEntity mGeometryEntity;

    @SerializedName("icon")
    private String mIcon;

    @SerializedName("id")
    private String mId;

    @SerializedName("opening_hours")
    private OpeningHoursEntity mOpeningHoursEntity;

    @SerializedName("photos")
    private RealmList<PhotoEntity> mPhotoEntities;

    @SerializedName("place_id")
    private String mPlaceId;

    @SerializedName("rating")
    private float mRating;

    public String getIcon() {
        return mIcon;
    }

    public String getId() {
        return mId;
    }

    public OpeningHoursEntity getOpeningHoursEntity() {
        return mOpeningHoursEntity;
    }

    public RealmList<PhotoEntity> getPhotoEntities() {
        return mPhotoEntities;
    }

    public String getPlaceId() {
        return mPlaceId;
    }

    public float getRating() {
        return mRating;
    }

    public GeometryEntity getGeometryEntity() {
        return mGeometryEntity;
    }

    public String getName() {
        return mName;
    }
}
