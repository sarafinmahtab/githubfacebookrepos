package com.android.githubfacebookrepos.dal.db;

/*
 * Created by Arafin Mahtab on 6/18/20.
 */

import android.util.Log;

import com.android.githubfacebookrepos.di.AppScope;
import com.android.githubfacebookrepos.helpers.ResponseHolder;
import com.android.githubfacebookrepos.model.mapped.GithubRepoMin;
import com.android.githubfacebookrepos.model.mapped.RepoNote;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;


/**
 * This DataSource initiates the corresponding service which is responsible to load data locally
 */
@AppScope
public class LocalDataSourceImpl implements LocalDataSource {

    private final String TAG = this.getClass().getName();

    private RealmRxService realmRxService;

    private LocalDataSourceImpl() {

    }

    /**
     * Inject tells Dagger how to provide instances of this type
     *
     * @param realmRxService This instance will be provided by dagger
     */
    @Inject
    public LocalDataSourceImpl(RealmRxService realmRxService) {
        this.realmRxService = realmRxService;
    }

    @Override
    public Single<ArrayList<GithubRepoMin>> fetchOrganizationRepos(String orgName) {
        try {
            return realmRxService.fetchCachedGithubRepo(orgName);
        } catch (Exception exception) {
            Log.w(TAG, exception.toString());
            return Single.error(exception);
        }
    }

    @Override
    public Completable saveOrganizationRepos(ArrayList<GithubRepoMin> githubRepoMins) {
        try {
            return realmRxService.saveOrganizationRepos(githubRepoMins);
        } catch (Exception exception) {
            Log.w(TAG, exception.toString());
            return Completable.error(exception);
        }
    }

    @Override
    public Single<ResponseHolder<RepoNote>> addUpdateNoteForRepo(RepoNote repoNote) {
        try {
            return realmRxService.addUpdateNote(repoNote);
        } catch (Exception exception) {
            Log.w(TAG, exception.toString());
            return Single.error(exception);
        }
    }

    @Override
    public Observable<ResponseHolder<RepoNote>> fetchRepoNote(int repoId) {
        try {
            return realmRxService.fetchRepoNote(repoId);
        } catch (Exception exception) {
            Log.w(TAG, exception.toString());
            return Observable.error(exception);
        }
    }
}
