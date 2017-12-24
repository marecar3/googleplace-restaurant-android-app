package com.markosolutions.googleplace.restaurant.application;

import android.app.Application;

import com.markosolutions.googleplace.restaurant.data.datasource.realm.GooglePlaceRealmMigration;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class PlaceRestaurantApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initRealm();
    }

    private void initRealm() {
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .schemaVersion(1)
                .migration(new GooglePlaceRealmMigration()).build();
        Realm.setDefaultConfiguration(config);
    }
}
