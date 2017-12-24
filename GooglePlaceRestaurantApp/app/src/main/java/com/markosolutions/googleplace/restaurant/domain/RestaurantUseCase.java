package com.markosolutions.googleplace.restaurant.domain;

import com.markosolutions.googleplace.restaurant.data.datasource.GooglePlaceCache;
import com.markosolutions.googleplace.restaurant.data.datasource.GooglePlaceStore;
import com.markosolutions.googleplace.restaurant.data.entity.GooglePlaceDetailsEntity;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class RestaurantUseCase {

    private GooglePlaceStore mGooglePlaceStore;

    private static final String GOOGLE_PLACE_TYPE_VALUE = "restaurant";
    private static final int GOOGLE_PLACE_RADIUS_VALUE = 2000;

    public RestaurantUseCase() {
        mGooglePlaceStore = new GooglePlaceStore();
    }

    public void getNearbyRestaurants
            (DisposableObserver<ArrayList<GooglePlaceDetailsEntity>> observer,
             double latitude,
             double longitude,
             String keyword) {

        Observable<ArrayList<GooglePlaceDetailsEntity>> observable = mGooglePlaceStore.getNearbyRestaurants(latitude, longitude, keyword,
                GOOGLE_PLACE_RADIUS_VALUE, GOOGLE_PLACE_TYPE_VALUE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
                observable.subscribeWith(observer);
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
