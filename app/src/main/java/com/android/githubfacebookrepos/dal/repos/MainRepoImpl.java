package com.android.githubfacebookrepos.dal.repos;

/*
 * Created by Arafin Mahtab on 6/16/20.
 */

import com.android.githubfacebookrepos.dal.network.ApiService;
import com.android.githubfacebookrepos.model.GithubRepo;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.Single;


public class MainRepoImpl implements MainRepo {

    private final String TAG = this.getClass().getName();

    private ApiService apiService;

    private MainRepoImpl() {
    }

    /**
     * Inject tells Dagger how to provide instances of this type
     *
     * @param apiService This instance will be provided by dagger
     */
    @Inject
    public MainRepoImpl(ApiService apiService) {
        this.apiService = apiService;
    }

    /**
     * Fetch Organization repos from service by retrofit ApiService
     *
     * @param orgName github organization name
     * @return list of GithubRepo as RxJava Single Response
     */
    @Override
    public Single<ArrayList<GithubRepo>> fetchOrganizationRepos(String orgName) {
        try {
            return apiService.fetchOrganizationRepos(orgName);
        } catch (Exception e) {
            return Single.error(e);
        }
    }
}
