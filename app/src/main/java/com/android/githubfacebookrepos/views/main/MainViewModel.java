package com.android.githubfacebookrepos.views.main;

/*
 * Created by Arafin Mahtab on 6/15/20.
 */

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.githubfacebookrepos.data.AppConstant;
import com.android.githubfacebookrepos.helpers.ResponseHolder;
import com.android.githubfacebookrepos.model.mapped.GithubRepoMin;
import com.android.githubfacebookrepos.model.params.ParamFetchOrgRepo;
import com.android.githubfacebookrepos.usecase.FetchOrgRepos;

import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.observers.DisposableSingleObserver;

public class MainViewModel extends ViewModel {

    private FetchOrgRepos fetchOrgReposUseCase;

    MutableLiveData<ResponseHolder<ArrayList<GithubRepoMin>>> orgRepoListLiveData = new MutableLiveData<>();

    @Inject
    public MainViewModel(FetchOrgRepos fetchOrgRepos) {
        this.fetchOrgReposUseCase = fetchOrgRepos;
    }

    /**
     * Execute [FetchOrgRepos] use case and observed by orgRepoListLiveData when any response returned
     *
     * @param orgName               organization name as path require to load repositories of that organization
     * @param isConnectionAvailable check connectivity before requesting any network request
     */
    public void fetchGithubRepos(String orgName, boolean isConnectionAvailable) {

        orgRepoListLiveData.postValue(ResponseHolder.loading());

        fetchOrgReposUseCase.execute(
                new ParamFetchOrgRepo(AppConstant.offlineModeEnabled, isConnectionAvailable, orgName),
                new DisposableSingleObserver<ResponseHolder<ArrayList<GithubRepoMin>>>() {
                    @Override
                    public void onSuccess(ResponseHolder<ArrayList<GithubRepoMin>> gitRepoListResponseHolder) {
                        orgRepoListLiveData.postValue(gitRepoListResponseHolder);

                        showCachedResponseOnError(orgName, gitRepoListResponseHolder.getError());

                        dispose();
                    }

                    @Override
                    public void onError(Throwable e) {
                        orgRepoListLiveData.postValue(ResponseHolder.error(e));

                        showCachedResponseOnError(orgName, e);

                        dispose();
                    }
                });
    }

    private void showCachedResponseOnError(String orgName, Throwable error) {
        if (error instanceof UnknownHostException) {
            fetchGithubRepos(orgName, false);
        }
    }

    @Override
    protected void onCleared() {

        // Disposing All UseCases before ViewModel is cleared
        fetchOrgReposUseCase.dispose();

        super.onCleared();
    }
}
