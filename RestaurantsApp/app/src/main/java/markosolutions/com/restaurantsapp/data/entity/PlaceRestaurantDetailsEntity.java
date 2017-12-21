package markosolutions.com.restaurantsapp.data.entity;


import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PlaceRestaurantDetailsEntity extends PlaceRestaurantEntity {

    @SerializedName("formatted_address")
    private String mFormattedAddress;

    @SerializedName("formatted_phone_number")
    private String mFormaatterPhoneNumber;

    @SerializedName("reviews")
    private ArrayList<ReviewEntity> mReviewEntities;

    @SerializedName("website")
    private String mWebsite;

    public String getFormattedAddress() {
        return mFormattedAddress;
    }

    public String getFormaatterPhoneNumber() {
        return mFormaatterPhoneNumber;
    }

    public ArrayList<ReviewEntity> getReviewEntities() {
        return mReviewEntities;
    }

    public String getWebsite() {
        return mWebsite;
    }
}
