package com.android.githubfacebookrepos.views.notes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.android.githubfacebookrepos.MainApplication;
import com.android.githubfacebookrepos.R;
import com.android.githubfacebookrepos.databinding.ActivityAddUpdateNoteBinding;
import com.android.githubfacebookrepos.di.ViewModelFactory;
import com.android.githubfacebookrepos.model.mapped.GithubRepoMin;

import javax.inject.Inject;

/**
 * Activity which will show add or update note of the repo
 */
public class AddUpdateNoteActivity extends AppCompatActivity {

    private static final String EXTRA_GIT_REPO = "GitRepoMin";

    public static void start(Activity activity, GithubRepoMin githubRepoMin) {
        Intent intent = new Intent(activity, AddUpdateNoteActivity.class);
        intent.putExtra(EXTRA_GIT_REPO, githubRepoMin);
        activity.startActivity(intent);
    }


    private final String TAG = this.getClass().getName();


    private ActivityAddUpdateNoteBinding binding;
    private AddUpdateNoteViewModel viewModel;

    @Inject
    ViewModelFactory viewModelFactory;


    private GithubRepoMin githubRepoMin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_update_note);

        githubRepoMin = getIntent().getParcelableExtra(EXTRA_GIT_REPO);

        setUpToolbar();


        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_update_note);

        // Injecting Activity from AppComponent hierarchy
        MainApplication application = (MainApplication) getApplicationContext();
        application.getAppComponent().addUpdateNoteComponent().create().inject(this);

        // Initializing viewModel from injected ViewModelFactory
        viewModel = new ViewModelProvider(this, viewModelFactory).get(AddUpdateNoteViewModel.class);
    }

    private void setUpToolbar() {
        if (getSupportActionBar() != null) {

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            if (githubRepoMin != null) {
                getSupportActionBar().setTitle(githubRepoMin.getRepoName());
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        super.onBackPressed();
        return true;
    }
}
