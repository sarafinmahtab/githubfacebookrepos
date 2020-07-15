package com.android.githubfacebookrepos.views.notes;

/*
 * Created by Arafin Mahtab on 6/21/20.
 */

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.githubfacebookrepos.helpers.ResponseHolder;
import com.android.githubfacebookrepos.model.mapped.RepoNote;
import com.android.githubfacebookrepos.usecase.AddUpdateNote;
import com.android.githubfacebookrepos.usecase.FetchRepoNote;

import javax.inject.Inject;

import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.DisposableSingleObserver;


public class AddUpdateNoteViewModel extends ViewModel {

    private FetchRepoNote fetchRepoNote;
    private AddUpdateNote addUpdateNote;

    MutableLiveData<ResponseHolder<RepoNote>> repoNoteLiveData = new MutableLiveData<>();

    @Inject
    public AddUpdateNoteViewModel(FetchRepoNote fetchRepoNote, AddUpdateNote addUpdateNote) {
        this.fetchRepoNote = fetchRepoNote;
        this.addUpdateNote = addUpdateNote;
    }

    public void addUpdateNote(String noteId, String note, int repoId) {

        repoNoteLiveData.setValue(ResponseHolder.loading());

        RepoNote repoNote = new RepoNote(noteId, note, System.currentTimeMillis(), repoId);

        addUpdateNote.execute(repoNote, new DisposableSingleObserver<ResponseHolder<RepoNote>>() {
            @Override
            public void onSuccess(ResponseHolder<RepoNote> repoNoteResponseHolder) {
                repoNoteLiveData.postValue(repoNoteResponseHolder);

                dispose();
            }

            @Override
            public void onError(Throwable e) {
                repoNoteLiveData.postValue(ResponseHolder.error(e));

                dispose();
            }
        });
    }

    public void fetchRepoNote(int repoId) {
        fetchRepoNote.execute(repoId, new DisposableObserver<ResponseHolder<RepoNote>>() {
            @Override
            public void onNext(ResponseHolder<RepoNote> repoNoteResponseHolder) {
                repoNoteLiveData.postValue(repoNoteResponseHolder);
            }

            @Override
            public void onError(Throwable e) {
                repoNoteLiveData.postValue(ResponseHolder.error(e));
            }

            @Override
            public void onComplete() {
                dispose();
            }
        });
    }

    @Override
    protected void onCleared() {

        fetchRepoNote.dispose();
        addUpdateNote.dispose();

        super.onCleared();
    }
}
