package com.android.githubfacebookrepos.usecase;

/*
 * Created by Arafin Mahtab on 6/21/20.
 */

import android.os.Looper;

import com.android.githubfacebookrepos.base.SingleUseCase;
import com.android.githubfacebookrepos.dal.repos.MainRepo;
import com.android.githubfacebookrepos.helpers.ResponseHolder;
import com.android.githubfacebookrepos.model.mapped.RepoNote;
import com.android.githubfacebookrepos.worker.WorkScheduler;

import javax.inject.Inject;

import io.reactivex.Single;

public class AddUpdateNote extends SingleUseCase<RepoNote, ResponseHolder<RepoNote>> {

    private MainRepo mainRepo;

    @Inject
    public AddUpdateNote(MainRepo mainRepo) {
        // Telling rx java to load this use case with IO thread and
        // by default observe on main thread.
        threadExecutorScheduler = WorkScheduler.with(Looper.myLooper());

        this.mainRepo = mainRepo;
    }

    @Override
    protected Single<ResponseHolder<RepoNote>> buildUseCaseSingle(RepoNote repoNote) {
        return mainRepo.addUpdateNoteForRepo(repoNote);
    }
}
