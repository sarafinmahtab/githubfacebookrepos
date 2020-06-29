package com.android.githubfacebookrepos.dal.repos;

/*
 * Created by Arafin Mahtab on 6/16/20.
 */

import android.util.Log;

import com.android.githubfacebookrepos.dal.db.LocalDataSource;
import com.android.githubfacebookrepos.dal.network.RemoteDataSource;
import com.android.githubfacebookrepos.di.AppScope;
import com.android.githubfacebookrepos.helpers.ResponseHolder;
import com.android.githubfacebookrepos.model.api.GithubRepo;
import com.android.githubfacebookrepos.model.mapped.GithubRepoMin;
import com.android.githubfacebookrepos.model.mapped.RepoNote;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

@AppScope
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
        try {
            return localDataSource.fetchOrganizationRepos(orgName);
        } catch (Exception e) {
            Log.w(TAG, e.toString());
            return Single.error(e);
        }
    }

    @Override
    public Completable saveOrganizationReposLocally(ArrayList<GithubRepoMin> githubRepoMins) {
        try {
            return localDataSource.saveOrganizationRepos(githubRepoMins);
        } catch (Exception e) {
            Log.w(TAG, e.toString());
            return Completable.error(e);
        }
    }

    @Override
    public Single<ResponseHolder<RepoNote>> addUpdateNoteForRepo(RepoNote repoNote) {
        try {
            return localDataSource.addUpdateNoteForRepo(repoNote);
        } catch (Exception e) {
            Log.w(TAG, e.toString());
            return Single.error(e);
        }
    }

    @Override
    public Observable<ResponseHolder<RepoNote>> fetchRepoNote(int repoId) {
        try {
            return localDataSource.fetchRepoNote(repoId);
        } catch (Exception e) {
            Log.w(TAG, e.toString());
            return Observable.error(e);
        }
    }
}
