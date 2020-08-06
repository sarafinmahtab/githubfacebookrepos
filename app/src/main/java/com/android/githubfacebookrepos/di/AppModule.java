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


@Module
public abstract class AppModule {

    @AppScope
    @Binds
    public abstract RemoteDataSource providesRemoteDataSource(RemoteDataSourceImpl remoteDataSourceImpl);


    @AppScope
    @Binds
    public abstract LocalDataSource providesLocalDataSource(LocalDataSourceImpl localDataSourceImpl);
}
