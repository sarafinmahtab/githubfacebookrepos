package com.android.githubfacebookrepos.di;

/*
 * Created by Arafin Mahtab on 6/18/20.
 */

import com.android.githubfacebookrepos.dal.db.LocalDataSource;
import com.android.githubfacebookrepos.dal.db.LocalDataSourceImpl;
import com.android.githubfacebookrepos.dal.network.RemoteDataSource;
import com.android.githubfacebookrepos.dal.network.RemoteDataSourceImpl;

import dagger.Binds;
import dagger.Module;

// Tells Dagger this is a Dagger module
@Module
public abstract class AppModule {

    // Makes Dagger provide RemoteDataSourceImpl when a RemoteDataSource type is requested
    @Binds
    public abstract RemoteDataSource provideRemoteDataSourceImpl(RemoteDataSourceImpl remoteDataSourceImpl);


    // Makes Dagger provide LocalDataSourceImpl when a LocalDataSource type is requested
    @Binds
    public abstract LocalDataSource provideLocalDataSourceImpl(LocalDataSourceImpl localDataSourceImpl);
}
