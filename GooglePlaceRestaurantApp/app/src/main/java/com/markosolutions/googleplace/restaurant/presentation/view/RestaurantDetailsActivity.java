package com.markosolutions.googleplace.restaurant.presentation.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.markosolutions.googleplace.restaurant.data.entity.GooglePlaceDetailsEntity;
import com.markosolutions.googleplace.restaurant.presentation.presenter.RestaurantDetailsPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import markosolutions.com.restaurant.R;

public class RestaurantDetailsActivity extends AppCompatActivity implements RestaurantDetailsView {

    public static final String GOOGLE_PLACE_ID_KEY = "GOOGLE_PLACE_ID_KEY";

    private RestaurantDetailsPresenter mRestaurantDetailsPresenter;

    @BindView(R.id.name)
    TextView mName;

    @BindView(R.id.address)
    TextView mAddress;

    @BindView(R.id.phone_number)
    TextView mPhoneNumber;

    @BindView(R.id.working_time)
    TextView mWorkingTime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_details);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        mRestaurantDetailsPresenter = new RestaurantDetailsPresenter();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mRestaurantDetailsPresenter.attach(this);
        getDetails();
    }

    private void getDetails() {
        String placeId = getIntent().getStringExtra(GOOGLE_PLACE_ID_KEY);
        mRestaurantDetailsPresenter.getGooglePlaceDetailsEntity(placeId);
    }

    @Override
    protected void onPause() {
        mRestaurantDetailsPresenter.detach();
        super.onPause();
    }

    @Override
    public void onRestaurantDetailsReceived(GooglePlaceDetailsEntity googlePlaceDetailsEntity) {
        mName.setText(googlePlaceDetailsEntity.getName());
        mAddress.setText(googlePlaceDetailsEntity.getFormattedAddress());
        mPhoneNumber.setText(googlePlaceDetailsEntity.getFormaatterPhoneNumber());
        StringBuilder stringBuilder = new StringBuilder();
        for (String time : googlePlaceDetailsEntity.getOpeningHoursEntity().getWeekdayText()) {
            stringBuilder.append(time).append("\n");
        }
        mWorkingTime.setText(stringBuilder.toString());
    }

    @Override
    public void onLoadingStarted() {

    }

    @Override
    public void onLoadingFinished() {

    }

    @Override
    public void onError() {

    }
}
