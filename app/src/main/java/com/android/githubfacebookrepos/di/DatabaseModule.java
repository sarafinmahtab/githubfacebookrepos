package com.android.githubfacebookrepos.di;

/*
 * Created by Arafin Mahtab on 6/18/20.
 */

import dagger.Module;

@Module
public class DatabaseModule {

    //TODO: Fix Realm Initialize with Dagger
    /*
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
    */
}
