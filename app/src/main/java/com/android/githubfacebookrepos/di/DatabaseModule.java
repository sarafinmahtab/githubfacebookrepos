package com.android.githubfacebookrepos.di;

/*
 * Created by Arafin Mahtab on 6/18/20.
 */

import android.content.Context;

import com.android.githubfacebookrepos.data.AppConstant;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;
import io.realm.RealmConfiguration;

@Module
public class DatabaseModule {

    @AppScope
    @Provides
    public Realm providesRealm(Context context) {
        Realm.init(context);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .schemaVersion(AppConstant.DATA_BASE_VERSION)
                .name(AppConstant.DATA_BASE_NAME)
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);

        return Realm.getDefaultInstance();
    }
}
