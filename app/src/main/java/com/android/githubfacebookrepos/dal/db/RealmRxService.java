package com.android.githubfacebookrepos.dal.db;

/*
 * Created by Arafin Mahtab on 6/18/20.
 */

import android.content.res.Resources;

import com.android.githubfacebookrepos.data.AppConstant;
import com.android.githubfacebookrepos.di.AppScope;
import com.android.githubfacebookrepos.helpers.ResponseHolder;
import com.android.githubfacebookrepos.model.mapped.GithubRepoMin;
import com.android.githubfacebookrepos.model.mapped.RepoNote;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.realm.Realm;

@AppScope
public class RealmRxService {

    private Realm realm;

    private RealmRxService() {

    }

    @Inject
    public RealmRxService(Realm realm) {
        this.realm = realm;
    }

    public Single<ArrayList<GithubRepoMin>> fetchCachedGithubRepo(String orgName) {

        try {
            return Single.just(new ArrayList<>(
                    realm.where(GithubRepoMin.class)
                            .like(AppConstant.ORG_NAME_FIELD, orgName)
                            .findAll()
            ));
        } catch (Exception e) {
            e.printStackTrace();
            return Single.error(e);
        }
    }

    public Completable saveOrganizationRepos(ArrayList<GithubRepoMin> githubRepoMins) {
        try {
            return Completable.create(
                    emitter -> realm.executeTransaction(
                            realm -> realm.insertOrUpdate(githubRepoMins)
                    )
            );
        } catch (Exception e) {
            e.printStackTrace();
            return Completable.error(e);
        }
    }

    public Single<ResponseHolder<RepoNote>> addUpdateNote(RepoNote repoNote) {
        try {
            return Single.create(emitter ->
                    realm.executeTransactionAsync(
                            realm -> realm.insertOrUpdate(repoNote),
                            () -> emitter.onSuccess(ResponseHolder.success(repoNote)),
                            emitter::onError
                    )
            );
        } catch (Exception e) {
            e.printStackTrace();
            return Single.error(e);
        }
    }

    public Observable<ResponseHolder<RepoNote>> fetchRepoNote(int repoId) {
        try {
            return Observable.create(emitter -> {
                RepoNote repoNote = realm.where(RepoNote.class)
                        .equalTo(AppConstant.REPO_ID_FIELD, repoId)
                        .findFirst();

                if (repoNote != null) {
                    emitter.onNext(ResponseHolder.success(repoNote));
                } else {
                    emitter.onError(new Resources.NotFoundException("No repo note found with the repo id " + repoId));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            return Observable.error(e);
        }
    }

    public void close() {
        if (!realm.isClosed()) {
            realm.close();
        }
    }
}
