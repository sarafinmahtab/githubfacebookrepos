package com.android.githubfacebookrepos.dal.repos;

import com.android.githubfacebookrepos.helpers.ResponseHolder;
import com.android.githubfacebookrepos.model.api.GithubRepo;
import com.android.githubfacebookrepos.model.api.Owner;
import com.android.githubfacebookrepos.model.mapped.GithubRepoMin;
import com.android.githubfacebookrepos.model.mapped.RepoNote;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.observers.TestObserver;
import io.reactivex.plugins.RxJavaPlugins;


/*
 * Created by Arafin Mahtab on 6/22/20.
 */

@RunWith(MockitoJUnitRunner.class)
public class MainRepoTest {

    private MainRepo mainRepoSUT;

    // Test Data
    private ArrayList<GithubRepo> githubRepos;
    private ArrayList<GithubRepoMin> githubRepoMins;

    private RepoNote repoNote;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        // Dependencies
        mainRepoSUT = Mockito.mock(MainRepo.class);

        populateTestData();
    }


    @Test
    public void fetchOrganizationReposFromServer_validRequest_returnedResponse() {

        // Params
        String orgName = "facebook";

        // Mock Response
        Mockito.doReturn(
                Single.just(githubRepos)
        ).when(mainRepoSUT).fetchOrganizationReposFromServer(orgName);


        // Trigger
        TestObserver<ArrayList<GithubRepo>> responseObserver =
                mainRepoSUT.fetchOrganizationReposFromServer(orgName).test();

        // Validation
        responseObserver.assertValue(githubRepos);

        // Clean Up
        responseObserver.dispose();
    }

    @Test(expected = Exception.class)
    public void fetchOrganizationReposFromServer_invalidRequest_returnedException() {

        // Trigger
        TestObserver<ArrayList<GithubRepo>> responseObserver =
                mainRepoSUT.fetchOrganizationReposFromServer("").test();

        // Validation
        responseObserver.assertError(Exception.class);

        // Clean Up
        responseObserver.dispose();
    }

    @Test
    public void fetchOrganizationReposFromServer_throws_returnedException() {
        RuntimeException runtimeException = new RuntimeException();

        // Params
        String orgName = "facebook";

        // Mock Response
        Mockito.doReturn(
                Single.error(runtimeException)
        ).when(mainRepoSUT).fetchOrganizationReposFromServer(orgName);


        // Trigger
        TestObserver<ArrayList<GithubRepo>> responseObserver =
                mainRepoSUT.fetchOrganizationReposFromServer(orgName).test();

        // Validation
        responseObserver.assertError(runtimeException);

        // Clean Up
        responseObserver.dispose();
    }

    @Test
    public void fetchCachedOrganizationRepos_validRequest_returnedResponse() {

        // Params
        String orgName = "facebook";

        // Mock Response
        Mockito.doReturn(
                Single.just(githubRepoMins)
        ).when(mainRepoSUT).fetchCachedOrganizationRepos(orgName);


        // Trigger
        TestObserver<ArrayList<GithubRepoMin>> responseObserver =
                mainRepoSUT.fetchCachedOrganizationRepos(orgName).test();

        // Validation
        responseObserver.assertValue(githubRepoMins);

        // Clean Up
        responseObserver.dispose();
    }

    @Test
    public void saveOrganizationReposLocally_validRequest_returnedResponse() {

        // Mock Response
        Mockito.doReturn(
                Completable.complete()
        ).when(mainRepoSUT).saveOrganizationReposLocally(githubRepoMins);


        // Trigger
        TestObserver<Void> responseObserver =
                mainRepoSUT.saveOrganizationReposLocally(githubRepoMins).test();

        // Validation
        responseObserver.assertComplete();

        // Clean Up
        responseObserver.dispose();
    }

    @Test
    public void addUpdateNoteForRepo_validRequest_returnedResponse() {

        ResponseHolder<RepoNote> responseHolder = ResponseHolder.success(repoNote);

        // Mock Response
        Mockito.doReturn(
                Single.just(responseHolder)
        ).when(mainRepoSUT).addUpdateNoteForRepo(repoNote);

        // Trigger
        TestObserver<ResponseHolder<RepoNote>> responseObserver =
                mainRepoSUT.addUpdateNoteForRepo(repoNote).test();

        // Validation
        responseObserver.assertValue(responseHolder);

        // Clean Up
        responseObserver.dispose();
    }

    @Test
    public void fetchRepoNote_validRequest_returnedResponse() {
        ResponseHolder<RepoNote> responseHolder = ResponseHolder.success(repoNote);

        // Mock Response
        Mockito.doReturn(
                Observable.just(responseHolder)
        ).when(mainRepoSUT).fetchRepoNote(githubRepoMins.get(0).getRepoId());

        // Trigger
        TestObserver<ResponseHolder<RepoNote>> responseObserver =
                mainRepoSUT.fetchRepoNote(githubRepoMins.get(0).getRepoId()).test();

        // Validation
        responseObserver.assertValue(responseHolder);

        // Clean Up
        responseObserver.dispose();


    }


    private void populateTestData() {

        Owner owner = new Owner();
        owner.setId(69631);
        owner.setLogin("facebook");
        owner.setAvatarUrl("https://avatars3.githubusercontent.com/u/69631?v=4");

        GithubRepo githubRepo = new GithubRepo();
        githubRepo.setId(165883);
        githubRepo.setName("codemod");
        githubRepo.setPrivate(false);
        githubRepo.setOwner(owner);
        githubRepo.setDescription("Codemod is a tool/library to assist you with large-scale codebase refactors");
        githubRepo.setFork(false);
        githubRepo.setLanguage("Python");
        githubRepo.setUpdatedAt("2020-06-21T20:14:37Z");


        GithubRepoMin githubRepoMin = new GithubRepoMin(
                githubRepo.getId(),
                githubRepo.getName(),
                githubRepo.isPrivate(),
                githubRepo.getOwner().getId(),
                githubRepo.getOwner().getLogin(),
                githubRepo.getOwner().getAvatarUrl(),
                githubRepo.getUpdatedAt(),
                githubRepo.getLanguage(),
                githubRepo.getDescription(),
                githubRepo.isFork()
        );


        githubRepos = new ArrayList<>();
        githubRepos.add(githubRepo);

        githubRepoMins = new ArrayList<>();
        githubRepoMins.add(githubRepoMin);


        repoNote = new RepoNote(
                UUID.randomUUID().toString(),
                "Nice Repo",
                new Date().getTime(),
                githubRepoMin.getRepoId());
    }


    @AfterClass
    public static void afterClass() throws Exception {
        RxJavaPlugins.reset();
        RxAndroidPlugins.reset();
    }
}
