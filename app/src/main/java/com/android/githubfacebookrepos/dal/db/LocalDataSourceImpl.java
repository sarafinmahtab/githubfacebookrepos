package com.android.githubfacebookrepos.dal.db;

/*
 * Created by Arafin Mahtab on 6/18/20.
 */

import android.util.Log;

import com.android.githubfacebookrepos.di.AppScope;
import com.android.githubfacebookrepos.model.mapped.GithubRepoMin;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;

/**
 * This DataSource initiates the corresponding service which is responsible to load data locally
 */
@AppScope
public class LocalDataSourceImpl implements LocalDataSource {

    private final String TAG = this.getClass().getName();

    private RealmService realmService;

    private LocalDataSourceImpl() {

    }

    /**
     * Inject tells Dagger how to provide instances of this type
     *
     * @param realmService This instance will be provided by dagger
     */
    @Inject
    public LocalDataSourceImpl(RealmService realmService) {
        this.realmService = realmService;
    }

    @Override
    public Single<ArrayList<GithubRepoMin>> fetchOrganizationRepos(String orgName) {
        try {
            return realmService.fetchCachedGithubRepo(orgName);
        } catch (Exception exception) {
            Log.w(TAG, exception.toString());
            return Single.error(exception);
        }
    }

    @Override
    public Completable saveOrganizationRepos(ArrayList<GithubRepoMin> githubRepoMins) {
        try {
            return realmService.saveOrganizationRepos(githubRepoMins);
        } catch (Exception exception) {
            Log.w(TAG, exception.toString());
            return Completable.error(exception);
        }
    }
}
