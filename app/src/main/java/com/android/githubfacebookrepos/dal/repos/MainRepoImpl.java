package com.android.githubfacebookrepos.dal.repos;

/*
 * Created by Arafin Mahtab on 6/16/20.
 */

import com.android.githubfacebookrepos.dal.db.LocalDataSource;
import com.android.githubfacebookrepos.dal.network.RemoteDataSource;
import com.android.githubfacebookrepos.model.api.GithubRepo;
import com.android.githubfacebookrepos.model.mapped.GithubRepoMin;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.Single;


public class MainRepoImpl implements MainRepo {

    private final String TAG = this.getClass().getName();

    private RemoteDataSource remoteDataSource;
    private LocalDataSource localDataSource;

    private MainRepoImpl() {
    }

    /**
     * Inject tells Dagger how to provide instances of this type
     *
     * @param remoteDataSource This instance will be provided by dagger
     * @param localDataSource  This instance will be provided by dagger
     */
    @Inject
    public MainRepoImpl(
            RemoteDataSource remoteDataSource,
            LocalDataSource localDataSource
    ) {
        this.remoteDataSource = remoteDataSource;
        this.localDataSource = localDataSource;
    }

    /**
     * Fetch Organization repos from server
     *
     * @param orgName github organization name
     * @return list of GithubRepo as RxJava Single Response
     */
    @Override
    public Single<ArrayList<GithubRepo>> fetchOrganizationReposFromServer(String orgName) {
        return remoteDataSource.fetchOrganizationRepos(orgName);
    }

    /**
     * Fetch any cached organization repos response
     *
     * @param orgName github organization name
     * @return list of GithubRepo as RxJava Single Response
     */
    @Override
    public Single<ArrayList<GithubRepoMin>> fetchCachedOrganizationRepos(String orgName) {
        return localDataSource.fetchOrganizationRepos(orgName).firstOrError();
    }

    @Override
    public void saveOrganizationReposLocally(ArrayList<GithubRepoMin> githubRepoMins) {
        localDataSource.saveOrganizationRepos(githubRepoMins);
    }
}
