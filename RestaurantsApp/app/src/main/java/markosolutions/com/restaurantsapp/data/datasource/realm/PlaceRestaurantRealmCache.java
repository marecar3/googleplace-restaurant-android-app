package markosolutions.com.restaurantsapp.data.datasource.realm;


import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;
import markosolutions.com.restaurantsapp.data.datasource.PlaceRestaurantCache;
import markosolutions.com.restaurantsapp.data.entity.PlaceRestaurantDetailsEntity;

public class PlaceRestaurantRealmCache implements PlaceRestaurantCache {

    @Override
    public void put(ArrayList<PlaceRestaurantDetailsEntity> placeRestaurantDetailsEntities) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.copyToRealm(placeRestaurantDetailsEntities);
        realm.commitTransaction();
        realm.close();
    }

    @Override
    public List<PlaceRestaurantDetailsEntity> getSortedPlaceRestaurantEntities(@SortCriteria int sortCriteria) {
        RealmResults<PlaceRestaurantDetailsEntity> placeRestaurantDetailsEntities =
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
    public PlaceRestaurantDetailsEntity getPlaceRestaurantDetailsEntity(String placeId) {
        Realm realm = Realm.getDefaultInstance();
        realm.where(PlaceRestaurantDetailsEntity.class).equalTo("place_id", placeId);
        return null;
    }

    @Override
    public void clear() {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<PlaceRestaurantDetailsEntity> placeRestaurantDetailsEntities =
                realm.where(PlaceRestaurantDetailsEntity.class).findAll();
        realm.beginTransaction();
        placeRestaurantDetailsEntities.deleteAllFromRealm();
        realm.commitTransaction();
        realm.close();
    }

    private RealmResults<PlaceRestaurantDetailsEntity> getPlaceRestaurantDetailsEntity() {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<PlaceRestaurantDetailsEntity> placeRestaurantDetailsEntities =
                realm.where(PlaceRestaurantDetailsEntity.class).findAll();
        return placeRestaurantDetailsEntities;
    }
}
