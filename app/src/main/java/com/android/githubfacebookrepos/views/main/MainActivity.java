package com.android.githubfacebookrepos.views.main;

/*
 * Created by Arafin Mahtab on 6/15/20.
 */


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.githubfacebookrepos.MainApplication;
import com.android.githubfacebookrepos.R;
import com.android.githubfacebookrepos.databinding.ActivityMainBinding;
import com.android.githubfacebookrepos.di.ViewModelFactory;
import com.android.githubfacebookrepos.helpers.CommonUtil;
import com.android.githubfacebookrepos.helpers.ResponseHolder;
import com.android.githubfacebookrepos.model.mapped.GithubRepoMin;
import com.android.githubfacebookrepos.views.main.adapter.GitReposAdapter;

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

    private GitReposAdapter adapter;

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        // Injecting Activity from AppComponent hierarchy
        MainApplication application = (MainApplication) getApplicationContext();
        application.getAppComponent().mainComponent().create().inject(this);

        // Initializing viewModel from injected ViewModelFactory
        viewModel = new ViewModelProvider(this, viewModelFactory).get(MainViewModel.class);

        adapter = new GitReposAdapter();

        // Initializing Views with DataBinding
        binding.gitRepoRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.gitRepoRecyclerView.setAdapter(adapter);


        // Initializing Observers before fetching data
        viewModel.orgRepoListLiveData.observe(this, orgRepoListObserver);


        viewModel.fetchGithubRepos(orgRepoName, CommonUtil.isNetworkConnectionAvailable(this));
    }


    private Observer<ResponseHolder<ArrayList<GithubRepoMin>>> orgRepoListObserver = orgRepoListResponseHolder -> {
        switch (orgRepoListResponseHolder.getStatus()) {
            case LOADING:

                binding.progressBar.setVisibility(View.VISIBLE);

                break;
            case SUCCESS:

                adapter.submitRepoList(orgRepoListResponseHolder.getData());
                binding.progressBar.setVisibility(View.GONE);

                break;
            case ERROR:

                adapter.submitRepoList(new ArrayList<>());
                binding.progressBar.setVisibility(View.GONE);

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
