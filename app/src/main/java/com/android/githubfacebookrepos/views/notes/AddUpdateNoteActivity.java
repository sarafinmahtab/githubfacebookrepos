package com.android.githubfacebookrepos.views.notes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.android.githubfacebookrepos.MainApplication;
import com.android.githubfacebookrepos.R;
import com.android.githubfacebookrepos.data.AppConstant;
import com.android.githubfacebookrepos.databinding.ActivityAddUpdateNoteBinding;
import com.android.githubfacebookrepos.di.ViewModelFactory;
import com.android.githubfacebookrepos.model.mapped.GithubRepoMin;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import javax.inject.Inject;

/**
 * Activity which will show add or update note of the repo
 */
public class AddUpdateNoteActivity extends AppCompatActivity implements View.OnClickListener, AddNoteDialog.AddNoteListener {

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


        // Update Views

        binding.setGitRepoMin(githubRepoMin);

        Glide.with(this)
                .load(githubRepoMin.getOrgAvatarUrl())
                .placeholder(R.drawable.ic_placeholder_repo)
                .apply(new RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .format(DecodeFormat.PREFER_RGB_565)
                        .override(AppConstant.GLIDE_ITEM_IMAGE_SIZE, AppConstant.GLIDE_ITEM_IMAGE_SIZE)
                )
                .into(binding.avatarImageView);

        binding.startAddNoteTextView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == binding.startAddNoteTextView.getId()) {
            openAddNoteDialog();
        }
    }

    private void openAddNoteDialog() {
        AddNoteDialog addNoteDialog = AddNoteDialog.getInstance(this, null);
        addNoteDialog.show(getSupportFragmentManager(), addNoteDialog.TAG);
    }

    @Override
    public void onAddNote(String note) {

        Toast.makeText(this, note, Toast.LENGTH_LONG).show();

        viewModel.addOrUpdateNote(note);
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
