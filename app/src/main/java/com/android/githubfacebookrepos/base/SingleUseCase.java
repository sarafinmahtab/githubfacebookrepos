package com.android.githubfacebookrepos.base;

/*
 * Created by Arafin Mahtab on 6/16/20.
 */


import io.reactivex.Single;
import io.reactivex.observers.DisposableSingleObserver;

/**
 * Abstract class for a Use Case (Interactor in terms of Clean Architecture).
 * This interface represents a execution unit for different use cases (this means any use case
 * in the application should implement this contract).
 * <p>
 * By convention each UseCase implementation will return the result using a [DisposableSingleObserver]
 * that will execute its job in a background thread and will post the result in the UI thread.
 * <p>
 * This use case is to be used when we expect a single value to be emitted via a [Single].
 */
public abstract class SingleUseCase<Params, Results> extends BaseReactiveUseCase {


    /**
     * Builds an [Single] which will be used when executing the current [SingleUseCase].
     */
    protected abstract Single<Results> buildUseCaseSingle(Params params);


    private Single<Results> buildUseCaseSingleWithSchedulers(Params params) {
        return buildUseCaseSingle(params)
                .subscribeOn(threadExecutorScheduler)
                .observeOn(postExecutionThreadScheduler);
    }


    /**
     * Executes the current use case.
     *
     * @param observer [DisposableSingleObserver] which will be listening to the observer build
     *                 by [buildUseCaseSingle] method.
     * @param params   Parameters (Optional) used to build/execute this use case.
     */
    public void execute(Params params, DisposableSingleObserver<Results> observer) {
        Single<Results> single = buildUseCaseSingleWithSchedulers(params);
        addDisposable(single.subscribeWith(observer));
    }


    /**
     * Executes the current use case immediately using blockingGet().
     *
     * @param params Parameters (Optional) used to build/execute this use case.
     */
    public Results executeImmediate(Params params) {
        Single<Results> single = buildUseCaseSingleWithSchedulers(params);
        addDisposable(single.subscribe());
        return single.blockingGet();
    }
}
