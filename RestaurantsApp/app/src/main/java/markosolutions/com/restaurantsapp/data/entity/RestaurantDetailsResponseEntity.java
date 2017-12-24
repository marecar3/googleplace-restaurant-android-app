package markosolutions.com.restaurantsapp.data.entity;

import com.google.gson.annotations.SerializedName;

public class RestaurantDetailsResponseEntity extends BaseResponseEntity {

    @SerializedName("result")
    private PlaceRestaurantDetailsEntity mPlaceRestaurantDetailsEntity;

    public PlaceRestaurantDetailsEntity getPlaceRestaurantDetailsEntity() {
        return mPlaceRestaurantDetailsEntity;
    }
}
