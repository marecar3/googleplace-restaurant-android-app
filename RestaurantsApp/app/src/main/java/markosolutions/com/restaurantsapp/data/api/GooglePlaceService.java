package markosolutions.com.restaurantsapp.data.api;


import java.util.HashMap;

import io.reactivex.Observable;
import markosolutions.com.restaurantsapp.data.entity.ResponseDetailsEntity;
import markosolutions.com.restaurantsapp.data.entity.ResponseEntity;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface GooglePlaceService {

    @GET("nearbysearch/json")
    Observable<ResponseEntity> getNearbyRestaurants(@Query("radius") int radius,
                                                    @Query("type") String type,
                                                    @QueryMap HashMap<String, String> additionalParams);

    @GET("details/json")
    Observable<ResponseDetailsEntity> getRestaurantDetails(@Query("placeid") String placeId);

}
