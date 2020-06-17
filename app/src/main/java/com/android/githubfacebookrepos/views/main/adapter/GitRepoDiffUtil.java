package com.android.githubfacebookrepos.views.main.adapter;

/*
 * Created by Arafin Mahtab on 6/17/20.
 */

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.android.githubfacebookrepos.model.GithubRepo;

public class GitRepoDiffUtil extends DiffUtil.ItemCallback<GithubRepo> {

    @Override
    public boolean areItemsTheSame(@NonNull GithubRepo oldItem, @NonNull GithubRepo newItem) {
        return oldItem.getId() == newItem.getId();
    }

    @Override
    public boolean areContentsTheSame(@NonNull GithubRepo oldItem, @NonNull GithubRepo newItem) {
        return oldItem.equals(newItem);
    }
}
