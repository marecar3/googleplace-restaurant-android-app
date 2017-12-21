package markosolutions.com.restaurantsapp.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class RestaurantsResponseEntity extends BaseResponseEntity {

    @SerializedName("results")
    private ArrayList<PlaceRestaurantEntity> mPlaceRestaurantEntities;

    @SerializedName("next_page_token")
    private String mNextPageToken;

    public ArrayList<PlaceRestaurantEntity> getPlaceRestaurantEntities() {
        return mPlaceRestaurantEntities;
    }

    public String getNextPageToken() {
        return mNextPageToken;
    }
}
