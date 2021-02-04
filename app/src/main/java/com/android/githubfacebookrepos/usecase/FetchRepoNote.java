package com.android.githubfacebookrepos.usecase;

/*
 * Created by Arafin Mahtab on 6/21/20.
 */

import android.util.Log;

import com.android.githubfacebookrepos.base.ObservableUseCase;
import com.android.githubfacebookrepos.dal.repos.MainRepo;
import com.android.githubfacebookrepos.di.ActivityScope;
import com.android.githubfacebookrepos.helpers.CommonUtil;
import com.android.githubfacebookrepos.helpers.ResponseHolder;
import com.android.githubfacebookrepos.model.mapped.RepoNote;
import com.android.githubfacebookrepos.worker.SchedulerType;
import com.android.githubfacebookrepos.worker.WorkScheduler;

import javax.inject.Inject;

import io.reactivex.Observable;


@ActivityScope
public class FetchRepoNote extends ObservableUseCase<Integer, ResponseHolder<RepoNote>> {

    private final String TAG = this.getClass().getName();

    private final MainRepo mainRepo;

    @Inject
    public FetchRepoNote(MainRepo mainRepo) {
        threadExecutorScheduler = WorkScheduler.with(SchedulerType.IO);

        this.mainRepo = mainRepo;
    }

    @Override
    protected Observable<ResponseHolder<RepoNote>> buildUseCaseObservable(Integer integer) {
        try {
            return mainRepo.fetchRepoNote(integer);
        } catch (Exception e) {
            String error = CommonUtil.getErrorMessage(e);
            Log.w(TAG, error);
            return Observable.just(ResponseHolder.error(e));
        }
    }
}
