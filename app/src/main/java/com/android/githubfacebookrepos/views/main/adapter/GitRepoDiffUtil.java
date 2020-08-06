package com.android.githubfacebookrepos.views.main.adapter;

/*
 * Created by Arafin Mahtab on 6/17/20.
 */

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.android.githubfacebookrepos.model.mapped.GithubRepoMin;


public class GitRepoDiffUtil extends DiffUtil.ItemCallback<GithubRepoMin> {

    @Override
    public boolean areItemsTheSame(@NonNull GithubRepoMin oldItem, @NonNull GithubRepoMin newItem) {
        return oldItem.getRepoId() == newItem.getRepoId();
    }

    @Override
    public boolean areContentsTheSame(@NonNull GithubRepoMin oldItem, @NonNull GithubRepoMin newItem) {
        return oldItem.equals(newItem);
    }
}
