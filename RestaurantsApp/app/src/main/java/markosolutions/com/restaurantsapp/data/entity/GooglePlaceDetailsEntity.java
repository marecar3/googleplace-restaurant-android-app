package markosolutions.com.restaurantsapp.data.entity;


import com.google.gson.annotations.SerializedName;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;

public class GooglePlaceDetailsEntity extends RealmObject {

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

    public List<PhotoEntity> getPhotoEntities() {
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

    @SerializedName("formatted_address")
    private String mFormattedAddress;

    @SerializedName("formatted_phone_number")
    private String mFormaatterPhoneNumber;

    @SerializedName("reviews")
    private RealmList<ReviewEntity> mReviewEntities;

    @SerializedName("website")
    private String mWebsite;

    private double mDistance;

    private int mNumberOfReviews;

    public String getFormattedAddress() {
        return mFormattedAddress;
    }

    public String getFormaatterPhoneNumber() {
        return mFormaatterPhoneNumber;
    }

    public RealmList<ReviewEntity> getReviewEntities() {
        return mReviewEntities;
    }

    public String getWebsite() {
        return mWebsite;
    }

    public void setDistance(double distance) {
        mDistance = distance;
    }

    public double getDistance() {
        return mDistance;
    }

    public void setNumberOfReviews(int numberOfReviews) {
        mNumberOfReviews = numberOfReviews;
    }

    public int getNumberOfReviews() {
        return mNumberOfReviews;
    }
}
