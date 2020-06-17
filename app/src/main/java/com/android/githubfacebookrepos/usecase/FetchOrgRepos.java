package com.android.githubfacebookrepos.usecase;

/*
 * Created by Arafin Mahtab on 6/16/20.
 */

import com.android.githubfacebookrepos.base.SingleUseCase;
import com.android.githubfacebookrepos.dal.repos.MainRepo;
import com.android.githubfacebookrepos.helpers.CommonUtil;
import com.android.githubfacebookrepos.helpers.ResponseHolder;
import com.android.githubfacebookrepos.model.GithubRepo;
import com.android.githubfacebookrepos.model.params.ParamFetchOrgRepo;
import com.android.githubfacebookrepos.worker.SchedulerType;
import com.android.githubfacebookrepos.worker.WorkScheduler;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.functions.Function;
import retrofit2.HttpException;


/**
 * Dedicated UseCase Business logic responsible for fetching github organization repos
 */
public class FetchOrgRepos extends SingleUseCase<ParamFetchOrgRepo, ResponseHolder<ArrayList<GithubRepo>>> {

    private MainRepo mainRepo;

    @Inject
    public FetchOrgRepos(MainRepo mainRepo) {
        // Telling rx java to load this use case with IO thread and
        // by default observe on main thread.
        threadExecutorScheduler = WorkScheduler.with(SchedulerType.IO);

        this.mainRepo = mainRepo;
    }

    @Override
    protected Single<ResponseHolder<ArrayList<GithubRepo>>> buildUseCaseSingle(ParamFetchOrgRepo paramFetchOrgRepo) {

        return mainRepo.fetchOrganizationRepos(paramFetchOrgRepo.getOrgName())
                .map(new Function<ArrayList<GithubRepo>, ResponseHolder<ArrayList<GithubRepo>>>() {
                    @Override
                    public ResponseHolder<ArrayList<GithubRepo>> apply(ArrayList<GithubRepo> githubRepos) throws Exception {
                        return ResponseHolder.success(githubRepos);
                    }
                })
                .onErrorReturn(throwable -> {

                    if (throwable instanceof HttpException) {
                        return ResponseHolder.error(
                                CommonUtil.prepareErrorResult(
                                        ((HttpException) throwable).code(),
                                        ((HttpException) throwable).message()
                                ));
                    } else {
                        return ResponseHolder.error(throwable);
                    }
                });
    }
}
