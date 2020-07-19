package com.android.githubfacebookrepos.dal.db;

/*
 * Created by Arafin Mahtab on 7/16/20.
 */

import com.android.githubfacebookrepos.helpers.ResponseHolder;
import com.android.githubfacebookrepos.model.mapped.GithubRepoMin;
import com.android.githubfacebookrepos.model.mapped.RepoNote;

import java.util.ArrayList;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

public class MockLocalDataSourceImpl implements LocalDataSource {

    private InMemoryRealmService realmRxService;

    private MockLocalDataSourceImpl() {

    }

    public MockLocalDataSourceImpl(InMemoryRealmService realmRxService) {
        this.realmRxService = realmRxService;
    }

    @Override
    public Single<ArrayList<GithubRepoMin>> fetchOrganizationRepos(String orgName) {
        try {
            return realmRxService.fetchCachedGithubRepo(orgName);
        } catch (Exception exception) {
            return Single.error(exception);
        }
    }

    @Override
    public Completable saveOrganizationRepos(ArrayList<GithubRepoMin> githubRepoMins) {
        try {
            return realmRxService.saveOrganizationRepos(githubRepoMins);
        } catch (Exception exception) {
            return Completable.error(exception);
        }
    }

    @Override
    public Single<ResponseHolder<RepoNote>> addUpdateNoteForRepo(RepoNote repoNote) {
        try {
            return realmRxService.addUpdateNote(repoNote);
        } catch (Exception exception) {
            return Single.error(exception);
        }
    }

    @Override
    public Observable<ResponseHolder<RepoNote>> fetchRepoNote(int repoId) {
        try {
            return realmRxService.fetchRepoNote(repoId);
        } catch (Exception exception) {
            return Observable.error(exception);
        }
    }
}
