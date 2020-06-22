package com.android.githubfacebookrepos.dal.repos;

import com.android.githubfacebookrepos.model.api.GithubRepo;
import com.android.githubfacebookrepos.model.api.Owner;
import com.android.githubfacebookrepos.model.mapped.GithubRepoMin;
import com.android.githubfacebookrepos.model.params.ParamFetchOrgRepo;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

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

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        // Dependencies
        mainRepoSUT = Mockito.mock(MainRepo.class);

        populateTestData();
    }


    @Test
    public void fetchOrganizationReposFromServer_validRequest_returnedResponse() {
        ParamFetchOrgRepo paramFetchOrgRepo = new ParamFetchOrgRepo(true, true, "facebook");

        Mockito.doReturn(
                Single.just(githubRepos)
        ).when(mainRepoSUT).fetchOrganizationReposFromServer(paramFetchOrgRepo.getOrgName());


        // Trigger
        TestObserver<ArrayList<GithubRepo>> responseObserver =
                mainRepoSUT.fetchOrganizationReposFromServer(paramFetchOrgRepo.getOrgName()).test();

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
        ParamFetchOrgRepo paramFetchOrgRepo = new ParamFetchOrgRepo(true, true, "facebook");

        // Mock Response
        Mockito.doReturn(
                Single.error(runtimeException)
        ).when(mainRepoSUT).fetchOrganizationReposFromServer(paramFetchOrgRepo.getOrgName());


        // Trigger
        TestObserver<ArrayList<GithubRepo>> responseObserver =
                mainRepoSUT.fetchOrganizationReposFromServer(paramFetchOrgRepo.getOrgName()).test();

        // Validation
        responseObserver.assertError(runtimeException);

        // Clean Up
        responseObserver.dispose();
    }

    @Test
    public void fetchCachedOrganizationRepos_validRequest_returnedResponse() {

    }

    @Test
    public void saveOrganizationReposLocally_validRequest_returnedResponse() {

    }

    @Test
    public void addUpdateNoteForRepo_validRequest_returnedResponse() {

    }

    @Test
    public void fetchRepoNote_validRequest_returnedResponse() {

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
    }


    @AfterClass
    public static void afterClass() throws Exception {
        RxJavaPlugins.reset();
        RxAndroidPlugins.reset();
    }
}
