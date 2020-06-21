package com.android.githubfacebookrepos.dal.db;

/*
 * Created by Arafin Mahtab on 6/18/20.
 */

import com.android.githubfacebookrepos.helpers.ResponseHolder;
import com.android.githubfacebookrepos.model.mapped.GithubRepoMin;
import com.android.githubfacebookrepos.model.mapped.RepoNote;

import java.util.ArrayList;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

public interface LocalDataSource {

    Single<ArrayList<GithubRepoMin>> fetchOrganizationRepos(String orgName);

    Completable saveOrganizationRepos(ArrayList<GithubRepoMin> githubRepoMins);

    Single<ResponseHolder<RepoNote>> addUpdateNoteForRepo(RepoNote repoNote);

    Observable<ResponseHolder<RepoNote>> fetchRepoNote(int repoId);
}
