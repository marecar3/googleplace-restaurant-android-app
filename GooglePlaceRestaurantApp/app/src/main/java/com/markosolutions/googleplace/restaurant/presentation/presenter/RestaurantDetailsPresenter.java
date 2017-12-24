package com.markosolutions.googleplace.restaurant.presentation.presenter;

import com.markosolutions.googleplace.restaurant.data.entity.GooglePlaceDetailsEntity;
import com.markosolutions.googleplace.restaurant.domain.RestaurantUseCase;
import com.markosolutions.googleplace.restaurant.presentation.view.RestaurantDetailsView;

public class RestaurantDetailsPresenter {

    private RestaurantUseCase mRestaurantUseCase;
    private RestaurantDetailsView mRestaurantDetailsView;

    public RestaurantDetailsPresenter() {
        mRestaurantUseCase = new RestaurantUseCase();
    }

    public void attach(RestaurantDetailsView restaurantDetailsView) {
        mRestaurantDetailsView = restaurantDetailsView;
    }

    public void detach() {
        mRestaurantDetailsView = null;
    }

    public void getGooglePlaceDetailsEntity(String placeId) {
        GooglePlaceDetailsEntity googlePlaceDetailsEntity  =
                mRestaurantUseCase.getRestaurantDetails(placeId);
        if (mRestaurantDetailsView != null) {
            mRestaurantDetailsView.onRestaurantDetailsReceived(googlePlaceDetailsEntity);
        }
    }
}
