package markosolutions.com.restaurantsapp.data.entity;

import com.google.gson.annotations.SerializedName;

public class PhotoEntity {

    @SerializedName("height")
    private int mHeight;

    @SerializedName("width")
    private int mWidth;

    @SerializedName("photo_reference")
    private String mPhotoReference;

    public int getHeight() {
        return mHeight;
    }

    public int getWidth() {
        return mWidth;
    }

    public String getPhotoReference() {
        return mPhotoReference;
    }
}
