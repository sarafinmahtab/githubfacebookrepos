package com.android.githubfacebookrepos.usecase;

import com.android.githubfacebookrepos.dal.db.LocalDataSource;
import com.android.githubfacebookrepos.dal.db.LocalDataSourceImpl;
import com.android.githubfacebookrepos.dal.db.RealmRxService;
import com.android.githubfacebookrepos.dal.network.ApiService;
import com.android.githubfacebookrepos.dal.network.RemoteDataSource;
import com.android.githubfacebookrepos.dal.network.RemoteDataSourceImpl;
import com.android.githubfacebookrepos.dal.repos.MainRepo;
import com.android.githubfacebookrepos.dal.repos.MainRepoImpl;
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
 * Created by Arafin Mahtab on 7/12/20.
 */

@RunWith(MockitoJUnitRunner.class)
public class FetchOrgReposTest {

    private RealmRxService realmRxService;
    private ApiService apiService;
    private FetchOrgRepos fetchOrgReposSUT;


    // Test Data
    private ArrayList<GithubRepo> githubRepos;
    private ArrayList<GithubRepoMin> githubRepoMins;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        // Dependencies
        apiService = Mockito.mock(ApiService.class);
        realmRxService = new RealmRxService();
        RemoteDataSource remoteDataSource = new RemoteDataSourceImpl(apiService);
        LocalDataSource localDataSource = new LocalDataSourceImpl(realmRxService);
        MainRepo mainRepo = new MainRepoImpl(remoteDataSource, localDataSource);

        SaveOrgRepos saveOrgRepos = Mockito.mock(SaveOrgRepos.class);
        fetchOrgReposSUT = new FetchOrgRepos(mainRepo, saveOrgRepos);


        populateTestData();
    }


    @Test
    public void fetchReposWithObserver_validRequest_gitRepoMinReturned() {

        // Params
        ParamFetchOrgRepo paramFetchOrgRepo = new ParamFetchOrgRepo(true, true, "facebook");

        // Mock Response
        Mockito.when(apiService.fetchOrganizationRepos(paramFetchOrgRepo.getOrgName()))
                .thenReturn(Single.just(githubRepos));


        // Trigger
        TestObserver<ResponseHolder<ArrayList<GithubRepoMin>>> gitRepoMinTestObserver = new TestObserver<>();

        // Execution
        fetchOrgReposSUT.buildUseCaseSingle(paramFetchOrgRepo)
                .subscribe(gitRepoMinTestObserver);

        // Validation
        Assert.assertThat(gitRepoMinTestObserver.values().get(0).getData(), CoreMatchers.is(githubRepoMins));
        Assert.assertEquals(gitRepoMinTestObserver.values().get(0).getStatus(), ResponseHolder.Status.SUCCESS);

        // Clean Up
        gitRepoMinTestObserver.dispose();
    }


    @Test
    public void fetchReposFromServer_validRequest_gitRepoMinReturned() {

        // Params
        ParamFetchOrgRepo paramFetchOrgRepo = new ParamFetchOrgRepo(true, true, "facebook");

        // Mock Response
        Mockito.doReturn(
                Single.just(githubRepos)
        ).when(apiService).fetchOrganizationRepos(paramFetchOrgRepo.getOrgName());


        // Execution
        ResponseHolder<ArrayList<GithubRepoMin>> responseHolder = fetchOrgReposSUT.executeImmediate(paramFetchOrgRepo);

        // Validation
        Assert.assertThat(responseHolder.getData(), CoreMatchers.is(githubRepoMins));
        Assert.assertEquals(responseHolder.getStatus(), ResponseHolder.Status.SUCCESS);
    }

    @Test
    public void fetchCachedRepos_validRequest_gitRepoMinReturned() {

        // Params
        ParamFetchOrgRepo paramFetchOrgRepo = new ParamFetchOrgRepo(true, false, "facebook");

        // Execution from Realm Database
        ResponseHolder<ArrayList<GithubRepoMin>> responseHolder = fetchOrgReposSUT.executeImmediate(paramFetchOrgRepo);

        // Validation with actual data
        Assert.assertTrue(responseHolder.getData().size() > githubRepoMins.size());
        Assert.assertEquals(responseHolder.getStatus(), ResponseHolder.Status.SUCCESS);
    }

    @Test
    public void fetchReposFromServer_validRequest_gitRepoResponseNotReturned() {

        // Params
        ParamFetchOrgRepo paramFetchOrgRepo = new ParamFetchOrgRepo(true, true, "facebook");

        // Mock Response
        Mockito.doReturn(
                Single.just(githubRepos)
        ).when(apiService).fetchOrganizationRepos(paramFetchOrgRepo.getOrgName());

        // Execution
        ResponseHolder<ArrayList<GithubRepoMin>> responseHolder = fetchOrgReposSUT.executeImmediate(paramFetchOrgRepo);

        // Validation
        Assert.assertThat(responseHolder.getData(), CoreMatchers.not(githubRepos));
        Assert.assertEquals(responseHolder.getStatus(), ResponseHolder.Status.SUCCESS);
    }

    @Test
    public void fetchReposFromServer_invalidRequest_gitRepoResponseNotReturned() {

        // Execution
        ResponseHolder<ArrayList<GithubRepoMin>> responseHolder = fetchOrgReposSUT.executeImmediate(
                new ParamFetchOrgRepo(true, true, null));

        // Validation
        Assert.assertThat(responseHolder.getData(), CoreMatchers.not(githubRepos));
        Assert.assertEquals(responseHolder.getStatus(), ResponseHolder.Status.ERROR);
    }


    @Test
    public void fetchReposFromServer_runtimeException_errorResponseReturned() {

        RuntimeException runtimeException = new RuntimeException();

        // Params
        ParamFetchOrgRepo paramFetchOrgRepo = new ParamFetchOrgRepo(true, true, "facebook");

        // Mock Response
        Mockito.doReturn(
                Single.error(runtimeException)
        ).when(apiService).fetchOrganizationRepos(paramFetchOrgRepo.getOrgName());

        // Execution
        ResponseHolder<ArrayList<GithubRepoMin>> responseHolder = fetchOrgReposSUT.executeImmediate(paramFetchOrgRepo);

        // Validation
        Assert.assertThat(responseHolder.getError(), CoreMatchers.is(runtimeException));
        Assert.assertEquals(responseHolder.getStatus(), ResponseHolder.Status.ERROR);
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
