package com.markosolutions.googleplace.restaurant.presentation.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

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
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import markosolutions.com.restaurant.R;

public class HomeActivity extends AppCompatActivity implements RestaurantListView, RestaurantRecyclerViewAdapter.OnItemClickListener {

    private static final String TAG = "HomeActivity";

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

    private LatLng mLastLatLng;

    @BindView(R.id.search_view)
    MaterialSearchView mMaterialSearchView;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

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
        setSupportActionBar(mToolbar);
        init();
    }

    private void init() {
        mLastLatLng = new LatLng(NEW_YORK_LATITUDE, NEW_YORK_LONGITUDE);
        mRestaurantListPresenter = new RestaurantListPresenter();
        addSearchViewListener();
        initRecycler();
    }

    private void addSearchViewListener() {
        mMaterialSearchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mMaterialSearchView.closeSearch();
                mRestaurantListPresenter.getRestaurantList(mLastLatLng.latitude, mLastLatLng.longitude, query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Do some magic
                return false;
            }
        });

        mMaterialSearchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                //Do some magic
            }

            @Override
            public void onSearchViewClosed() {
                //Do some magic
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
    }

    @Override
    protected void onPause() {
        mRestaurantListPresenter.detach();
        mRestaurantRecyclerViewAdapter.setOnItemClickListener(null);
        super.onPause();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SELECT_PLACE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                mLastLatLng = place.getLatLng();
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                Log.e(TAG, "error getting location " + status);
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

    @Override
    public void onItemClicked(String placeId) {
        openDetails(placeId);
    }

    private void openDetails(String placeId) {
        Intent intent = new Intent(this, RestaurantDetailsActivity.class);
        intent.putExtra(RestaurantDetailsActivity.GOOGLE_PLACE_ID_KEY, placeId);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        mMaterialSearchView.setMenuItem(menu.findItem(R.id.action_search));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_location :
                openAutocompleteModal();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
