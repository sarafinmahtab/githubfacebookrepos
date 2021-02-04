package com.android.githubfacebookrepos.base;

/*
 * Created by Arafin Mahtab on 6/16/20.
 */

import io.reactivex.Completable;
import io.reactivex.observers.DisposableCompletableObserver;


/**
 * Abstract class for a Use Case (Interactor in terms of Clean Architecture).
 * This interface represents a execution unit for different use cases (this means any use case
 * in the application should implement this contract).
 * <p>
 * By convention each UseCase implementation will return the result using a [DisposableCompletableObserver]
 * that will execute its job in a background thread and will post the result in the UI thread.
 * <p>
 * This use case is to be used when we expect a Completable value to be emitted via a [Completable].
 */
public abstract class CompletableUseCase<Params> extends BaseReactiveUseCase {
    /**
     * Builds an [Completable] which will be used when executing the current [CompletableUseCase].
     */
    protected abstract Completable buildUseCaseCompletable(Params params);

    private Completable buildUseCaseCompletableWithSchedulers(Params params) {
        return buildUseCaseCompletable(params)
                .subscribeOn(threadExecutorScheduler)
                .observeOn(postExecutionThreadScheduler);
    }

    /**
     * Executes the current use case.
     *
     * @param observer [DisposableCompletableObserver] which will be listening to the observer build
     *                 by [buildUseCaseCompletable] method.
     * @param params   Parameters (Optional) used to build/execute this use case.
     */
    public void execute(Params params, DisposableCompletableObserver observer) {
        Completable completable = buildUseCaseCompletableWithSchedulers(params);
        addDisposable(completable.subscribeWith(observer));
    }

    /**
     * Executes the current use case. Skips the subscriber.
     *
     * @param params Parameters (Optional) used to build/execute this use case.
     */
    public void execute(Params params) {
        Completable completable = buildUseCaseCompletableWithSchedulers(params);
        addDisposable(completable.subscribe());
    }
}
