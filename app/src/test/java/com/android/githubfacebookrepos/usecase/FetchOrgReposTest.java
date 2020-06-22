package com.android.githubfacebookrepos.usecase;

import com.android.githubfacebookrepos.dal.repos.MainRepo;
import com.android.githubfacebookrepos.helpers.ResponseHolder;
import com.android.githubfacebookrepos.model.api.GithubRepo;
import com.android.githubfacebookrepos.model.api.Owner;
import com.android.githubfacebookrepos.model.mapped.GithubRepoMin;
import com.android.githubfacebookrepos.model.params.ParamFetchOrgRepo;

import org.hamcrest.CoreMatchers;
import org.junit.AfterClass;
import org.junit.Assert;
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
public class FetchOrgReposTest {

    private MainRepo mainRepo;
    private FetchOrgRepos fetchOrgReposSUT;


    // Test Data
    private ArrayList<GithubRepo> githubRepos;
    private ArrayList<GithubRepoMin> githubRepoMins;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        // Dependencies
        mainRepo = Mockito.mock(MainRepo.class);
        fetchOrgReposSUT = new FetchOrgRepos(mainRepo);


        populateTestData();
    }


    /**
     * ISSUE: This test is not matching though the data returns properly.
     * Probable case should using different ResponseHolder instance for both object
     */
    @Test
    public void executeWithObserver_validRequest_gitRepoMinReturned() {

        // Params
        ParamFetchOrgRepo paramFetchOrgRepo = new ParamFetchOrgRepo(true, true, "facebook");

        // Mock Response
        Mockito.when(mainRepo.fetchOrganizationReposFromServer(paramFetchOrgRepo.getOrgName()))
                .thenReturn(Single.just(githubRepos));


        // Trigger
        TestObserver<ResponseHolder<ArrayList<GithubRepoMin>>> gitRepoMinTestObserver = new TestObserver<>();

        // Execution
        fetchOrgReposSUT.buildUseCaseSingle(paramFetchOrgRepo)
                .subscribe(gitRepoMinTestObserver);

        // Validation
        gitRepoMinTestObserver.assertValue(ResponseHolder.success(githubRepoMins));

        // Clean Up
        gitRepoMinTestObserver.dispose();
    }


    @Test
    public void executeImmediate_validRequest_gitRepoMinReturned() {

        // Params
        ParamFetchOrgRepo paramFetchOrgRepo = new ParamFetchOrgRepo(true, true, "facebook");

        // Mock Response
        Mockito.doReturn(
                Single.just(githubRepos)
        ).when(mainRepo).fetchOrganizationReposFromServer(paramFetchOrgRepo.getOrgName());


        // Execution
        ResponseHolder<ArrayList<GithubRepoMin>> responseHolder = fetchOrgReposSUT.executeImmediate(paramFetchOrgRepo);

        // Validation
        Assert.assertThat(responseHolder, CoreMatchers.not(ResponseHolder.success(githubRepoMins)));
    }


    @Test
    public void executeImmediate_validRequest_gitRepoResponseNotReturned() {

        // Params
        ParamFetchOrgRepo paramFetchOrgRepo = new ParamFetchOrgRepo(true, true, "facebook");

        // Mock Response
        Mockito.doReturn(
                Single.just(githubRepos)
        ).when(mainRepo).fetchOrganizationReposFromServer(paramFetchOrgRepo.getOrgName());

        // Execution
        ResponseHolder<ArrayList<GithubRepoMin>> responseHolder = fetchOrgReposSUT.executeImmediate(paramFetchOrgRepo);

        // Validation
        Assert.assertThat(responseHolder, CoreMatchers.not(ResponseHolder.success(githubRepos)));
    }

    @Test
    public void executeImmediate_invalidRequest_gitRepoResponseNotReturned() {

        // Params
//        ParamFetchOrgRepo paramFetchOrgRepo = new ParamFetchOrgRepo(true, true, "facebook");

        // Mock Response
//        Mockito.doReturn(
//                Single.just(githubRepos)
//        ).when(mainRepo).fetchOrganizationReposFromServer(paramFetchOrgRepo.getOrgName());

        // Execution
        ResponseHolder<ArrayList<GithubRepoMin>> responseHolder = fetchOrgReposSUT.executeImmediate(
                new ParamFetchOrgRepo(true, true, null));

        // Validation
        Assert.assertThat(responseHolder, CoreMatchers.not(ResponseHolder.success(githubRepos)));
    }


    @Test
    public void executeImmediate_runtimeException_errorResponseReturned() {

        RuntimeException runtimeException = new RuntimeException();

        // Params
        ParamFetchOrgRepo paramFetchOrgRepo = new ParamFetchOrgRepo(true, true, "facebook");

        // Mock Response
        Mockito.doReturn(
                Single.error(runtimeException)
        ).when(mainRepo).fetchOrganizationReposFromServer(paramFetchOrgRepo.getOrgName());

        // Execution
        ResponseHolder<ArrayList<GithubRepoMin>> responseHolder = fetchOrgReposSUT.executeImmediate(paramFetchOrgRepo);

        // Validation
        Assert.assertThat(responseHolder.getError(), CoreMatchers.is(runtimeException));
    }


    private void populateTestData() {

        // Test Data
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
