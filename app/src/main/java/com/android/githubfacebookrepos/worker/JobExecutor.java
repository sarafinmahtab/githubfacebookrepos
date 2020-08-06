package com.android.githubfacebookrepos.worker;

/*
 * Created by Arafin Mahtab on 6/16/20.
 */

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class JobExecutor implements Executor {

    public static final int MINIMUM_THREADS = 3;
    private static JobExecutor jobExecutor;

    private Executor threadPoolExecutor;

    public JobExecutor(int numberOfThread) {
        threadPoolExecutor = Executors.newFixedThreadPool(numberOfThread);
    }

    @Override
    public void execute(Runnable command) {
        threadPoolExecutor.execute(command);
    }

    public static JobExecutor init(int numberOfThread) {
        if (jobExecutor == null) {
            jobExecutor = new JobExecutor(numberOfThread);
        }
        return jobExecutor;
    }
}
