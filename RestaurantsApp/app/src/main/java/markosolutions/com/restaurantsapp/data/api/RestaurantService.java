package markosolutions.com.restaurantsapp.data.api;


import io.reactivex.Observable;
import markosolutions.com.restaurantsapp.data.entity.RestaurantDetailsResponseEntity;
import markosolutions.com.restaurantsapp.data.entity.RestaurantsResponseEntity;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RestaurantService {

    @GET("nearbysearch/json")
    Observable<RestaurantsResponseEntity> getNearbyRestaurants(@Query("location") String location,
                                                               @Query("radius") int radius,
                                                               @Query("type") String type);

    @GET("details/json")
    Observable<RestaurantDetailsResponseEntity> getRestaurantDetails(@Query("placeid") String placeId);

}
