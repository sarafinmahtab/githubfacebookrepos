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
import com.android.githubfacebookrepos.model.mapped.RepoNote;
import com.google.gson.GsonBuilder;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.plugins.RxJavaPlugins;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/*
 * Created by Arafin Mahtab on 7/19/20.
 */

public class AddUpdateNoteTest {

    private AddUpdateNote addUpdateNoteSUT;
    private FetchRepoNote fetchRepoNoteSUT;

    private InMemoryRealmService inMemoryRealmService;

    @Before
    public void setUp() throws Exception {

        GsonConverterFactory gsonConverterFactory = GsonConverterFactory.create(
                new GsonBuilder()
                        .disableHtmlEscaping()
                        .create()
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

        addUpdateNoteSUT = new AddUpdateNote(mainRepo);
        fetchRepoNoteSUT = new FetchRepoNote(mainRepo);
    }

    @Test
    public void addUpdateNoteForRepo_validRequest_successRepoNoteReturned() {

        // Params
        RepoNote repoNote = new RepoNote(UUID.randomUUID().toString(), "This is a demo note", System.currentTimeMillis(), 1);

        // Execution
        ResponseHolder<RepoNote> responseHolder = addUpdateNoteSUT.executeImmediate(repoNote);

        // Validation
        Assert.assertEquals(repoNote, responseHolder.getData());
        Assert.assertEquals(ResponseHolder.Status.SUCCESS, responseHolder.getStatus());

        ResponseHolder<RepoNote> repoNoteResponseHolder = fetchRepoNoteSUT.executeImmediate(1);
        Assert.assertEquals(repoNote.getNoteId(), repoNote.getNoteId());
        Assert.assertEquals(ResponseHolder.Status.SUCCESS, repoNoteResponseHolder.getStatus());
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
