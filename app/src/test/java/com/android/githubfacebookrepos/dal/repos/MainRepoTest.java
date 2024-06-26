package com.android.githubfacebookrepos.dal.repos;

import com.android.githubfacebookrepos.dal.db.LocalDataSource;
import com.android.githubfacebookrepos.dal.db.LocalDataSourceImpl;
import com.android.githubfacebookrepos.dal.db.RealmRxService;
import com.android.githubfacebookrepos.dal.network.ApiService;
import com.android.githubfacebookrepos.dal.network.RemoteDataSource;
import com.android.githubfacebookrepos.dal.network.RemoteDataSourceImpl;
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
import java.util.List;
import java.util.UUID;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.observers.TestObserver;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;


/*
 * Created by Arafin Mahtab on 6/22/20.
 */

@RunWith(MockitoJUnitRunner.class)
public class MainRepoTest {

    private RealmRxService realmRxService;
    private ApiService apiService;
    private MainRepo mainRepoSUT;

    // Test Data
    private ArrayList<GithubRepo> githubRepos;
    private ArrayList<GithubRepoMin> githubRepoMins;

    private RepoNote repoNote;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        RxAndroidPlugins.reset();
        RxAndroidPlugins.setMainThreadSchedulerHandler(scheduler -> Schedulers.trampoline());

        RxJavaPlugins.reset();
        RxJavaPlugins.setIoSchedulerHandler(scheduler -> Schedulers.trampoline());
        RxJavaPlugins.setComputationSchedulerHandler(scheduler -> Schedulers.trampoline());
        RxJavaPlugins.setNewThreadSchedulerHandler(scheduler -> Schedulers.trampoline());

        // Dependencies
        apiService = Mockito.mock(ApiService.class);
        realmRxService = Mockito.mock(RealmRxService.class);
        RemoteDataSource remoteDataSource = new RemoteDataSourceImpl(apiService);
        LocalDataSource localDataSource = new LocalDataSourceImpl(realmRxService);
        mainRepoSUT = new MainRepoImpl(remoteDataSource, localDataSource);

        populateTestData();
    }


    @Test
    public void fetchOrganizationReposFromServer_validRequest_returnedResponse() {

        // Params
        String orgName = "facebook";

        // Mock Response
        Mockito.doReturn(
                Single.just(githubRepos)
        ).when(apiService).fetchOrganizationRepos(orgName);


        // Trigger
        TestObserver<List<GithubRepo>> responseObserver =
                mainRepoSUT.fetchOrganizationReposFromServer(orgName).test();

        // Validation
        responseObserver.assertValue(githubRepos);

        // Clean Up
        responseObserver.dispose();
    }

    @Test(expected = Exception.class)
    public void fetchOrganizationReposFromServer_invalidRequest_returnedException() {

        // Trigger
        TestObserver<List<GithubRepo>> responseObserver =
                apiService.fetchOrganizationRepos("").test();

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
        ).when(apiService).fetchOrganizationRepos(orgName);


        // Trigger
        TestObserver<List<GithubRepo>> responseObserver =
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
        ).when(realmRxService).fetchCachedGithubRepo(orgName);


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
        ).when(realmRxService).saveOrganizationRepos(githubRepoMins);


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
        ).when(realmRxService).addUpdateNote(repoNote);

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
        ).when(realmRxService).fetchRepoNote(githubRepoMins.get(0).getRepoId());

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
