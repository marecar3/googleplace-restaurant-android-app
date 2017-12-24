package com.markosolutions.googleplace.restaurant.data.datasource;

import com.markosolutions.googleplace.restaurant.data.api.RestAPI;
import com.markosolutions.googleplace.restaurant.data.datasource.realm.GooglePlaceRealmCache;
import com.markosolutions.googleplace.restaurant.data.entity.GooglePlaceDetailsEntity;
import com.markosolutions.googleplace.restaurant.data.entity.GooglePlaceEntity;
import com.markosolutions.googleplace.restaurant.data.entity.ResponseDetailsEntity;
import com.markosolutions.googleplace.restaurant.data.entity.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import markosolutions.com.restaurant.Util;

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
        mGooglePlaceCache = new GooglePlaceRealmCache();
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

                                        double placeRestaurantLatitude = googlePlaceDetailsEntity.getGeometryEntity() != null
                                                && googlePlaceDetailsEntity.getGeometryEntity().getLocationEntity() != null ?
                                                googlePlaceDetailsEntity.getGeometryEntity().getLocationEntity().getLatitude() : 0;

                                        double placeRestaurantLongitude = googlePlaceDetailsEntity.getGeometryEntity() != null
                                                && googlePlaceDetailsEntity.getGeometryEntity().getLocationEntity() != null ?
                                                googlePlaceDetailsEntity.getGeometryEntity().getLocationEntity().getLongitude() : 0;

                                        if (placeRestaurantLatitude != 0 && placeRestaurantLongitude != 0) {
                                            double distance = Util.distance(latitude, longitude, placeRestaurantLatitude, placeRestaurantLongitude);
                                            googlePlaceDetailsEntity.setDistance(distance);
                                        }

                                        int numberOfReviews = googlePlaceDetailsEntity.getReviewEntities() != null ? googlePlaceDetailsEntity.getReviewEntities().size() : 0;
                                        googlePlaceDetailsEntity.setNumberOfReviews(numberOfReviews);
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
