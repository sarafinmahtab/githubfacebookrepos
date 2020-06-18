package com.android.githubfacebookrepos.dal.db;

/*
 * Created by Arafin Mahtab on 6/18/20.
 */

import com.android.githubfacebookrepos.dal.network.ApiService;

import javax.inject.Inject;

public class LocalDataSourceImpl implements LocalDataSource {

    private final String TAG = this.getClass().getName();

    private ApiService apiService;

    private LocalDataSourceImpl() {

    }

    /**
     * Inject tells Dagger how to provide instances of this type
     *
     * @param apiService This instance will be provided by dagger
     */
    @Inject
    public LocalDataSourceImpl(ApiService apiService) {
        this.apiService = apiService;
    }
}
