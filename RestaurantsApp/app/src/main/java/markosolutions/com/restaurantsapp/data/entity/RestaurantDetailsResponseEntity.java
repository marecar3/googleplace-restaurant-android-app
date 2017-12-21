package markosolutions.com.restaurantsapp.data.entity;

import com.google.gson.annotations.SerializedName;

public class RestaurantDetailsResponseEntity extends BaseResponseEntity {

    @SerializedName("result")
    private PlaceRestaurantEntity mPlaceRestaurantEntity;

    public PlaceRestaurantEntity getPlaceRestaurantEntity() {
        return mPlaceRestaurantEntity;
    }
}
