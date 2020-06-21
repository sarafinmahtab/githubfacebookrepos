package com.android.githubfacebookrepos.base;

/*
 * Created by Arafin Mahtab on 6/21/20.
 */


import io.reactivex.Observable;
import io.reactivex.observers.DisposableObserver;

/**
 * Abstract class for a Use Case (Interactor in terms of Clean Architecture).
 * This interface represents a execution unit for different use cases (this means any use case
 * in the application should implement this contract).
 * <p>
 * By convention each UseCase implementation will return the result using a [DisposableObserver]
 * that will execute its job in a background thread and will post the result in the UI thread.
 * <p>
 * This use case is to be used when we expect a Observable value to be emitted via a [Observable].
 */
public abstract class ObservableUseCase<Params, Results> extends BaseReactiveUseCase {


    /**
     * Builds an [Observable] which will be used when executing the current [ObservableUseCase].
     */
    protected abstract Observable<Results> buildUseCaseObservable(Params params);


    private Observable<Results> buildUseCaseObservableWithSchedulers(Params params) {
        return buildUseCaseObservable(params)
                .subscribeOn(threadExecutorScheduler)
                .observeOn(postExecutionThreadScheduler);
    }


    /**
     * Executes the current use case.
     *
     * @param observer [DisposableObservableObserver] which will be listening to the observer build
     *                 by [buildUseCaseObservable] method.
     * @param params   Parameters (Optional) used to build/execute this use case.
     */
    public void execute(Params params, DisposableObserver<Results> observer) {
        Observable<Results> observable = buildUseCaseObservableWithSchedulers(params);
        addDisposable(observable.subscribeWith(observer));
    }
}
