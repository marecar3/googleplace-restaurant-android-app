package markosolutions.com.restaurantsapp.domain;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import markosolutions.com.restaurantsapp.data.datasource.PlaceRestaurantCache;
import markosolutions.com.restaurantsapp.data.datasource.PlaceRestaurantStore;
import markosolutions.com.restaurantsapp.data.entity.PlaceRestaurantDetailsEntity;

public class UseCase {

    private PlaceRestaurantStore mPlaceRestaurantStore;

    private static final String GOOGLE_PLACE_TYPE_VALUE = "restaurant";
    private static final int GOOGLE_PLACE_RADIUS_VALUE = 500;

    public UseCase() {
        mPlaceRestaurantStore = new PlaceRestaurantStore();
    }

    public Observable<ArrayList<PlaceRestaurantDetailsEntity>> getNearbyRestaurants
            (double latitude,
             double longitude,
             String keyword) {
        return mPlaceRestaurantStore.getNearbyRestaurants(latitude, longitude, keyword,
                GOOGLE_PLACE_RADIUS_VALUE, GOOGLE_PLACE_TYPE_VALUE);
    }

    public PlaceRestaurantDetailsEntity getRestaurantDetails(String placeId) {
        return mPlaceRestaurantStore.getRestaurantDetails(placeId);
    }

    public List<PlaceRestaurantDetailsEntity> sortNearbyRestaurantsByBestMatch() {
        return mPlaceRestaurantStore.getSortedPlaceRestaurantEntities(PlaceRestaurantCache.SortCriteria.BEST_MATCH);
    }

    public List<PlaceRestaurantDetailsEntity> sortNearbyRestaurantsByDistance() {
        return mPlaceRestaurantStore.getSortedPlaceRestaurantEntities(PlaceRestaurantCache.SortCriteria.DISTANCE);
    }

    public List<PlaceRestaurantDetailsEntity> sortNearbyRestaurantsByMostReviewed() {
        return mPlaceRestaurantStore.getSortedPlaceRestaurantEntities(PlaceRestaurantCache.SortCriteria.MOST_REVIEWED);
    }

}
