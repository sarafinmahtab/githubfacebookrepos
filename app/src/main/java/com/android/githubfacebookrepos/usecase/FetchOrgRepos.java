package com.android.githubfacebookrepos.usecase;

/*
 * Created by Arafin Mahtab on 6/16/20.
 */

import android.util.Log;

import com.android.githubfacebookrepos.base.SingleUseCase;
import com.android.githubfacebookrepos.dal.repos.MainRepo;
import com.android.githubfacebookrepos.di.ActivityScope;
import com.android.githubfacebookrepos.helpers.CommonUtil;
import com.android.githubfacebookrepos.helpers.ResponseHolder;
import com.android.githubfacebookrepos.model.mapped.GithubRepoMin;
import com.android.githubfacebookrepos.model.params.ParamFetchOrgRepo;
import com.android.githubfacebookrepos.worker.SchedulerType;
import com.android.githubfacebookrepos.worker.WorkScheduler;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.stream.Collectors;

import javax.inject.Inject;

import io.reactivex.Single;
import retrofit2.HttpException;


/**
 * Dedicated UseCase Business logic responsible for fetching github organization repos
 */
@ActivityScope
public class FetchOrgRepos extends SingleUseCase<ParamFetchOrgRepo, ResponseHolder<ArrayList<GithubRepoMin>>> {

    private final String TAG = this.getClass().getName();

    private MainRepo mainRepo;
    private SaveOrgRepos saveOrgReposUseCase;

    @Inject
    public FetchOrgRepos(MainRepo mainRepo, SaveOrgRepos saveOrgRepos) {
        // Telling rx java to load this use case with IO thread and
        // by default observe on main thread.
        threadExecutorScheduler = WorkScheduler.with(SchedulerType.IO);

        this.mainRepo = mainRepo;
        this.saveOrgReposUseCase = saveOrgRepos;
    }

    @Override
    protected Single<ResponseHolder<ArrayList<GithubRepoMin>>> buildUseCaseSingle(ParamFetchOrgRepo paramFetchOrgRepo) {

        try {
            if (paramFetchOrgRepo.isNetworkConnectionAvailable()) {

                return mainRepo.fetchOrganizationReposFromServer(paramFetchOrgRepo.getOrgName())
                        .map(githubRepos -> {

                            ArrayList<GithubRepoMin> githubRepoMinArrayList = githubRepos.stream()
                                    .map(githubRepo -> new GithubRepoMin(
                                            githubRepo.getId(),
                                            githubRepo.getName(),
                                            githubRepo.isPrivate(),
                                            githubRepo.getOwner().getId(),
                                            githubRepo.getOwner().getLogin(),
                                            githubRepo.getOwner().getAvatarUrl(),
                                            githubRepo.getUpdatedAt(),
                                            githubRepo.getLanguage(),
                                            githubRepo.getDescription(),
                                            githubRepo.isFork())
                                    ).collect(Collectors.toCollection(ArrayList::new));

                            if (paramFetchOrgRepo.isShouldCacheResponse()) {
                                saveOrgReposUseCase.execute(githubRepoMinArrayList);
                            }

                            return ResponseHolder.success(githubRepoMinArrayList);
                        })
                        .onErrorResumeNext(throwable -> {
                            if (throwable instanceof UnknownHostException) {
                                return buildUseCaseSingle(
                                        new ParamFetchOrgRepo(paramFetchOrgRepo.isShouldCacheResponse(),
                                                false,
                                                paramFetchOrgRepo.getOrgName()
                                        )
                                );
                            }
                            return Single.error(throwable);
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
            } else {

                return mainRepo.fetchCachedOrganizationRepos(paramFetchOrgRepo.getOrgName())
                        .map(ResponseHolder::success)
                        .onErrorReturn(ResponseHolder::error);
            }

        } catch (Exception e) {
            String error = CommonUtil.getErrorMessage(e);
            Log.w(TAG, error);
            return Single.just(ResponseHolder.error(e));
        }
    }
}
