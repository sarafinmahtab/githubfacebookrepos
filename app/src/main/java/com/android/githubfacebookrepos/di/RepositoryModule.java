package com.android.githubfacebookrepos.di;

/*
 * Created by Arafin Mahtab on 6/16/20.
 */

import com.android.githubfacebookrepos.dal.repos.MainRepo;
import com.android.githubfacebookrepos.dal.repos.MainRepoImpl;

import dagger.Binds;
import dagger.Module;


@Module
public abstract class RepositoryModule {

    @AppScope
    @Binds
    public abstract MainRepo provideMainRepoImpl(MainRepoImpl mainRepo);
}
