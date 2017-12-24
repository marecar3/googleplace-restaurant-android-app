package markosolutions.com.restaurantsapp.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResponseEntity extends BaseResponseEntity {

    @SerializedName("results")
    private ArrayList<GooglePlaceEntity> mPlaceRestaurantEntities;

    @SerializedName("next_page_token")
    private String mNextPageToken;

    public ArrayList<GooglePlaceEntity> getPlaceRestaurantEntities() {
        return mPlaceRestaurantEntities;
    }

    public String getNextPageToken() {
        return mNextPageToken;
    }
}
