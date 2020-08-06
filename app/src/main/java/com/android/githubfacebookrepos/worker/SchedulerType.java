package com.android.githubfacebookrepos.worker;

/*
 * Created by Arafin Mahtab on 6/16/20.
 */

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


@Retention(RetentionPolicy.SOURCE)
@IntDef({
        SchedulerType.MAIN,
        SchedulerType.IO,
        SchedulerType.COMPUTATION,
        SchedulerType.NEW_THREAD
})
public @interface SchedulerType {
    int MAIN = 0;
    int IO = 1;
    int COMPUTATION = 2;
    int NEW_THREAD = 3;
}
