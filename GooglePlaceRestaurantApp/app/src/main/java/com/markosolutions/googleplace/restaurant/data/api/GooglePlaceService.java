package com.markosolutions.googleplace.restaurant.data.api;


import com.markosolutions.googleplace.restaurant.data.entity.ResponseDetailsEntity;
import com.markosolutions.googleplace.restaurant.data.entity.ResponseEntity;

import java.util.HashMap;

import io.reactivex.Observable;
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
