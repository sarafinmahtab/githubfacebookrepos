package com.android.githubfacebookrepos.worker;

/*
 * Created by Arafin Mahtab on 6/16/20.
 */

import android.os.Looper;

import java.util.concurrent.Executor;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public final class WorkScheduler {

//    public static boolean isTesting = true;

    public static Scheduler with(@SchedulerType int currentScheduler) {

//        if (isTesting)
//            return Schedulers.trampoline();

        switch (currentScheduler) {
            case SchedulerType.MAIN:
                return AndroidSchedulers.mainThread();
            case SchedulerType.IO:
                return Schedulers.io();
            case SchedulerType.COMPUTATION:
                return Schedulers.computation();
            case SchedulerType.NEW_THREAD:
                return Schedulers.newThread();
        }

        return AndroidSchedulers.mainThread();
    }

    public static Scheduler with(Executor executor) {
        return Schedulers.from(executor);
    }

    public static Scheduler with(Looper looper) {
        return AndroidSchedulers.from(looper);
    }
}
