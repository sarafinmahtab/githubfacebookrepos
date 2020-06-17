package com.android.githubfacebookrepos.di;

/*
 * Created by Arafin Mahtab on 6/16/20.
 */


import com.android.githubfacebookrepos.dal.repos.MainRepo;
import com.android.githubfacebookrepos.dal.repos.MainRepoImpl;

import dagger.Binds;
import dagger.Module;

// Tells Dagger this is a Dagger module
@Module
public abstract class RepositoryModule {

    // Makes Dagger provide SharedPreferencesStorage when a Storage type is requested
    @Binds
    public abstract MainRepo provideStorage(MainRepoImpl mainRepo);
}
