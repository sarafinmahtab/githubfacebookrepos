package com.android.githubfacebookrepos.views.main;

/*
 * Created by Arafin Mahtab on 6/15/20.
 */

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.githubfacebookrepos.helpers.ResponseHolder;
import com.android.githubfacebookrepos.model.GithubRepo;
import com.android.githubfacebookrepos.model.params.ParamFetchOrgRepo;
import com.android.githubfacebookrepos.usecase.FetchOrgRepos;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.observers.DisposableSingleObserver;

public class MainViewModel extends ViewModel {

    private FetchOrgRepos fetchOrgReposUseCase;

    MutableLiveData<ResponseHolder<ArrayList<GithubRepo>>> orgRepoListLiveData;

    @Inject
    public MainViewModel(FetchOrgRepos fetchOrgRepos) {
        this.fetchOrgReposUseCase = fetchOrgRepos;
    }

    /**
     * Execute [FetchOrgRepos] use case and observed by orgRepoListLiveData when any response returned
     *
     * @param orgName organization name as path require to load repositories of that organization
     */
    public void fetchGithubRepos(String orgName) {

        orgRepoListLiveData.postValue(ResponseHolder.loading());

        fetchOrgReposUseCase.execute(
                new ParamFetchOrgRepo(true, orgName),
                new DisposableSingleObserver<ResponseHolder<ArrayList<GithubRepo>>>() {
                    @Override
                    public void onSuccess(ResponseHolder<ArrayList<GithubRepo>> arrayListResponseHolder) {
                        orgRepoListLiveData.postValue(arrayListResponseHolder);

                        dispose();
                    }

                    @Override
                    public void onError(Throwable e) {
                        orgRepoListLiveData.postValue(ResponseHolder.error(e));

                        dispose();
                    }
                });
    }

    @Override
    protected void onCleared() {

        // Disposing All UseCases before ViewModel is cleared
        fetchOrgReposUseCase.dispose();

        super.onCleared();
    }
}
