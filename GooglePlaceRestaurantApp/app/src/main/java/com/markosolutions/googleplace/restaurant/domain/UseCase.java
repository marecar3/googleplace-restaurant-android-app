package com.markosolutions.googleplace.restaurant.domain;

import com.markosolutions.googleplace.restaurant.data.datasource.GooglePlaceCache;
import com.markosolutions.googleplace.restaurant.data.datasource.GooglePlaceStore;
import com.markosolutions.googleplace.restaurant.data.entity.GooglePlaceDetailsEntity;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class UseCase {

    private GooglePlaceStore mGooglePlaceStore;

    private static final String GOOGLE_PLACE_TYPE_VALUE = "restaurant";
    private static final int GOOGLE_PLACE_RADIUS_VALUE = 500;

    public UseCase() {
        mGooglePlaceStore = new GooglePlaceStore();
    }

    public Observable<ArrayList<GooglePlaceDetailsEntity>> getNearbyRestaurants
            (double latitude,
             double longitude,
             String keyword) {
        return mGooglePlaceStore.getNearbyRestaurants(latitude, longitude, keyword,
                GOOGLE_PLACE_RADIUS_VALUE, GOOGLE_PLACE_TYPE_VALUE);
    }

    public GooglePlaceDetailsEntity getRestaurantDetails(String placeId) {
        return mGooglePlaceStore.getRestaurantDetails(placeId);
    }

    public List<GooglePlaceDetailsEntity> sortNearbyRestaurantsByBestMatch() {
        return mGooglePlaceStore.getSortedPlaceRestaurantEntities(GooglePlaceCache.SortCriteria.BEST_MATCH);
    }

    public List<GooglePlaceDetailsEntity> sortNearbyRestaurantsByDistance() {
        return mGooglePlaceStore.getSortedPlaceRestaurantEntities(GooglePlaceCache.SortCriteria.DISTANCE);
    }

    public List<GooglePlaceDetailsEntity> sortNearbyRestaurantsByMostReviewed() {
        return mGooglePlaceStore.getSortedPlaceRestaurantEntities(GooglePlaceCache.SortCriteria.MOST_REVIEWED);
    }

}
