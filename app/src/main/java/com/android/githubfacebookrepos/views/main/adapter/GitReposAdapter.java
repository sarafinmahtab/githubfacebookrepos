package com.android.githubfacebookrepos.views.main.adapter;

/*
 * Created by Arafin Mahtab on 6/17/20.
 */

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.RecyclerView;

import com.android.githubfacebookrepos.base.BaseViewHolder;
import com.android.githubfacebookrepos.base.ItemClickListener;
import com.android.githubfacebookrepos.databinding.ItemGitRepoBinding;
import com.android.githubfacebookrepos.model.mapped.GithubRepoMin;

import java.util.ArrayList;

public class GitReposAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private ItemClickListener itemClickListener;

    private AsyncListDiffer<GithubRepoMin> mDiffer = new AsyncListDiffer<>(this, new GitRepoDiffUtil());

    public GitReposAdapter(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemGitRepoBinding itemGitRepoBinding = ItemGitRepoBinding.inflate(layoutInflater, parent, false);

        GitRepoViewHolder gitRepoViewHolder = new GitRepoViewHolder(itemGitRepoBinding, viewType);
        gitRepoViewHolder.setItemClickListener(itemClickListener);
        return gitRepoViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        GithubRepoMin githubRepoMin = mDiffer.getCurrentList().get(position);
        ((GitRepoViewHolder) holder).bind(githubRepoMin);
    }

    @Override
    public int getItemCount() {
        return mDiffer.getCurrentList().size();
    }


    public void submitRepoList(ArrayList<GithubRepoMin> githubRepos) {
        mDiffer.submitList(githubRepos);
    }
}
