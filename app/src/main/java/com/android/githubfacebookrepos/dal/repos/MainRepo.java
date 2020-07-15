package com.android.githubfacebookrepos.dal.repos;

/*
 * Created by Arafin Mahtab on 6/16/20.
 */

import com.android.githubfacebookrepos.helpers.ResponseHolder;
import com.android.githubfacebookrepos.model.api.GithubRepo;
import com.android.githubfacebookrepos.model.mapped.GithubRepoMin;
import com.android.githubfacebookrepos.model.mapped.RepoNote;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

public interface MainRepo {

    Single<List<GithubRepo>> fetchOrganizationReposFromServer(String orgName);

    Single<ArrayList<GithubRepoMin>> fetchCachedOrganizationRepos(String orgName);

    Completable saveOrganizationReposLocally(ArrayList<GithubRepoMin> githubRepoMins);

    Single<ResponseHolder<RepoNote>> addUpdateNoteForRepo(RepoNote repoNote);

    Observable<ResponseHolder<RepoNote>> fetchRepoNote(int repoId);
}
