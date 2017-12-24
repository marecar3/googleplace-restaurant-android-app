package com.markosolutions.googleplace.restaurant;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.markosolutions.googleplace.restaurant.data.entity.GooglePlaceDetailsEntity;
import com.markosolutions.googleplace.restaurant.domain.UseCase;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import markosolutions.com.restaurant.R;

public class HomeActivity extends AppCompatActivity {

    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    @Override
    protected void onResume() {
        super.onResume();
        final UseCase useCase = new UseCase();

        Observable<ArrayList<GooglePlaceDetailsEntity>> observable =
                useCase.getNearbyRestaurants(-33.8670522,151.1957362, "pizza")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

            observable.subscribeWith(new Observer<ArrayList<GooglePlaceDetailsEntity>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(ArrayList<GooglePlaceDetailsEntity> placeRestaurantDetailsEntities) {
                for (GooglePlaceDetailsEntity googlePlaceDetailsEntity : placeRestaurantDetailsEntities) {
                    Log.e("ivasavic", "count " + googlePlaceDetailsEntity.getDistance());
                }

                List<GooglePlaceDetailsEntity> list =
                        useCase.sortNearbyRestaurantsByDistance();
                for (GooglePlaceDetailsEntity googlePlaceDetailsEntity : list) {
                    Log.e("ivasavic", "count " + googlePlaceDetailsEntity.getDistance());
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

        //ChIJcSImzzCuEmsRcewmeMbMSz8

//        Observable<ResponseDetailsEntity> observable =
//                restAPI.getRestaurantDetails("ChIJcSImzzCuEmsRcewmeMbMSz8")
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread());
//
//        observable.subscribeWith(new Observer<ResponseDetailsEntity>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//
//            }
//
//            @Override
//            public void onNext(ResponseDetailsEntity restaurantsResponseEntity) {
//                Log.e("ivasavic", "count : " + restaurantsResponseEntity.getPlaceRestaurantEntity().getOpeningHoursEntity().getWeekdayText());
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//
//            @Override
//            public void onComplete() {
//
//            }
//        });


//        List<Observable<Integer>> observables =
//                new ArrayList<>();
//        observables.add(Observable.fromArray(1, 2, 3));
//        observables.add(Observable.fromArray(1, 2, 3));
//
//        Observable<Object> observable = Observable.zip(observables, new Function<Object[], Object>() {
//            @Override
//            public Object apply(Object[] o) throws Exception {
//                return null;
//            }
//        }).subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread());
//
//        observable.subscribeWith(new Observer<Object>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//
//            }
//
//            @Override
//            public void onNext(Object restaurantsResponseEntity) {
//                Log.e("ivasavic", "count : " + restaurantsResponseEntity);
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//
//            @Override
//            public void onComplete() {
//
//            }
//        });

    }
}
