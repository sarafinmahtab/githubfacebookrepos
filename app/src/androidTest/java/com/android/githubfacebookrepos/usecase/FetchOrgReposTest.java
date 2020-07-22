package com.android.githubfacebookrepos.usecase;

import com.android.githubfacebookrepos.dal.db.InMemoryRealmService;
import com.android.githubfacebookrepos.dal.db.LocalDataSource;
import com.android.githubfacebookrepos.dal.db.MockLocalDataSourceImpl;
import com.android.githubfacebookrepos.dal.network.ApiService;
import com.android.githubfacebookrepos.dal.network.RemoteDataSource;
import com.android.githubfacebookrepos.dal.network.RemoteDataSourceImpl;
import com.android.githubfacebookrepos.dal.repos.MainRepo;
import com.android.githubfacebookrepos.dal.repos.MainRepoImpl;
import com.android.githubfacebookrepos.data.ServerConstant;
import com.android.githubfacebookrepos.helpers.ResponseHolder;
import com.android.githubfacebookrepos.model.api.GithubRepo;
import com.android.githubfacebookrepos.model.api.Owner;
import com.android.githubfacebookrepos.model.mapped.GithubRepoMin;
import com.android.githubfacebookrepos.model.params.ParamFetchOrgRepo;
import com.google.gson.GsonBuilder;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.observers.TestObserver;
import io.reactivex.plugins.RxJavaPlugins;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/*
 * Created by Arafin Mahtab on 7/12/20.
 */

public class FetchOrgReposTest {

    private FetchOrgRepos fetchOrgReposSUT;
    private SaveOrgRepos saveOrgRepos;

    private InMemoryRealmService inMemoryRealmService;

    private GithubRepoMin githubRepoMin;

    @Before
    public void setUp() throws Exception {

        // Dependencies
        prepareGithubRepoData();

        GsonConverterFactory gsonConverterFactory = GsonConverterFactory.create(
                new GsonBuilder().disableHtmlEscaping().create()
        );

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ServerConstant.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(gsonConverterFactory)
                .build();

        ApiService apiService = retrofit.create(ApiService.class);

        inMemoryRealmService = new InMemoryRealmService();
        RemoteDataSource remoteDataSource = new RemoteDataSourceImpl(apiService);
        LocalDataSource localDataSource = new MockLocalDataSourceImpl(inMemoryRealmService);
        MainRepo mainRepo = new MainRepoImpl(remoteDataSource, localDataSource);

        saveOrgRepos = new SaveOrgRepos(mainRepo);
        fetchOrgReposSUT = new FetchOrgRepos(mainRepo, saveOrgRepos);
    }


    @Test
    public void fetchReposWithObserver_validRequest_gitRepoMinReturned() {

        // Params
        ParamFetchOrgRepo paramFetchOrgRepo = new ParamFetchOrgRepo(true, true, "facebook");

        // Trigger
        TestObserver<ResponseHolder<ArrayList<GithubRepoMin>>> gitRepoMinTestObserver = new TestObserver<>();

        // Execution
        fetchOrgReposSUT.buildUseCaseSingle(paramFetchOrgRepo)
                .subscribe(gitRepoMinTestObserver);

        // Validation
        Assert.assertEquals(ResponseHolder.Status.SUCCESS, gitRepoMinTestObserver.values().get(0).getStatus());

        // Clean Up
        gitRepoMinTestObserver.dispose();
    }


    @Test
    public void fetchReposFromServer_validRequest_gitRepoMinReturned() {

        // Params
        ParamFetchOrgRepo paramFetchOrgRepo = new ParamFetchOrgRepo(true, true, "facebook");


        // Execution
        ResponseHolder<ArrayList<GithubRepoMin>> responseHolder = fetchOrgReposSUT.executeImmediate(paramFetchOrgRepo);

        // Validation
        Assert.assertEquals(ResponseHolder.Status.SUCCESS, responseHolder.getStatus());
    }

    @Test
    public void fetchCachedRepos_validRequest_gitRepoMinReturned() {

        // Params
        ParamFetchOrgRepo paramFetchOrgRepo = new ParamFetchOrgRepo(true, false, "facebook");

        // Execution from Realm Database
        ResponseHolder<ArrayList<GithubRepoMin>> responseHolder = fetchOrgReposSUT.executeImmediate(paramFetchOrgRepo);

        // Validation with actual data
        Assert.assertEquals(ResponseHolder.Status.SUCCESS, responseHolder.getStatus());
    }

    @Test
    public void fetchCachedRepos_newRepoAdd_validNumberOfRepoReturned() {

        // Params
        ParamFetchOrgRepo paramFetchOrgRepo = new ParamFetchOrgRepo(true, false, "facebook");

        // Execution
        ResponseHolder<ArrayList<GithubRepoMin>> oldResponseHolder = fetchOrgReposSUT.executeImmediate(paramFetchOrgRepo);

        // Validation
        Assert.assertEquals(ResponseHolder.Status.SUCCESS, oldResponseHolder.getStatus());

        // New Repo Insertion
        ArrayList<GithubRepoMin> githubRepoMins = new ArrayList<>();
        githubRepoMins.add(githubRepoMin);
        saveOrgRepos.execute(githubRepoMins);

        // Execution
        ResponseHolder<ArrayList<GithubRepoMin>> newResponseHolder = fetchOrgReposSUT.executeImmediate(paramFetchOrgRepo);

        // Validation
        Assert.assertEquals(oldResponseHolder.getData().size() + githubRepoMins.size(), newResponseHolder.getData().size());
    }

    @Test
    public void fetchReposFromServer_invalidRequest_gitRepoResponseNullReturned() {

        // Execution
        ResponseHolder<ArrayList<GithubRepoMin>> responseHolder = fetchOrgReposSUT.executeImmediate(
                new ParamFetchOrgRepo(true, true, null));

        // Validation
        Assert.assertNull(responseHolder.getData());
        Assert.assertEquals(ResponseHolder.Status.ERROR, responseHolder.getStatus());
    }

    public void prepareGithubRepoData() {
        Owner owner = new Owner();
        owner.setId(1342004);
        owner.setLogin("facebook");
        owner.setAvatarUrl("https://avatars1.githubusercontent.com/u/1342004?v=4");

        GithubRepo githubRepo = new GithubRepo();
        githubRepo.setId(1936771);
        githubRepo.setName("fresco");
        githubRepo.setPrivate(false);
        githubRepo.setOwner(owner);
        githubRepo.setDescription("Image Caching Library for Java, Kotlin and Android");
        githubRepo.setFork(false);
        githubRepo.setLanguage("Java");
        githubRepo.setUrl("https://api.github.com/repos/facebook/fresco");
        githubRepo.setUpdatedAt("2020-07-20T16:46:31Z");


        githubRepoMin = new GithubRepoMin(
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
    }

    @After
    public void tearDown() throws Exception {
        inMemoryRealmService.close();
    }

    @AfterClass
    public static void afterClass() throws Exception {
        RxJavaPlugins.reset();
        RxAndroidPlugins.reset();
    }
}
