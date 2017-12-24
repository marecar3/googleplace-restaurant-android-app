package com.markosolutions.googleplace.restaurant.presentation.view;

import com.markosolutions.googleplace.restaurant.data.entity.GooglePlaceDetailsEntity;

import java.util.List;

public interface RestaurantListView {

    void onRestaurantListReceived(List<GooglePlaceDetailsEntity> restaurantList);
    void onLoadingStarted();
    void onLoadingFinished();
    void onError();
}
