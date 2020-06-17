package com.android.githubfacebookrepos.model.params;

/*
 * Created by Arafin Mahtab on 6/16/20.
 */

public class ParamFetchOrgRepo {

    private boolean cacheResponse = false;
    private String orgName;

    public ParamFetchOrgRepo(boolean cacheResponse, String orgName) {
        this.cacheResponse = cacheResponse;
        this.orgName = orgName;
    }

    public boolean isCacheResponse() {
        return cacheResponse;
    }

    public String getOrgName() {
        return orgName;
    }
}
