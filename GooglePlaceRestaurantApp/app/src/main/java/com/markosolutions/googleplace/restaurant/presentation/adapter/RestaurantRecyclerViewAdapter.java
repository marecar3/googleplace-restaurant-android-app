package com.markosolutions.googleplace.restaurant.presentation.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.markosolutions.googleplace.restaurant.data.entity.GooglePlaceDetailsEntity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import markosolutions.com.restaurant.BuildConfig;
import markosolutions.com.restaurant.R;


public class RestaurantRecyclerViewAdapter extends RecyclerView.Adapter<RestaurantRecyclerViewAdapter.RestaurantViewHolder> {

    private Context mContext;
    private ArrayList<GooglePlaceDetailsEntity> mGooglePlaceEntities = new ArrayList<>(0);

    public RestaurantRecyclerViewAdapter(Context context) {
        mContext = context;
    }

    @Override
    public RestaurantViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.restaurant_recyclerview_item, parent, false);
        RestaurantViewHolder restaurantViewHolder = new RestaurantViewHolder(view);
        return restaurantViewHolder;
    }

    @Override
    public void onBindViewHolder(RestaurantViewHolder holder, int position) {
        GooglePlaceDetailsEntity googlePlaceDetailsEntity = mGooglePlaceEntities.get(position);
        holder.bindView(googlePlaceDetailsEntity);
    }

    @Override
    public int getItemCount() {
        return mGooglePlaceEntities.size();
    }

    public class RestaurantViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.name)
        TextView mName;

        @BindView(R.id.icon)
        ImageView mIcon;

        @BindView(R.id.distance)
        TextView mDistance;

        @BindView(R.id.address)
        TextView mAddress;

        @BindView(R.id.rating)
        TextView mRating;

        @BindView(R.id.review)
        TextView mReview;

        public RestaurantViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bindView(GooglePlaceDetailsEntity googlePlaceDetailsEntity) {
            mName.setText(googlePlaceDetailsEntity.getName());
            String distance = String.format("%.2f", googlePlaceDetailsEntity.getDistance());
            mDistance.setText(distance
                    + " "
                    + mContext.getResources().getString(R.string.distance_kilometers));
            mAddress.setText(googlePlaceDetailsEntity.getFormattedAddress());
            mRating.setText(String.valueOf(googlePlaceDetailsEntity.getRating()));
            mReview.setText(String.valueOf(googlePlaceDetailsEntity.getNumberOfReviews()));

            String url =  googlePlaceDetailsEntity.getPhotoEntities() != null &&
                    googlePlaceDetailsEntity.getPhotoEntities().size() > 0 ?
            String.format(BuildConfig.IMAGE_URL, googlePlaceDetailsEntity.getPhotoEntities().get(0).getPhotoReference()) : googlePlaceDetailsEntity.getIcon();
            Picasso.with(mContext).load(url).centerCrop().fit().into(mIcon);
        }
    }

    public void addAll(List<GooglePlaceDetailsEntity> restaurantList) {
        mGooglePlaceEntities.clear();
        mGooglePlaceEntities.addAll(restaurantList);
    }

}
