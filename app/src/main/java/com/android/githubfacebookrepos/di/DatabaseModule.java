package com.android.githubfacebookrepos.di;

/*
 * Created by Arafin Mahtab on 6/18/20.
 */

import com.android.githubfacebookrepos.dal.db.RealmService;

import dagger.Module;
import dagger.Provides;

@Module
public class DatabaseModule {

    /*
    @AppScope
    @Provides
    public Realm providesRealm() {
        return Realm.getDefaultInstance();
    }
    */

    @AppScope
    @Provides
    public RealmService providesRealmService() {
        return new RealmService();
    }
}
