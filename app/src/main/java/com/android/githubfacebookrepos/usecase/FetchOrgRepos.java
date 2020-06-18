package com.android.githubfacebookrepos.usecase;

/*
 * Created by Arafin Mahtab on 6/16/20.
 */

import com.android.githubfacebookrepos.base.SingleUseCase;
import com.android.githubfacebookrepos.dal.repos.MainRepo;
import com.android.githubfacebookrepos.helpers.CommonUtil;
import com.android.githubfacebookrepos.helpers.ResponseHolder;
import com.android.githubfacebookrepos.model.mapped.GithubRepoMin;
import com.android.githubfacebookrepos.model.params.ParamFetchOrgRepo;
import com.android.githubfacebookrepos.worker.SchedulerType;
import com.android.githubfacebookrepos.worker.WorkScheduler;

import java.util.ArrayList;
import java.util.stream.Collectors;

import javax.inject.Inject;

import io.reactivex.Single;
import retrofit2.HttpException;


/**
 * Dedicated UseCase Business logic responsible for fetching github organization repos
 */
public class FetchOrgRepos extends SingleUseCase<ParamFetchOrgRepo, ResponseHolder<ArrayList<GithubRepoMin>>> {

    private MainRepo mainRepo;

    @Inject
    public FetchOrgRepos(MainRepo mainRepo) {
        // Telling rx java to load this use case with IO thread and
        // by default observe on main thread.
        threadExecutorScheduler = WorkScheduler.with(SchedulerType.IO);

        this.mainRepo = mainRepo;
    }

    @Override
    protected Single<ResponseHolder<ArrayList<GithubRepoMin>>> buildUseCaseSingle(ParamFetchOrgRepo paramFetchOrgRepo) {

        if (paramFetchOrgRepo.isShouldCacheResponse() && paramFetchOrgRepo.isNetworkConnectionAvailable()) {

            // Loading GithubRepo Data from server and mapped to secondary object GithubRepoMin

            return mainRepo.fetchOrganizationReposFromServer(paramFetchOrgRepo.getOrgName())
                    .map(githubRepos -> {

                        ArrayList<GithubRepoMin> githubRepoMinArrayList = githubRepos.stream()
                                .map(githubRepo -> new GithubRepoMin(
                                        githubRepo.getId(),
                                        githubRepo.getName(),
                                        githubRepo.isPrivate(),
                                        githubRepo.getOwner().getLogin(),
                                        githubRepo.getOwner().getAvatarUrl(),
                                        githubRepo.getUpdatedAt(),
                                        githubRepo.getLanguage(),
                                        githubRepo.getDescription(),
                                        githubRepo.isFork())
                                ).collect(Collectors.toCollection(ArrayList::new));

                        return ResponseHolder.success(githubRepoMinArrayList);
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
            // Loading cached GithubRepoMin data as there is no available internet connection

//            return mainRepo.fetchCachedOrganizationRepos(paramFetchOrgRepo.getOrgName());

            return Single.never();
        }
    }
}
