package markosolutions.com.restaurantsapp.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class OpeningHoursEntity {

    @SerializedName("open_now")
    private boolean mIsOpenNow;

    @SerializedName("weekday_text")
    private ArrayList<String> mWeekdayText;

    public boolean isOpenNow() {
        return mIsOpenNow;
    }

    public ArrayList<String> getWeekdayText() {
        return mWeekdayText;
    }
}
