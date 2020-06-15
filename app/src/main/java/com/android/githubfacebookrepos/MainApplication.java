package com.android.githubfacebookrepos;

/*
 * Created by Arafin Mahtab on 6/15/20.
 */


import android.app.Application;

import androidx.annotation.UiThread;

import com.android.githubfacebookrepos.di.AppComponent;
import com.android.githubfacebookrepos.di.DaggerAppComponent;

public class MainApplication extends Application {

    private AppComponent appComponent;

    @UiThread
    public AppComponent getAppComponent() {

        if (appComponent == null) {
            appComponent = DaggerAppComponent.factory()
                    .create(getApplicationContext());
        }

        return appComponent;
    }
}
