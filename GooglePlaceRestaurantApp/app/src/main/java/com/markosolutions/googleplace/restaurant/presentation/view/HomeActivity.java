package com.markosolutions.googleplace.restaurant.presentation.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.markosolutions.googleplace.restaurant.data.entity.GooglePlaceDetailsEntity;
import com.markosolutions.googleplace.restaurant.presentation.adapter.DividerItemDecoration;
import com.markosolutions.googleplace.restaurant.presentation.adapter.RestaurantRecyclerViewAdapter;
import com.markosolutions.googleplace.restaurant.presentation.presenter.RestaurantListPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import markosolutions.com.restaurant.R;

public class HomeActivity extends AppCompatActivity implements RestaurantListView {

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        mRestaurantListPresenter = new RestaurantListPresenter();
        initRecycler();
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
        mRestaurantListPresenter.getRestaurantList(-33.8670522,151.1957362, "pizza");
    }

    @Override
    protected void onPause() {
        mRestaurantListPresenter.detach();
        super.onPause();
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

    }
}
