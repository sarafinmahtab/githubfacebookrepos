package com.android.githubfacebookrepos.views.main;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.githubfacebookrepos.MainApplication;
import com.android.githubfacebookrepos.R;
import com.android.githubfacebookrepos.base.ItemClickListener;
import com.android.githubfacebookrepos.databinding.ActivityMainBinding;
import com.android.githubfacebookrepos.di.ViewModelFactory;
import com.android.githubfacebookrepos.helpers.CommonUtil;
import com.android.githubfacebookrepos.helpers.ResponseHolder;
import com.android.githubfacebookrepos.model.mapped.GithubRepoMin;
import com.android.githubfacebookrepos.views.main.adapter.GitReposAdapter;
import com.android.githubfacebookrepos.views.notes.AddUpdateNoteActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

import java.util.ArrayList;

import javax.inject.Inject;


public class MainActivity extends AppCompatActivity implements ItemClickListener {

    private final String TAG = this.getClass().getName();

    public static final int REPO_CLICKED = 0;

    private String orgRepoName = "facebook";

    private GitReposAdapter adapter;
    private ActivityMainBinding binding;
    private MainViewModel viewModel;

    @Inject
    ViewModelFactory viewModelFactory;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        MainApplication application = (MainApplication) getApplicationContext();
        application.getAppComponent().mainComponent().create().inject(this);

        viewModel = new ViewModelProvider(this, viewModelFactory).get(MainViewModel.class);

        RequestManager glideRequestManager = Glide.with(this);
        adapter = new GitReposAdapter(glideRequestManager, this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.gitRepoRecyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, layoutManager.getOrientation());
        binding.gitRepoRecyclerView.addItemDecoration(dividerItemDecoration);

        binding.gitRepoRecyclerView.setAdapter(adapter);


        viewModel.orgRepoListLiveData.observe(this, orgRepoListObserver);

        viewModel.fetchGithubRepos(orgRepoName, CommonUtil.isNetworkConnectionAvailable(this));
    }

    @Override
    public void onItemClick(int position, int viewType, int actionType, Object value) {
        if (actionType == MainActivity.REPO_CLICKED) {
            GithubRepoMin githubRepoMin = (GithubRepoMin) value;
            AddUpdateNoteActivity.start(this, githubRepoMin);
        }
    }

    private final Observer<ResponseHolder<ArrayList<GithubRepoMin>>> orgRepoListObserver = orgRepoListResponseHolder -> {
        switch (orgRepoListResponseHolder.getStatus()) {
            case LOADING:
                binding.progressBar.setVisibility(View.VISIBLE);
                binding.emptyViewGroup.setVisibility(View.GONE);

                break;
            case SUCCESS:
                adapter.submitRepoList(orgRepoListResponseHolder.getData());
                binding.progressBar.setVisibility(View.GONE);

                binding.emptyViewGroup.setVisibility(
                        orgRepoListResponseHolder.getData().isEmpty() ?
                                View.VISIBLE :
                                View.GONE);

                binding.emptyTextView.setText(getString(R.string.no_repo_found));

                break;
            case ERROR:
                adapter.submitRepoList(new ArrayList<>());
                binding.progressBar.setVisibility(View.GONE);
                binding.emptyViewGroup.setVisibility(View.VISIBLE);

                String logMessage = CommonUtil.getErrorMessage(orgRepoListResponseHolder.getError());
                Log.w(TAG, logMessage);

                String errorMessage = CommonUtil.prepareErrorMessage(this, orgRepoListResponseHolder.getError());
                binding.emptyTextView.setText(errorMessage);

                break;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.make_bug) {
            makeBug();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void makeBug() {
        throw new RuntimeException("This is a crash");
    }

    @Override
    protected void onDestroy() {
        viewModel.orgRepoListLiveData.removeObserver(orgRepoListObserver);

        super.onDestroy();
    }
}
