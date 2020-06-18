package com.android.githubfacebookrepos.di;

/*
 * Created by Arafin Mahtab on 6/18/20.
 */

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;

@Module
public class DatabaseModule {

    @AppScope
    @Provides
    public Realm providesRealm() {
        return Realm.getDefaultInstance();
    }
}
