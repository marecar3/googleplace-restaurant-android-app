package markosolutions.com.restaurantsapp.domain;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import markosolutions.com.restaurantsapp.data.datasource.GooglePlaceCache;
import markosolutions.com.restaurantsapp.data.datasource.GooglePlaceStore;
import markosolutions.com.restaurantsapp.data.entity.GooglePlaceDetailsEntity;

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
