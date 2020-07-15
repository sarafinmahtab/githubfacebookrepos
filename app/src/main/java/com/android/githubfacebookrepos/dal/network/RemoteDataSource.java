package com.android.githubfacebookrepos.dal.network;

/*
 * Created by Arafin Mahtab on 6/18/20.
 */

import com.android.githubfacebookrepos.model.api.GithubRepo;

import java.util.List;

import io.reactivex.Single;

public interface RemoteDataSource {

    Single<List<GithubRepo>> fetchOrganizationRepos(String orgName);
}
