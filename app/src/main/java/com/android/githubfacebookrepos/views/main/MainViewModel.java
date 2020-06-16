package com.android.githubfacebookrepos.views.main;

/*
 * Created by Arafin Mahtab on 6/15/20.
 */

import androidx.lifecycle.ViewModel;

import com.android.githubfacebookrepos.usecase.FetchGithubRepos;

import javax.inject.Inject;

public class MainViewModel extends ViewModel {

    private FetchGithubRepos fetchGithubReposUseCase;

    @Inject
    public MainViewModel(FetchGithubRepos fetchGithubRepos) {
        this.fetchGithubReposUseCase = fetchGithubRepos;
    }

    public void fetchGithubRepos() {

    }

    @Override
    protected void onCleared() {

        // Disposing All UseCases before ViewModel is cleared
        fetchGithubReposUseCase.dispose();

        super.onCleared();
    }
}
