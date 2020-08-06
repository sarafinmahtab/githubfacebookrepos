package com.android.githubfacebookrepos.base;

/*
 * Created by Arafin Mahtab on 6/16/20.
 */

import com.android.githubfacebookrepos.worker.JobExecutor;
import com.android.githubfacebookrepos.worker.SchedulerType;
import com.android.githubfacebookrepos.worker.WorkScheduler;

import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;


public abstract class BaseReactiveUseCase {

    protected Scheduler threadExecutorScheduler =
            WorkScheduler.with(JobExecutor.init(JobExecutor.MINIMUM_THREADS));
    protected Scheduler postExecutionThreadScheduler = WorkScheduler.with(SchedulerType.MAIN);

    private CompositeDisposable disposables = new CompositeDisposable();

    public void dispose() {
        if (!disposables.isDisposed()) {
            disposables.dispose();
        }
    }

    protected void addDisposable(Disposable disposable) {
        disposables.add(disposable);
    }
}
