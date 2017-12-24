package markosolutions.com.restaurantsapp.application;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import markosolutions.com.restaurantsapp.data.datasource.realm.PlaceRestaurantRealmMigration;

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
                .migration(new PlaceRestaurantRealmMigration()).build();
        Realm.setDefaultConfiguration(config);
    }
}