package com.android.githubfacebookrepos.dal.repos;

/*
 * Created by Arafin Mahtab on 6/16/20.
 */

import com.android.githubfacebookrepos.model.GithubRepo;

import java.util.ArrayList;

import io.reactivex.Single;

public interface MainRepo {

    Single<ArrayList<GithubRepo>> fetchOrganizationRepos(String orgName);
}