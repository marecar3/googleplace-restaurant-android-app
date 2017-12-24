package com.markosolutions.googleplace.restaurant.data.datasource.realm;


import com.markosolutions.googleplace.restaurant.data.datasource.GooglePlaceCache;
import com.markosolutions.googleplace.restaurant.data.entity.GooglePlaceDetailsEntity;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class GooglePlaceRealmCache implements GooglePlaceCache {

    @Override
    public void put(ArrayList<GooglePlaceDetailsEntity> placeRestaurantDetailsEntities) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealm(placeRestaurantDetailsEntities);
        realm.commitTransaction();
        realm.close();
    }

    @Override
    public List<GooglePlaceDetailsEntity> getSortedPlaceRestaurantEntities(@SortCriteria int sortCriteria) {
        RealmResults<GooglePlaceDetailsEntity> placeRestaurantDetailsEntities =
                getPlaceRestaurantDetailsEntity();

        switch (sortCriteria) {
            case SortCriteria.BEST_MATCH :
                return placeRestaurantDetailsEntities;
            case SortCriteria.DISTANCE:
                return placeRestaurantDetailsEntities.sort("mDistance", Sort.ASCENDING);
            case SortCriteria.MOST_REVIEWED:
                return placeRestaurantDetailsEntities.sort("mNumberOfReviews", Sort.DESCENDING);
            default:
                return placeRestaurantDetailsEntities;
        }
    }


    @Override
    public GooglePlaceDetailsEntity getPlaceRestaurantDetailsEntity(String placeId) {
        Realm realm = Realm.getDefaultInstance();
        GooglePlaceDetailsEntity googlePlaceDetailsEntity = realm.where(GooglePlaceDetailsEntity.class)
                .equalTo("mPlaceId", placeId)
                .findFirst();
        return googlePlaceDetailsEntity;
    }

    @Override
    public void clear() {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<GooglePlaceDetailsEntity> placeRestaurantDetailsEntities =
                realm.where(GooglePlaceDetailsEntity.class).findAll();
        realm.beginTransaction();
        placeRestaurantDetailsEntities.deleteAllFromRealm();
        realm.commitTransaction();
        realm.close();
    }

    private RealmResults<GooglePlaceDetailsEntity> getPlaceRestaurantDetailsEntity() {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<GooglePlaceDetailsEntity> placeRestaurantDetailsEntities =
                realm.where(GooglePlaceDetailsEntity.class).findAll();
        return placeRestaurantDetailsEntities;
    }
}
