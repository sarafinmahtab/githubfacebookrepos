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
    public static final int MAIN = 0;
    public static final int IO = 1;
    public static final int COMPUTATION = 2;
    public static final int NEW_THREAD = 3;
}
