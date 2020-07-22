package com.android.githubfacebookrepos.usecase;

import com.android.githubfacebookrepos.dal.db.InMemoryRealmService;
import com.android.githubfacebookrepos.dal.db.LocalDataSource;
import com.android.githubfacebookrepos.dal.db.MockLocalDataSourceImpl;
import com.android.githubfacebookrepos.dal.network.RemoteDataSource;
import com.android.githubfacebookrepos.dal.network.RemoteDataSourceImpl;
import com.android.githubfacebookrepos.dal.repos.MainRepo;
import com.android.githubfacebookrepos.dal.repos.MainRepoImpl;
import com.android.githubfacebookrepos.helpers.ResponseHolder;
import com.android.githubfacebookrepos.model.api.GithubRepo;
import com.android.githubfacebookrepos.model.api.Owner;
import com.android.githubfacebookrepos.model.mapped.GithubRepoMin;
import com.android.githubfacebookrepos.model.params.ParamFetchOrgRepo;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

import io.reactivex.observers.TestObserver;


/*
 * Created by Arafin Mahtab on 7/22/20.
 */

public class SaveOrgReposTest {

    private FetchOrgRepos fetchOrgRepos;
    private SaveOrgRepos saveOrgReposSUT;

    private InMemoryRealmService inMemoryRealmService;

    private GithubRepoMin githubRepoMin;

    @Before
    public void setUp() throws Exception {

        // Dependencies
        prepareGithubRepoData();

        inMemoryRealmService = new InMemoryRealmService();
        RemoteDataSource remoteDataSource = Mockito.mock(RemoteDataSourceImpl.class);
        LocalDataSource localDataSource = new MockLocalDataSourceImpl(inMemoryRealmService);
        MainRepo mainRepo = new MainRepoImpl(remoteDataSource, localDataSource);

        saveOrgReposSUT = new SaveOrgRepos(mainRepo);
        fetchOrgRepos = new FetchOrgRepos(mainRepo, saveOrgReposSUT);
    }

    @Test
    public void saveOrgRepos_validRequest_completed() {
        ArrayList<GithubRepoMin> githubRepoMins = new ArrayList<>();
        githubRepoMins.add(githubRepoMin);

        TestObserver<Void> gitRepoMinTestObserver = new TestObserver<>();
        saveOrgReposSUT.buildUseCaseCompletable(githubRepoMins)
                .subscribe(gitRepoMinTestObserver);

        // Validation
        gitRepoMinTestObserver.assertComplete();
    }

    @Test
    public void fetchOrgRepos_validRequest_exists() {
        // Params
        ParamFetchOrgRepo paramFetchOrgRepo = new ParamFetchOrgRepo(true, false, githubRepoMin.getOrgName());

        // Execution
        ResponseHolder<ArrayList<GithubRepoMin>> responseHolder = fetchOrgRepos.executeImmediate(paramFetchOrgRepo);

        // Validation
        Assert.assertEquals(ResponseHolder.Status.SUCCESS, responseHolder.getStatus());
        Assert.assertTrue(responseHolder.getData().contains(githubRepoMin));
    }

    public void prepareGithubRepoData() {
        Owner owner = new Owner();
        owner.setId(1342004);
        owner.setLogin("google");
        owner.setAvatarUrl("https://avatars1.githubusercontent.com/u/1342004?v=4");

        GithubRepo githubRepo = new GithubRepo();
        githubRepo.setId(1936771);
        githubRepo.setName("truth");
        githubRepo.setPrivate(false);
        githubRepo.setOwner(owner);
        githubRepo.setDescription("Fluent assertions for Java and Android");
        githubRepo.setFork(false);
        githubRepo.setLanguage("Java");
        githubRepo.setUrl("https://api.github.com/repos/google/truth");
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
}
