package com.android.githubfacebookrepos.usecase;

/*
 * Created by Arafin Mahtab on 6/21/20.
 */

import android.util.Log;

import com.android.githubfacebookrepos.base.SingleUseCase;
import com.android.githubfacebookrepos.dal.repos.MainRepo;
import com.android.githubfacebookrepos.di.ActivityScope;
import com.android.githubfacebookrepos.helpers.CommonUtil;
import com.android.githubfacebookrepos.helpers.ResponseHolder;
import com.android.githubfacebookrepos.model.mapped.RepoNote;
import com.android.githubfacebookrepos.worker.SchedulerType;
import com.android.githubfacebookrepos.worker.WorkScheduler;

import javax.inject.Inject;

import io.reactivex.Single;

@ActivityScope
public class AddUpdateNote extends SingleUseCase<RepoNote, ResponseHolder<RepoNote>> {

    private final String TAG = this.getClass().getName();

    private MainRepo mainRepo;

    @Inject
    public AddUpdateNote(MainRepo mainRepo) {
        // Telling rx java to load this use case with IO thread and
        // by default observe on main thread.
        threadExecutorScheduler = WorkScheduler.with(SchedulerType.IO);

        this.mainRepo = mainRepo;
    }

    @Override
    protected Single<ResponseHolder<RepoNote>> buildUseCaseSingle(RepoNote repoNote) {
        try {
            return mainRepo.addUpdateNoteForRepo(repoNote);
        } catch (Exception e) {
            String error = CommonUtil.getErrorMessage(e);
            Log.w(TAG, error);
            return Single.just(ResponseHolder.error(e));
        }
    }
}
