package com.android.githubfacebookrepos.views.main.adapter;

/*
 * Created by Arafin Mahtab on 6/17/20.
 */

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.RecyclerView;

import com.android.githubfacebookrepos.R;
import com.android.githubfacebookrepos.base.BaseViewHolder;
import com.android.githubfacebookrepos.base.ItemClickListener;
import com.android.githubfacebookrepos.data.AppConstant;
import com.android.githubfacebookrepos.databinding.ItemGitRepoBinding;
import com.android.githubfacebookrepos.model.mapped.GithubRepoMin;
import com.android.githubfacebookrepos.views.main.MainActivity;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class GitReposAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private RequestManager glideRequestManager;
    private ItemClickListener itemClickListener;

    private AsyncListDiffer<GithubRepoMin> mDiffer = new AsyncListDiffer<>(this, new GitRepoDiffUtil());

    public GitReposAdapter(RequestManager glideRequestManager, ItemClickListener itemClickListener) {
        this.glideRequestManager = glideRequestManager;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemGitRepoBinding itemGitRepoBinding = ItemGitRepoBinding.inflate(layoutInflater, parent, false);

        return new GitRepoViewHolder(itemGitRepoBinding, viewType);
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


    class GitRepoViewHolder extends BaseViewHolder {

        private ItemGitRepoBinding binding;

        public GitRepoViewHolder(ItemGitRepoBinding binding, int viewType) {
            super(binding.getRoot(), viewType);
            this.binding = binding;
        }

        public void bind(GithubRepoMin githubRepoMin) {

            getItemView().setOnClickListener(v ->
                    itemClickListener.onItemClick(
                            getAdapterPosition(),
                            getViewType(),
                            MainActivity.REPO_CLICKED,
                            githubRepoMin
                    ));

            glideRequestManager
                    .load(githubRepoMin.getOrgAvatarUrl())
                    .placeholder(R.drawable.ic_placeholder_repo)
                    .apply(new RequestOptions()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .format(DecodeFormat.PREFER_RGB_565)
                            .override(AppConstant.GLIDE_ITEM_IMAGE_SIZE, AppConstant.GLIDE_ITEM_IMAGE_SIZE)
                    )
                    .into(binding.avatarImageView);

            binding.setGitRepoMin(githubRepoMin);
            binding.executePendingBindings();
        }
    }
}
