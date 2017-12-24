package com.markosolutions.googleplace.restaurant.presentation.presenter;

import com.markosolutions.googleplace.restaurant.data.entity.GooglePlaceDetailsEntity;
import com.markosolutions.googleplace.restaurant.domain.RestaurantUseCase;
import com.markosolutions.googleplace.restaurant.presentation.view.RestaurantListView;

import java.util.ArrayList;

import io.reactivex.observers.DisposableObserver;

public class RestaurantListPresenter {

    private RestaurantUseCase mRestaurantUseCase;
    private RestaurantListView mRestaurantListView;

    public RestaurantListPresenter() {
        mRestaurantUseCase = new RestaurantUseCase();
    }

    public void attach(RestaurantListView restaurantListView) {
        mRestaurantListView = restaurantListView;
    }

    public void detach() {
        mRestaurantListView = null;
    }

    public void getRestaurantList(double latitude, double longitude, String query) {
        mRestaurantUseCase.getNearbyRestaurants(new RestaurantListObserver(), latitude, longitude, query);
    }

    private final class RestaurantListObserver extends DisposableObserver<ArrayList<GooglePlaceDetailsEntity>> {

        @Override public void onComplete() {

        }

        @Override public void onError(Throwable e) {

        }

        @Override public void onNext(ArrayList<GooglePlaceDetailsEntity> googlePlaceDetailsEntities) {
            mRestaurantListView.onRestaurantListReceived(googlePlaceDetailsEntities);
        }
    }

}
