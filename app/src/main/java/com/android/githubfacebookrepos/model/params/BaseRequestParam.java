package com.android.githubfacebookrepos.model.params;

/*
 * Created by Arafin Mahtab on 6/17/20.
 */

public class BaseRequestParam {

    private boolean shouldCacheResponse;
    private boolean isNetworkConnectionAvailable;

    public BaseRequestParam(boolean shouldCacheResponse, boolean isNetworkConnectionAvailable) {
        this.shouldCacheResponse = shouldCacheResponse;
        this.isNetworkConnectionAvailable = isNetworkConnectionAvailable;
    }

    public boolean isShouldCacheResponse() {
        return shouldCacheResponse;
    }

    public boolean isNetworkConnectionAvailable() {
        return isNetworkConnectionAvailable;
    }
}
