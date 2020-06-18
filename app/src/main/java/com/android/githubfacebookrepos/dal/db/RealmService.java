package com.android.githubfacebookrepos.dal.db;

/*
 * Created by Arafin Mahtab on 6/18/20.
 */

import com.android.githubfacebookrepos.data.AppConstant;
import com.android.githubfacebookrepos.model.mapped.GithubRepoMin;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.realm.Realm;

public class RealmService {

    private Realm realm;

    private RealmService() {

    }

    @Inject
    public RealmService(Realm realm) {
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

    public void close() {
        if (!realm.isClosed()) {
            realm.close();
        }
    }
}
