package markosolutions.com.restaurantsapp.data.datasource;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import markosolutions.com.restaurantsapp.Util;
import markosolutions.com.restaurantsapp.data.api.RestAPI;
import markosolutions.com.restaurantsapp.data.datasource.realm.GoogleGooglePlaceRealmCache;
import markosolutions.com.restaurantsapp.data.entity.GooglePlaceDetailsEntity;
import markosolutions.com.restaurantsapp.data.entity.GooglePlaceEntity;
import markosolutions.com.restaurantsapp.data.entity.ResponseDetailsEntity;
import markosolutions.com.restaurantsapp.data.entity.ResponseEntity;

public class GooglePlaceStore {

    private RestAPI mRestAPI;
    private GooglePlaceCache mGooglePlaceCache;

    private Consumer<ArrayList<GooglePlaceDetailsEntity>> mCacheConsumer = new Consumer<ArrayList<GooglePlaceDetailsEntity>>() {
        @Override
        public void accept(ArrayList<GooglePlaceDetailsEntity> placeRestaurantEntities) throws Exception {
            mGooglePlaceCache.clear();
            mGooglePlaceCache.put(placeRestaurantEntities);
        }
    };

    public GooglePlaceStore() {
        mRestAPI = new RestAPI();
        mGooglePlaceCache = new GoogleGooglePlaceRealmCache();
    }

    public Observable<ArrayList<GooglePlaceDetailsEntity>> getNearbyRestaurants
                                                      (final double latitude,
                                                       final double longitude,
                                                       String keyword,
                                                       int radius,
                                                       String placeType) {

        return mRestAPI.getNearbyRestaurants(latitude, longitude, keyword, radius, placeType)
                .flatMap(new Function<ResponseEntity, Observable<ArrayList<GooglePlaceDetailsEntity>>>() {
                    @Override
                    public Observable<ArrayList<GooglePlaceDetailsEntity>> apply(ResponseEntity responseEntity)
                            throws Exception {

                        ArrayList<Observable<ResponseDetailsEntity>> observables = new ArrayList<>(0);
                        for (GooglePlaceEntity googlePlaceEntity : responseEntity.getPlaceRestaurantEntities()) {
                            Observable<ResponseDetailsEntity> observable = mRestAPI.getRestaurantDetails(googlePlaceEntity.getPlaceId());
                            observables.add(observable);
                        }

                        return Observable.zip(observables, new Function<Object[], ArrayList<GooglePlaceDetailsEntity>>() {
                            @Override
                            public ArrayList<GooglePlaceDetailsEntity> apply(Object[] objects) throws Exception {
                                ArrayList<GooglePlaceDetailsEntity> placeRestaurantDetailsEntities = new ArrayList<>(objects.length);
                                for (Object o : objects) {
                                    if (o instanceof ResponseDetailsEntity) {
                                        ResponseDetailsEntity responseDetailsEntity = (ResponseDetailsEntity)o;
                                        GooglePlaceDetailsEntity googlePlaceDetailsEntity = responseDetailsEntity.getGooglePlaceDetailsEntity();
                                        double placeRestaurantLatitude = googlePlaceDetailsEntity.getGeometryEntity().getLocationEntity().getLatitude();
                                        double placeRestaurantLongitude = googlePlaceDetailsEntity.getGeometryEntity().getLocationEntity().getLongitude();
                                        double distance = Util.distance(latitude, longitude, placeRestaurantLatitude, placeRestaurantLongitude);
                                        googlePlaceDetailsEntity.setDistance(distance);
                                        googlePlaceDetailsEntity.setNumberOfReviews(googlePlaceDetailsEntity.getReviewEntities().size());
                                        placeRestaurantDetailsEntities.add(googlePlaceDetailsEntity);
                                    }
                                }

                                return placeRestaurantDetailsEntities;
                            }
                        });
                    }
                }).doOnNext(mCacheConsumer);
    }

    public GooglePlaceDetailsEntity getRestaurantDetails(String placeId) {
        return mGooglePlaceCache.getPlaceRestaurantDetailsEntity(placeId);
    }

    public List<GooglePlaceDetailsEntity> getSortedPlaceRestaurantEntities
            (@GooglePlaceCache.SortCriteria int sortCriteria) {
        return mGooglePlaceCache.getSortedPlaceRestaurantEntities(sortCriteria);
    }
}
