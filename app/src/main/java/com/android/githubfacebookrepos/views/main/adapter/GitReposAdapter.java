package com.android.githubfacebookrepos.views.main.adapter;

/*
 * Created by Arafin Mahtab on 6/17/20.
 */

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.RecyclerView;

import com.android.githubfacebookrepos.R;
import com.android.githubfacebookrepos.base.BaseViewHolder;
import com.android.githubfacebookrepos.model.GithubRepo;

public class GitReposAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private AsyncListDiffer<GithubRepo> mDiffer = new AsyncListDiffer<>(this, new GitRepoDiffUtil());

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_git_repo, parent, false);
        return new GitRepoViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mDiffer.getCurrentList().size();
    }
}
