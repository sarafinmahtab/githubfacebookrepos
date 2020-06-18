package com.android.githubfacebookrepos.dal.db;

/*
 * Created by Arafin Mahtab on 6/18/20.
 */

import com.android.githubfacebookrepos.model.mapped.GithubRepoMin;

import java.util.ArrayList;

import io.reactivex.Flowable;

public interface LocalDataSource {

    Flowable<ArrayList<GithubRepoMin>> fetchOrganizationRepos(String orgName);

    void saveOrganizationRepos(ArrayList<GithubRepoMin> githubRepoMins);
}
