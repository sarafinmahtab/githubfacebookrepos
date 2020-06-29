package com.android.githubfacebookrepos.usecase;

/*
 * Created by Arafin Mahtab on 6/18/20.
 */

import android.os.Looper;
import android.util.Log;

import com.android.githubfacebookrepos.base.CompletableUseCase;
import com.android.githubfacebookrepos.dal.repos.MainRepo;
import com.android.githubfacebookrepos.di.ActivityScope;
import com.android.githubfacebookrepos.helpers.CommonUtil;
import com.android.githubfacebookrepos.model.mapped.GithubRepoMin;
import com.android.githubfacebookrepos.worker.WorkScheduler;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.Completable;


/**
 * Dedicated UseCase Business logic responsible for saving github organization repos locally
 */
@ActivityScope
public class SaveOrgRepos extends CompletableUseCase<ArrayList<GithubRepoMin>> {

    private final String TAG = this.getClass().getName();

    private MainRepo mainRepo;

    @Inject
    public SaveOrgRepos(MainRepo mainRepo) {
        // Telling rx java to load this use case with looper of current thread and
        // by default observe on main thread.
        threadExecutorScheduler = WorkScheduler.with(Looper.myLooper());

        this.mainRepo = mainRepo;
    }


    @Override
    protected Completable buildUseCaseCompletable(ArrayList<GithubRepoMin> githubRepoMins) {

        try {
            return mainRepo.saveOrganizationReposLocally(githubRepoMins)
                    .onErrorComplete(throwable -> {
                        String error = CommonUtil.getErrorMessage(throwable);
                        Log.w(TAG, error);
                        return true;
                    });

        } catch (Exception e) {
            String error = CommonUtil.getErrorMessage(e);
            Log.w(TAG, error);
            return Completable.error(e);
        }
    }
}
