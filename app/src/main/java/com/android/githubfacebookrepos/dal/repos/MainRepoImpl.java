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
import java.util.List;

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

    @Inject
    public MainRepoImpl(
            RemoteDataSource remoteDataSource,
            LocalDataSource localDataSource
    ) {
        this.remoteDataSource = remoteDataSource;
        this.localDataSource = localDataSource;
    }

    @Override
    public Single<List<GithubRepo>> fetchOrganizationReposFromServer(String orgName) {
        return remoteDataSource.fetchOrganizationRepos(orgName);
    }

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
