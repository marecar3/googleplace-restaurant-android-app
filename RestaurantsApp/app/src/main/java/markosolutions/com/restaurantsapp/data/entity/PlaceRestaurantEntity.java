package markosolutions.com.restaurantsapp.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PlaceRestaurantEntity {

    @SerializedName("geometry")
    private GeometryEntity mGeometryEntity;

    @SerializedName("icon")
    private String mIcon;

    @SerializedName("id")
    private String mId;

    @SerializedName("opening_hours")
    private OpeningHoursEntity mOpeningHoursEntity;

    @SerializedName("photos")
    private ArrayList<PhotoEntity> mPhotoEntities;

    @SerializedName("place_id")
    private String mPlaceId;

    @SerializedName("rating")
    private float mRating;

    @SerializedName("")

    public GeometryEntity getGeometryEntity() {
        return mGeometryEntity;
    }

}
