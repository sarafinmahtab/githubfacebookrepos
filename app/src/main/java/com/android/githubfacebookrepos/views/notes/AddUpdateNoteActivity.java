package com.android.githubfacebookrepos.views.notes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.android.githubfacebookrepos.MainApplication;
import com.android.githubfacebookrepos.R;
import com.android.githubfacebookrepos.data.AppConstant;
import com.android.githubfacebookrepos.databinding.ActivityAddUpdateNoteBinding;
import com.android.githubfacebookrepos.di.ViewModelFactory;
import com.android.githubfacebookrepos.helpers.CommonUtil;
import com.android.githubfacebookrepos.helpers.ResponseHolder;
import com.android.githubfacebookrepos.model.mapped.GithubRepoMin;
import com.android.githubfacebookrepos.model.mapped.RepoNote;
import com.android.githubfacebookrepos.views.notes.dialog.AddNoteDialog;
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


    private RepoNote repoNote;
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


        viewModel.repoNoteLiveData.observe(this, repoNoteObserver);


        viewModel.fetchRepoNote(githubRepoMin.getRepoId());
    }

    private Observer<ResponseHolder<RepoNote>> repoNoteObserver = new Observer<ResponseHolder<RepoNote>>() {
        @Override
        public void onChanged(ResponseHolder<RepoNote> repoNoteResponseHolder) {
            switch (repoNoteResponseHolder.getStatus()) {

                case LOADING:

                    // Show a loader view if need

                    break;
                case SUCCESS:
                    binding.setRepoNote(repoNoteResponseHolder.getData());
                    repoNote = repoNoteResponseHolder.getData();

                    break;
                case ERROR:

                    String warningMessage = CommonUtil.prepareErrorMessage(repoNoteResponseHolder.getError());
                    Log.w(TAG, warningMessage);

                    break;
            }
        }
    };

    @Override
    public void onClick(View v) {
        if (v.getId() == binding.startAddNoteTextView.getId()) {
            openAddNoteDialog();
        }
    }

    private void openAddNoteDialog() {
        AddNoteDialog addNoteDialog = AddNoteDialog.getInstance(
                this,
                repoNote != null ? repoNote.getNoteId() : null,
                repoNote != null ? repoNote.getNote() : null
        );
        addNoteDialog.show(getSupportFragmentManager(), addNoteDialog.TAG);
    }

    @Override
    public void onAddNote(String noteId, String note) {
        viewModel.addUpdateNote(noteId, note, githubRepoMin.getRepoId());
    }

    private void setUpToolbar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(getString(R.string.title_notes));
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        super.onBackPressed();
        return true;
    }

    @Override
    protected void onDestroy() {

        viewModel.repoNoteLiveData.removeObserver(repoNoteObserver);

        super.onDestroy();
    }
}
