package com.android.githubfacebookrepos.views.main.adapter;

/*
 * Created by Arafin Mahtab on 6/17/20.
 */

import com.android.githubfacebookrepos.R;
import com.android.githubfacebookrepos.base.BaseViewHolder;
import com.android.githubfacebookrepos.data.AppConstant;
import com.android.githubfacebookrepos.databinding.ItemGitRepoBinding;
import com.android.githubfacebookrepos.model.mapped.GithubRepoMin;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

public class GitRepoViewHolder extends BaseViewHolder {

    private ItemGitRepoBinding binding;

    public GitRepoViewHolder(ItemGitRepoBinding binding, int viewType) {
        super(binding.getRoot(), viewType);
        this.binding = binding;
    }

    public void bind(GithubRepoMin githubRepoMin) {

        Glide.with(getContext())
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
