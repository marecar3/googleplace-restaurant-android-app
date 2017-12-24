package markosolutions.com.restaurantsapp.data.datasource;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import markosolutions.com.restaurantsapp.Util;
import markosolutions.com.restaurantsapp.data.api.RestAPI;
import markosolutions.com.restaurantsapp.data.datasource.realm.PlaceRestaurantRealmCache;
import markosolutions.com.restaurantsapp.data.entity.PlaceRestaurantDetailsEntity;
import markosolutions.com.restaurantsapp.data.entity.PlaceRestaurantEntity;
import markosolutions.com.restaurantsapp.data.entity.RestaurantDetailsResponseEntity;
import markosolutions.com.restaurantsapp.data.entity.RestaurantsResponseEntity;

public class PlaceRestaurantStore {

    private RestAPI mRestAPI;
    private PlaceRestaurantCache mPlaceRestaurantCache;

    private Consumer<ArrayList<PlaceRestaurantDetailsEntity>> mCacheConsumer = new Consumer<ArrayList<PlaceRestaurantDetailsEntity>>() {
        @Override
        public void accept(ArrayList<PlaceRestaurantDetailsEntity> placeRestaurantEntities) throws Exception {
            mPlaceRestaurantCache.clear();
            mPlaceRestaurantCache.put(placeRestaurantEntities);
        }
    };

    public PlaceRestaurantStore() {
        mRestAPI = new RestAPI();
        mPlaceRestaurantCache = new PlaceRestaurantRealmCache();
    }

    public Observable<ArrayList<PlaceRestaurantDetailsEntity>> getNearbyRestaurants
                                                      (final double latitude,
                                                       final double longitude,
                                                       String keyword,
                                                       int radius,
                                                       String placeType) {

        return mRestAPI.getNearbyRestaurants(latitude, longitude, keyword, radius, placeType)
                .flatMap(new Function<RestaurantsResponseEntity, Observable<ArrayList<PlaceRestaurantDetailsEntity>>>() {
                    @Override
                    public Observable<ArrayList<PlaceRestaurantDetailsEntity>> apply(RestaurantsResponseEntity restaurantsResponseEntity)
                            throws Exception {

                        ArrayList<Observable<RestaurantDetailsResponseEntity>> observables = new ArrayList<>(0);
                        for (PlaceRestaurantEntity placeRestaurantEntity : restaurantsResponseEntity.getPlaceRestaurantEntities()) {
                            Observable<RestaurantDetailsResponseEntity> observable = mRestAPI.getRestaurantDetails(placeRestaurantEntity.getPlaceId());
                            observables.add(observable);
                        }

                        return Observable.zip(observables, new Function<Object[], ArrayList<PlaceRestaurantDetailsEntity>>() {
                            @Override
                            public ArrayList<PlaceRestaurantDetailsEntity> apply(Object[] objects) throws Exception {
                                ArrayList<PlaceRestaurantDetailsEntity> placeRestaurantDetailsEntities = new ArrayList<>(objects.length);
                                for (Object o : objects) {
                                    if (o instanceof RestaurantDetailsResponseEntity) {
                                        RestaurantDetailsResponseEntity restaurantDetailsResponseEntity = (RestaurantDetailsResponseEntity)o;
                                        PlaceRestaurantDetailsEntity placeRestaurantDetailsEntity = restaurantDetailsResponseEntity.getPlaceRestaurantDetailsEntity();
                                        double placeRestaurantLatitude = placeRestaurantDetailsEntity.getGeometryEntity().getLocationEntity().getLatitude();
                                        double placeRestaurantLongitude = placeRestaurantDetailsEntity.getGeometryEntity().getLocationEntity().getLongitude();
                                        double distance = Util.distance(latitude, longitude, placeRestaurantLatitude, placeRestaurantLongitude);
                                        placeRestaurantDetailsEntity.setDistance(distance);
                                        placeRestaurantDetailsEntity.setNumberOfReviews(placeRestaurantDetailsEntity.getReviewEntities().size());
                                        placeRestaurantDetailsEntities.add(placeRestaurantDetailsEntity);
                                    }
                                }

                                return placeRestaurantDetailsEntities;
                            }
                        });
                    }
                }).doOnNext(mCacheConsumer);
    }

    public PlaceRestaurantDetailsEntity getRestaurantDetails(String placeId) {
        return mPlaceRestaurantCache.getPlaceRestaurantDetailsEntity(placeId);
    }

    public List<PlaceRestaurantDetailsEntity> getSortedPlaceRestaurantEntities
            (@PlaceRestaurantCache.SortCriteria int sortCriteria) {
        return mPlaceRestaurantCache.getSortedPlaceRestaurantEntities(sortCriteria);
    }
}
