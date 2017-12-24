package com.markosolutions.googleplace.restaurant.presentation.presenter;

import android.util.Log;

import com.markosolutions.googleplace.restaurant.data.entity.GooglePlaceDetailsEntity;
import com.markosolutions.googleplace.restaurant.domain.RestaurantUseCase;
import com.markosolutions.googleplace.restaurant.presentation.view.RestaurantListView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.observers.DisposableObserver;

public class RestaurantListPresenter {

    private static final String TAG = "RestaurantListPresenter";

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
        mRestaurantListView.onLoadingStarted();
        mRestaurantUseCase.getNearbyRestaurants(new RestaurantListObserver(), latitude, longitude, query);
    }

    public void sortNearbyRestaurantsByDistance() {
        List<GooglePlaceDetailsEntity> sorted = mRestaurantUseCase.sortNearbyRestaurantsByDistance();
        mRestaurantListView.onRestaurantListReceived(sorted);
    }

    public void sortNearbyRestaurantsByMostReviewed() {
        List<GooglePlaceDetailsEntity> sorted = mRestaurantUseCase.sortNearbyRestaurantsByMostReviewed();
        mRestaurantListView.onRestaurantListReceived(sorted);
    }

    public void sortNearbyRestaurantsByBestMatch() {
        List<GooglePlaceDetailsEntity> sorted = mRestaurantUseCase.sortNearbyRestaurantsByBestMatch();
        mRestaurantListView.onRestaurantListReceived(sorted);
    }

    private final class RestaurantListObserver extends DisposableObserver<ArrayList<GooglePlaceDetailsEntity>> {

        @Override public void onComplete() {
            if (mRestaurantListView == null) return;

            mRestaurantListView.onLoadingFinished();
        }

        @Override public void onError(Throwable e) {
            Log.e(TAG, e.getMessage());
            if (mRestaurantListView == null) return;

            mRestaurantListView.onLoadingFinished();
        }

        @Override public void onNext(ArrayList<GooglePlaceDetailsEntity> googlePlaceDetailsEntities) {
            if (mRestaurantListView == null) return;

            mRestaurantListView.onRestaurantListReceived(googlePlaceDetailsEntities);
        }
    }

}
