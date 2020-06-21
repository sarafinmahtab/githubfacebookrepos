package com.android.githubfacebookrepos.usecase;

/*
 * Created by Arafin Mahtab on 6/21/20.
 */

import android.os.Looper;

import com.android.githubfacebookrepos.base.ObservableUseCase;
import com.android.githubfacebookrepos.dal.repos.MainRepo;
import com.android.githubfacebookrepos.helpers.ResponseHolder;
import com.android.githubfacebookrepos.model.mapped.RepoNote;
import com.android.githubfacebookrepos.worker.WorkScheduler;

import javax.inject.Inject;

import io.reactivex.Observable;

public class FetchRepoNote extends ObservableUseCase<Integer, ResponseHolder<RepoNote>> {

    private final String TAG = this.getClass().getName();

    private MainRepo mainRepo;

    @Inject
    public FetchRepoNote(MainRepo mainRepo) {
        // Telling rx java to load this use case with looper of current thread and
        // by default observe on main thread.
        threadExecutorScheduler = WorkScheduler.with(Looper.myLooper());

        this.mainRepo = mainRepo;
    }

    @Override
    protected Observable<ResponseHolder<RepoNote>> buildUseCaseObservable(Integer integer) {
        return mainRepo.fetchRepoNote(integer);
    }
}
