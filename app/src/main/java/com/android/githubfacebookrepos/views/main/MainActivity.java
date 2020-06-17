package com.android.githubfacebookrepos.views.main;

/*
 * Created by Arafin Mahtab on 6/15/20.
 */


import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.android.githubfacebookrepos.MainApplication;
import com.android.githubfacebookrepos.R;
import com.android.githubfacebookrepos.di.ViewModelFactory;
import com.android.githubfacebookrepos.helpers.CommonUtil;
import com.android.githubfacebookrepos.helpers.ResponseHolder;
import com.android.githubfacebookrepos.model.GithubRepo;

import java.util.ArrayList;

import javax.inject.Inject;


/**
 * Activity which will show the list of Github facebook repos
 */
public class MainActivity extends AppCompatActivity {

    private final String TAG = this.getClass().getName();

    private String orgRepoName = "facebook";

    private MainViewModel viewModel;

    @Inject
    ViewModelFactory viewModelFactory;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Injecting Activity from AppComponent hierarchy
        MainApplication application = (MainApplication) getApplicationContext();
        application.getAppComponent().mainComponent().create().inject(this);

        // Initializing viewModel from injected ViewModelFactory
        viewModel = new ViewModelProvider(this, viewModelFactory).get(MainViewModel.class);


        viewModel.orgRepoListLiveData.observe(this, orgRepoListObserver);


        viewModel.fetchGithubRepos(orgRepoName);
    }


    private Observer<ResponseHolder<ArrayList<GithubRepo>>> orgRepoListObserver = orgRepoListResponseHolder -> {
        switch (orgRepoListResponseHolder.getStatus()) {
            case LOADING:

                break;
            case SUCCESS:

                break;
            case ERROR:

                String warningMessage = CommonUtil.prepareErrorMessage(orgRepoListResponseHolder.getError());
                Log.w(TAG, warningMessage);
                Toast.makeText(this, warningMessage, Toast.LENGTH_LONG).show();

                break;
        }
    };

    @Override
    protected void onDestroy() {

        viewModel.orgRepoListLiveData.removeObserver(orgRepoListObserver);

        super.onDestroy();
    }
}
