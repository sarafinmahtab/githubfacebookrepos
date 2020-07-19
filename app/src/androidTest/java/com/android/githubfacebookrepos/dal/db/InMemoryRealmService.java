package com.android.githubfacebookrepos.dal.db;

/*
 * Created by Arafin Mahtab on 7/16/20.
 */

import android.content.res.Resources;

import com.android.githubfacebookrepos.data.AppConstant;
import com.android.githubfacebookrepos.helpers.ResponseHolder;
import com.android.githubfacebookrepos.model.mapped.GithubRepoMin;
import com.android.githubfacebookrepos.model.mapped.RepoNote;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class InMemoryRealmService {

    private RealmConfiguration realmConfiguration;

    public InMemoryRealmService() {
        realmConfiguration = new RealmConfiguration.Builder()
                .schemaVersion(AppConstant.DATA_BASE_VERSION)
                .name("mock.realm")
                .inMemory()
                .build();
    }

    public Single<ArrayList<GithubRepoMin>> fetchCachedGithubRepo(String orgName) {
        try {
            Realm realm = Realm.getInstance(realmConfiguration);
            RealmQuery<GithubRepoMin> query = realm.where(GithubRepoMin.class);
            RealmResults<GithubRepoMin> items = query
                    .equalTo(AppConstant.ORG_NAME_FIELD, orgName)
                    .findAll();
            List<GithubRepoMin> results = realm.copyFromRealm(items);

            return Single.just(new ArrayList<>(results));
        } catch (Exception e) {
            e.printStackTrace();
            return Single.error(e);
        }
    }

    public Completable saveOrganizationRepos(ArrayList<GithubRepoMin> githubRepoMins) {
        try {
            Realm realm = Realm.getInstance(realmConfiguration);
            realm.beginTransaction();
            realm.insertOrUpdate(githubRepoMins);
            realm.commitTransaction();

            return Completable.complete();
        } catch (Exception e) {
            e.printStackTrace();
            return Completable.error(e);
        }
    }

    public Single<ResponseHolder<RepoNote>> addUpdateNote(RepoNote repoNote) {
        try {
            Realm realm = Realm.getInstance(realmConfiguration);
            realm.beginTransaction();
            realm.insertOrUpdate(repoNote);
            realm.commitTransaction();

            return Single.just(ResponseHolder.success(repoNote));
        } catch (Exception e) {
            e.printStackTrace();
            return Single.error(e);
        }
    }

    public Observable<ResponseHolder<RepoNote>> fetchRepoNote(int repoId) {
        try {
            Realm realm = Realm.getInstance(realmConfiguration);
            RepoNote repoNote = realm.where(RepoNote.class)
                    .equalTo(AppConstant.REPO_ID_FIELD, repoId)
                    .findFirst();

            if (repoNote != null) {
                return Observable.just(ResponseHolder.success(repoNote));
            } else {
                return Observable.error(new Resources.NotFoundException("No repo note found with the repo id " + repoId));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Observable.error(e);
        }
    }

    public void close() {
        Realm realm = Realm.getInstance(realmConfiguration);
        if (!realm.isClosed()) {
            realm.close();
        }
    }

    public void delete() {
        Realm.deleteRealm(realmConfiguration);
    }
}
