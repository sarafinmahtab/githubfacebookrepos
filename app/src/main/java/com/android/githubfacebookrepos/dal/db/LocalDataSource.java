package com.android.githubfacebookrepos.dal.db;

/*
 * Created by Arafin Mahtab on 6/18/20.
 */

import com.android.githubfacebookrepos.model.mapped.GithubRepoMin;

import java.util.ArrayList;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface LocalDataSource {

    Single<ArrayList<GithubRepoMin>> fetchOrganizationRepos(String orgName);

    Completable saveOrganizationRepos(ArrayList<GithubRepoMin> githubRepoMins);
}
