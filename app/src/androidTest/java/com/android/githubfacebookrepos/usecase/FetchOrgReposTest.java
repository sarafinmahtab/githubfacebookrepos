package com.android.githubfacebookrepos.usecase;

import com.android.githubfacebookrepos.dal.db.LocalDataSource;
import com.android.githubfacebookrepos.dal.db.LocalDataSourceImpl;
import com.android.githubfacebookrepos.dal.db.RealmRxService;
import com.android.githubfacebookrepos.dal.network.ApiService;
import com.android.githubfacebookrepos.dal.network.RemoteDataSource;
import com.android.githubfacebookrepos.dal.network.RemoteDataSourceImpl;
import com.android.githubfacebookrepos.dal.repos.MainRepo;
import com.android.githubfacebookrepos.dal.repos.MainRepoImpl;
import com.android.githubfacebookrepos.data.ServerConstant;
import com.android.githubfacebookrepos.helpers.ResponseHolder;
import com.android.githubfacebookrepos.model.mapped.GithubRepoMin;
import com.android.githubfacebookrepos.model.params.ParamFetchOrgRepo;
import com.google.gson.GsonBuilder;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

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


    @Before
    public void setUp() throws Exception {

        // Dependencies

        GsonConverterFactory gsonConverterFactory = GsonConverterFactory.create(
                new GsonBuilder().disableHtmlEscaping().create()
        );

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ServerConstant.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(gsonConverterFactory)
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        RealmRxService realmRxService = new RealmRxService();
        RemoteDataSource remoteDataSource = new RemoteDataSourceImpl(apiService);
        LocalDataSource localDataSource = new LocalDataSourceImpl(realmRxService);
        MainRepo mainRepo = new MainRepoImpl(remoteDataSource, localDataSource);

        SaveOrgRepos saveOrgRepos = Mockito.mock(SaveOrgRepos.class);
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
        Assert.assertEquals(gitRepoMinTestObserver.values().get(0).getStatus(), ResponseHolder.Status.SUCCESS);

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
        Assert.assertEquals(responseHolder.getStatus(), ResponseHolder.Status.SUCCESS);
    }

    @Test
    public void fetchCachedRepos_validRequest_gitRepoMinReturned() {

        // Params
        ParamFetchOrgRepo paramFetchOrgRepo = new ParamFetchOrgRepo(true, false, "facebook");

        // Execution from Realm Database
        ResponseHolder<ArrayList<GithubRepoMin>> responseHolder = fetchOrgReposSUT.executeImmediate(paramFetchOrgRepo);

        // Validation with actual data
        Assert.assertEquals(responseHolder.getStatus(), ResponseHolder.Status.SUCCESS);
    }

    @Test
    public void fetchReposFromServer_validRequest_gitRepoResponseNotEmpty() {

        // Params
        ParamFetchOrgRepo paramFetchOrgRepo = new ParamFetchOrgRepo(true, true, "facebook");

        // Execution
        ResponseHolder<ArrayList<GithubRepoMin>> responseHolder = fetchOrgReposSUT.executeImmediate(paramFetchOrgRepo);

        // Validation
        Assert.assertEquals(responseHolder.getStatus(), ResponseHolder.Status.SUCCESS);
    }

    @Test
    public void fetchReposFromServer_invalidRequest_gitRepoResponseNullReturned() {

        // Execution
        ResponseHolder<ArrayList<GithubRepoMin>> responseHolder = fetchOrgReposSUT.executeImmediate(
                new ParamFetchOrgRepo(true, true, null));

        // Validation
        Assert.assertNull(responseHolder.getData());
        Assert.assertEquals(responseHolder.getStatus(), ResponseHolder.Status.ERROR);
    }

    @AfterClass
    public static void afterClass() throws Exception {
        RxJavaPlugins.reset();
        RxAndroidPlugins.reset();
    }
}
