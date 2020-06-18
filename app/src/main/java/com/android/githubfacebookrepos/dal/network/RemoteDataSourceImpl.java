package com.android.githubfacebookrepos.dal.network;

/*
 * Created by Arafin Mahtab on 6/18/20.
 */

import com.android.githubfacebookrepos.model.api.GithubRepo;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.Single;

/**
 * Data source responsible how the data will be fetched from remote
 */
public class RemoteDataSourceImpl implements RemoteDataSource {

    private final String TAG = this.getClass().getName();

    private ApiService apiService;

    private RemoteDataSourceImpl() {

    }

    /**
     * Inject tells Dagger how to provide instances of this type
     *
     * @param apiService This instance will be provided by dagger
     */
    @Inject
    public RemoteDataSourceImpl(ApiService apiService) {
        this.apiService = apiService;
    }

    @Override
    public Single<ArrayList<GithubRepo>> fetchOrganizationRepos(String orgName) {
        try {
            return apiService.fetchOrganizationRepos(orgName);
        } catch (Exception e) {
            return Single.error(e);
        }
    }
}