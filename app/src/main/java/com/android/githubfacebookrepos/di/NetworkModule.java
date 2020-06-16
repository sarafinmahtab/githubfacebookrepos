package com.android.githubfacebookrepos.di;

/*
 * Created by Arafin Mahtab on 6/16/20.
 */

import com.android.githubfacebookrepos.BuildConfig;
import com.android.githubfacebookrepos.dal.network.ApiService;
import com.android.githubfacebookrepos.data.ServerConstant;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {

    @AppScope
    @Provides
    public GsonConverterFactory provideGsonConverterFactory() {
        Gson gson = new GsonBuilder()
                .disableHtmlEscaping()
//                .setDateFormat(DateUtils.SERVER_API_DATE_FORMAT)
                .create();
        return GsonConverterFactory.create(gson);
    }

    @AppScope
    @Provides
    public OkHttpClient.Builder provideOkHttpClientBuilder() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        if (BuildConfig.DEBUG) {
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        } else {
            interceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
        }

        return new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(3, TimeUnit.MINUTES)
                .writeTimeout(10, TimeUnit.MINUTES);
    }

    @AppScope
    @Provides
    public Retrofit provideRetrofit(
            OkHttpClient.Builder okHttpClientBuilder,
            GsonConverterFactory gsonConverterFactory
    ) {
        return new Retrofit.Builder()
                .baseUrl(ServerConstant.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(gsonConverterFactory)
                .client(okHttpClientBuilder.build())
                .build();
    }

    @AppScope
    @Provides
    public ApiService provideApiService(Retrofit retrofit) {
        return retrofit.create(ApiService.class);
    }
}
