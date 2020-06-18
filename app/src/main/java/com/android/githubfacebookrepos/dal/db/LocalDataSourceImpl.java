package com.android.githubfacebookrepos.dal.db;

/*
 * Created by Arafin Mahtab on 6/18/20.
 */

import com.android.githubfacebookrepos.model.mapped.GithubRepoMin;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Single;

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
    public Flowable<ArrayList<GithubRepoMin>> fetchOrganizationRepos(String orgName) {
        return Single.just(new ArrayList<GithubRepoMin>()).toFlowable();
//        return realmService.fetchCachedGithubRepo(orgName);
    }

    @Override
    public void saveOrganizationRepos(ArrayList<GithubRepoMin> githubRepoMins) {
//        realmService.saveOrganizationRepos(githubRepoMins);
    }
}
