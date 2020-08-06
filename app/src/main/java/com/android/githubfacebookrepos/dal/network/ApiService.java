package com.android.githubfacebookrepos.dal.network;

/*
 * Created by Arafin Mahtab on 6/15/20.
 */

import com.android.githubfacebookrepos.model.api.GithubRepo;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;


public interface ApiService {

    @GET("orgs/{org}/repos")
    Single<List<GithubRepo>> fetchOrganizationRepos(
            @Path("org") String orgName
    );
}
