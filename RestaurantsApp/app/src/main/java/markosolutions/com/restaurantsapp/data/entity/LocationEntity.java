package markosolutions.com.restaurantsapp.data.entity;


import com.google.gson.annotations.SerializedName;

public class LocationEntity {

    @SerializedName("lat")
    private double mLatitude;

    @SerializedName("lng")
    private double mLongitude;

    public double getLatitude() {
        return mLatitude;
    }

    public double getLongitude() {
        return mLongitude;
    }
}
