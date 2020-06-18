package com.android.githubfacebookrepos.dal.db;

/*
 * Created by Arafin Mahtab on 6/18/20.
 */

import javax.inject.Inject;

public class RealmService {

    @Inject
    public RealmService() {

    }

/*
    private Realm realm;

    @Inject
    public RealmService(Realm realm) {
        this.realm = realm;
    }

    public Flowable<ArrayList<GithubRepoMin>> fetchCachedGithubRepo(String orgName) {

        return realm.asFlowable()
                .map(realm -> new ArrayList<>(realm.where(GithubRepoMin.class)
                        .like("Login", orgName)
                        .findAll())
                );
    }

    public void saveOrganizationRepos(ArrayList<GithubRepoMin> githubRepoMins) {
        realm.executeTransactionAsync(realm -> {
            realm.insertOrUpdate(githubRepoMins);
        });
    }

 */
}
