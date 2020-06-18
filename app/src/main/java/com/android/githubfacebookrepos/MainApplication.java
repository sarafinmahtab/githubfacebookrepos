package com.android.githubfacebookrepos;

/*
 * Created by Arafin Mahtab on 6/15/20.
 */


import android.app.Application;
import android.content.Context;

import androidx.annotation.UiThread;
import androidx.multidex.MultiDex;

import com.android.githubfacebookrepos.data.AppConstant;
import com.android.githubfacebookrepos.di.AppComponent;
import com.android.githubfacebookrepos.di.DaggerAppComponent;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MainApplication extends Application {

    private AppComponent appComponent;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .schemaVersion(AppConstant.DATA_BASE_VERSION)
                .name(AppConstant.DATA_BASE_NAME)
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);
    }

    @UiThread
    public AppComponent getAppComponent() {

        if (appComponent == null) {
            appComponent = DaggerAppComponent.factory()
                    .create(getApplicationContext());
        }

        return appComponent;
    }
}
