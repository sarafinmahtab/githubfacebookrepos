package com.android.githubfacebookrepos.usecase;

/*
 * Created by Arafin Mahtab on 6/16/20.
 */

import com.android.githubfacebookrepos.base.SingleUseCase;
import com.android.githubfacebookrepos.worker.SchedulerType;
import com.android.githubfacebookrepos.worker.WorkScheduler;

import javax.inject.Inject;

import io.reactivex.Single;


/**
 * Dedicated UseCase Business logic responsible for fetching github repos
 */
public class FetchGithubRepos extends SingleUseCase<String, String> {

    @Inject
    public FetchGithubRepos() {
        // Telling rx java to load this use case with IO thread and
        // by default observe on main thread.
        threadExecutorScheduler = WorkScheduler.with(SchedulerType.IO);
    }

    @Override
    protected Single<String> buildUseCaseSingle(String s) {
        return null;
    }
}
