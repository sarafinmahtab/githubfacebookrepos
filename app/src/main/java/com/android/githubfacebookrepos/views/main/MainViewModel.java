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


    public void fetchGithubRepos(String orgName, boolean isNetworkConnectionAvailable) {

        orgRepoListLiveData.postValue(ResponseHolder.loading());

        fetchOrgReposUseCase.execute(
                new ParamFetchOrgRepo(AppConstant.OFFLINE_MODE_ENABLED, isNetworkConnectionAvailable, orgName),
                new DisposableSingleObserver<ResponseHolder<ArrayList<GithubRepoMin>>>() {
                    @Override
                    public void onSuccess(ResponseHolder<ArrayList<GithubRepoMin>> gitRepoListResponseHolder) {
                        orgRepoListLiveData.postValue(gitRepoListResponseHolder);

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

        // Rechecking All RxUseCases Disposed before ViewModel is cleared
        fetchOrgReposUseCase.dispose();

        super.onCleared();
    }
}
