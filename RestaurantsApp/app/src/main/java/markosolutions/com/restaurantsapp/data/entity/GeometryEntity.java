package markosolutions.com.restaurantsapp.data.entity;

import com.google.gson.annotations.SerializedName;

public class GeometryEntity {

    @SerializedName("location")
    private LocationEntity mLocationEntity;

    public LocationEntity getLocationEntity() {
        return mLocationEntity;
    }
}
