package com.markosolutions.googleplace.restaurant.presentation.view;


import com.markosolutions.googleplace.restaurant.data.entity.GooglePlaceDetailsEntity;

public interface RestaurantDetailsView {

    void onRestaurantDetailsReceived(GooglePlaceDetailsEntity googlePlaceDetailsEntity);
    void onLoadingStarted();
    void onLoadingFinished();
    void onError();

}
