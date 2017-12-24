package com.markosolutions.googleplace.restaurant.presentation.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.model.LatLng;
import com.markosolutions.googleplace.restaurant.data.entity.GooglePlaceDetailsEntity;
import com.markosolutions.googleplace.restaurant.presentation.adapter.DividerItemDecoration;
import com.markosolutions.googleplace.restaurant.presentation.adapter.RestaurantRecyclerViewAdapter;
import com.markosolutions.googleplace.restaurant.presentation.presenter.RestaurantListPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import markosolutions.com.restaurant.R;

public class HomeActivity extends AppCompatActivity implements RestaurantListView, RestaurantRecyclerViewAdapter.OnItemClickListener {

    private static final int REQUEST_SELECT_PLACE = 0x100;

    private static final double NEW_YORK_LATITUDE = 40.712775;
    private static final double NEW_YORK_LONGITUDE = -74.0059732;

    @BindView(R.id.restaurant_recycler_view)
    RecyclerView mRestaurantRecyclerView;

    private RestaurantRecyclerViewAdapter mRestaurantRecyclerViewAdapter;
    private RestaurantListPresenter mRestaurantListPresenter;

    @OnClick(R.id.sort_distance)
    public void onSortDistanceClicked() {
        mRestaurantListPresenter.sortNearbyRestaurantsByDistance();
    }

    @OnClick(R.id.sort_review)
    public void onSortReviewClicked() {
        mRestaurantListPresenter.sortNearbyRestaurantsByMostReviewed();
    }

    @OnClick(R.id.sort_best_match)
    public void onSortBestMatchClicked() {
        mRestaurantListPresenter.sortNearbyRestaurantsByBestMatch();
    }

    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;

    @BindView(R.id.search_field)
    EditText mSearchField;

    private LatLng mLastLatLng;

    @OnClick(R.id.location_button)
    public void onLocationButtonClicked() {
        openAutocompleteModal();
    }

    private void openAutocompleteModal() {
        try {
            PlaceAutocomplete.IntentBuilder intentBuilder = new PlaceAutocomplete.IntentBuilder
                    (PlaceAutocomplete.MODE_OVERLAY);
            Intent intent = intentBuilder.build(this);
            startActivityForResult(intent, REQUEST_SELECT_PLACE);
        } catch (GooglePlayServicesRepairableException |
                GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        mRestaurantListPresenter = new RestaurantListPresenter();
        listenKeyboardButton();
        initRecycler();
    }

    private void listenKeyboardButton() {
        mSearchField.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    hideKeyboard();
                    mRestaurantListPresenter.getRestaurantList(NEW_YORK_LATITUDE, NEW_YORK_LONGITUDE, v.getText().toString());
                    return true;
                }
                return false;
            }
        });
    }

    private void initRecycler() {
        mRestaurantRecyclerViewAdapter = new RestaurantRecyclerViewAdapter(getApplicationContext());
        mRestaurantRecyclerView.setAdapter(mRestaurantRecyclerViewAdapter);
        mRestaurantRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRestaurantRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
    }

    @Override
    protected void onResume() {
        super.onResume();
        mRestaurantListPresenter.attach(this);
        mRestaurantRecyclerViewAdapter.setOnItemClickListener(this);
        executeSearch();
    }

    private void executeSearch() {
        if (mLastLatLng == null) return;
        String query = mSearchField.getText().toString();

        mRestaurantListPresenter.getRestaurantList
                (mLastLatLng.latitude,
                mLastLatLng.longitude,
                query);
    }

    @Override
    protected void onPause() {
        mRestaurantListPresenter.detach();
        mRestaurantRecyclerViewAdapter.setOnItemClickListener(null);
        mLastLatLng = null;
        super.onPause();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SELECT_PLACE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                mLastLatLng = place.getLatLng();
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRestaurantListReceived(List<GooglePlaceDetailsEntity> restaurantList) {
        mRestaurantRecyclerViewAdapter.addAll(restaurantList);
        mRestaurantRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoadingStarted() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onLoadingFinished() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onError() {
        mProgressBar.setVisibility(View.GONE);
    }

    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void onItemClicked(String placeId) {
        openDetails(placeId);
    }

    private void openDetails(String placeId) {
        Intent intent = new Intent(this, RestaurantDetailsActivity.class);
        intent.putExtra(RestaurantDetailsActivity.GOOGLE_PLACE_ID_KEY, placeId);
        startActivity(intent);
    }
}
