package markosolutions.com.restaurantsapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import markosolutions.com.restaurantsapp.data.api.RestaurantService;
import markosolutions.com.restaurantsapp.data.api.ServiceGenerator;
import markosolutions.com.restaurantsapp.data.entity.RestaurantsResponseEntity;


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
        RestaurantService restaurantService = ServiceGenerator.createService(RestaurantService.class);

        Observable<RestaurantsResponseEntity> observable = restaurantService.
                getNearbyRestaurants("-33.8670522,151.1957362", 500, "restaurant")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

            observable.subscribeWith(new Observer<RestaurantsResponseEntity>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(RestaurantsResponseEntity restaurantsResponseEntity) {
                Log.e("ivasavic", "count : " + restaurantsResponseEntity.getPlaceRestaurantEntities().size());
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

    }
}
