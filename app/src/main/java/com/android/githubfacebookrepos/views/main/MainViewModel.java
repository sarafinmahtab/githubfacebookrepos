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

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.observers.DisposableSingleObserver;


public class MainViewModel extends ViewModel {

    private final FetchOrgRepos fetchOrgReposUseCase;

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
                    public void onSuccess(@NotNull ResponseHolder<ArrayList<GithubRepoMin>> gitRepoListResponseHolder) {
                        orgRepoListLiveData.postValue(gitRepoListResponseHolder);

                        dispose();
                    }

                    @Override
                    public void onError(@NotNull Throwable e) {
                        orgRepoListLiveData.postValue(ResponseHolder.error(e));

                        dispose();
                    }
                });
    }

    @Override
    protected void onCleared() {
        fetchOrgReposUseCase.dispose();

        super.onCleared();
    }
}
