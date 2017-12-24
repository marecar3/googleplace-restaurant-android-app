package markosolutions.com.restaurantsapp.data.datasource;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

import markosolutions.com.restaurantsapp.data.entity.PlaceRestaurantDetailsEntity;

public interface PlaceRestaurantCache {

    @IntDef({SortCriteria.BEST_MATCH, SortCriteria.DISTANCE, SortCriteria.MOST_REVIEWED})
    @Retention(RetentionPolicy.SOURCE)
    @interface SortCriteria {
        int BEST_MATCH = 0;
        int DISTANCE = 1;
        int MOST_REVIEWED = 2;
    }


    void put(ArrayList<PlaceRestaurantDetailsEntity> placeRestaurantDetailsEntities);
    void clear();
    List<PlaceRestaurantDetailsEntity> getSortedPlaceRestaurantEntities(@SortCriteria int sortCriteria);
    PlaceRestaurantDetailsEntity getPlaceRestaurantDetailsEntity(String placeId);
}
