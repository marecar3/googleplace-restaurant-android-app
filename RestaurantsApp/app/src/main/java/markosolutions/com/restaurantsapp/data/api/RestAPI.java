package markosolutions.com.restaurantsapp.data.api;

import android.text.TextUtils;

import java.util.HashMap;

import io.reactivex.Observable;
import markosolutions.com.restaurantsapp.data.entity.ResponseDetailsEntity;
import markosolutions.com.restaurantsapp.data.entity.ResponseEntity;

public class RestAPI {

    private static final String GOOGLE_PLACE_LOCATION_KEY = "location";
    private static final String GOOGLE_PLACE_KEYWORD_KEY = "keyword";

    private GooglePlaceService mGooglePlaceService;

    public RestAPI() {
        mGooglePlaceService = ServiceGenerator.createService(GooglePlaceService.class);
    }

    public Observable<ResponseEntity> getNearbyRestaurants(double latitude,
                                                           double longitude,
                                                           String keyword,
                                                           int radius,
                                                           String placeType) {
        HashMap<String, String> params = generateAdditionalParams(latitude, longitude, keyword);
        return mGooglePlaceService.getNearbyRestaurants(radius, placeType, params);
    }

    private HashMap<String, String> generateAdditionalParams(double latitude, double longitude, String keyword) {
        HashMap<String, String> params = new HashMap<>(0);

        if (latitude != 0 && longitude != 0) {
            params.put(GOOGLE_PLACE_LOCATION_KEY, String.format("%f,%f", latitude, longitude));
        }

        if (!TextUtils.isEmpty(keyword)) {
            params.put(GOOGLE_PLACE_KEYWORD_KEY, keyword);
        }

        return params;
    }

    public Observable<ResponseDetailsEntity> getRestaurantDetails(String placeId) {
        return mGooglePlaceService.getRestaurantDetails(placeId);
    }

}
