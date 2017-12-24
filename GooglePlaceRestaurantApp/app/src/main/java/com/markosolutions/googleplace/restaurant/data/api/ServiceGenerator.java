package com.markosolutions.googleplace.restaurant.data.api;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import markosolutions.com.restaurant.BuildConfig;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {

    private static final String TAG = "ServiceGenerator";

    private static final String GOOGLE_PLACE_API_KEY = "key";

    private static final int READ_TIMEOUT = 30;
    private static final HttpLoggingInterceptor sHttpLoggingInterceptor =
            new HttpLoggingInterceptor().setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);

    private static final Interceptor sQueryParamInterceptor = new Interceptor() {
        @Override
        public Response intercept(Interceptor.Chain chain) throws IOException {
            Request request = chain.request();
            HttpUrl url = request.url().newBuilder().
                    addQueryParameter(GOOGLE_PLACE_API_KEY, BuildConfig.GOOGLE_PLACE_API_KEY).build();
            request = request.newBuilder().url(url).build();
            return chain.proceed(request);
        }
    };

    private static OkHttpClient.Builder sHttpClientBuilder = new OkHttpClient.Builder()
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .connectTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(sHttpLoggingInterceptor)
            .addInterceptor(sQueryParamInterceptor);

    private static Retrofit.Builder sBuilder = new Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(new Gson()));

    private static OkHttpClient sOkHttpClient = sHttpClientBuilder.build();

    private static Retrofit sRetrofit = sBuilder.client(sOkHttpClient).build();

    public static  <S> S createService(Class<S> serviceClass) {
        return sRetrofit.create(serviceClass);
    }
}
