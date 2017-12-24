package markosolutions.com.restaurantsapp.data.entity;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class GeometryEntity extends RealmObject {

    @SerializedName("location")
    private LocationEntity mLocationEntity;

    public LocationEntity getLocationEntity() {
        return mLocationEntity;
    }
}
