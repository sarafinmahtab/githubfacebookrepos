package com.android.githubfacebookrepos.dal.repos;

/*
 * Created by Arafin Mahtab on 6/16/20.
 */

import com.android.githubfacebookrepos.model.api.GithubRepo;
import com.android.githubfacebookrepos.model.mapped.GithubRepoMin;

import java.util.ArrayList;

import io.reactivex.Single;

public interface MainRepo {

    Single<ArrayList<GithubRepo>> fetchOrganizationReposFromServer(String orgName);

    Single<ArrayList<GithubRepoMin>> fetchCachedOrganizationRepos(String orgName);
}
