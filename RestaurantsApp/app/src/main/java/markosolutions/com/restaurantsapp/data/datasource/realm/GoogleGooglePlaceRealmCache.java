package markosolutions.com.restaurantsapp.data.datasource.realm;


import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;
import markosolutions.com.restaurantsapp.data.datasource.GooglePlaceCache;
import markosolutions.com.restaurantsapp.data.entity.GooglePlaceDetailsEntity;

public class GoogleGooglePlaceRealmCache implements GooglePlaceCache {

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
                return placeRestaurantDetailsEntities.sort("mDistance", Sort.DESCENDING);
            case SortCriteria.MOST_REVIEWED:
                return placeRestaurantDetailsEntities.sort("mNumberOfReviews", Sort.DESCENDING);
            default:
                return placeRestaurantDetailsEntities;
        }
    }


    @Override
    public GooglePlaceDetailsEntity getPlaceRestaurantDetailsEntity(String placeId) {
        Realm realm = Realm.getDefaultInstance();
        realm.where(GooglePlaceDetailsEntity.class).equalTo("place_id", placeId);
        return null;
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
