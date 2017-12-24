package markosolutions.com.restaurantsapp.data.api;


import java.util.HashMap;

import io.reactivex.Observable;
import markosolutions.com.restaurantsapp.data.entity.RestaurantDetailsResponseEntity;
import markosolutions.com.restaurantsapp.data.entity.RestaurantsResponseEntity;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface RestaurantService {

    @GET("nearbysearch/json")
    Observable<RestaurantsResponseEntity> getNearbyRestaurants(@Query("radius") int radius,
                                                               @Query("type") String type,
                                                               @QueryMap HashMap<String, String> additionalParams);

    @GET("details/json")
    Observable<RestaurantDetailsResponseEntity> getRestaurantDetails(@Query("placeid") String placeId);

}
