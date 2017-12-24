package com.markosolutions.googleplace.restaurant.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;

public class OpeningHoursEntity extends RealmObject {

    @SerializedName("open_now")
    private boolean mIsOpenNow;

    @SerializedName("weekday_text")
    private RealmList<String> mWeekdayText;

    public boolean isOpenNow() {
        return mIsOpenNow;
    }

    public List<String> getWeekdayText() {
        return mWeekdayText;
    }
}
