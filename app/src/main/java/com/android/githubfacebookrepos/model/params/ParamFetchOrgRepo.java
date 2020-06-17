package com.android.githubfacebookrepos.model.params;

/*
 * Created by Arafin Mahtab on 6/16/20.
 */

public class ParamFetchOrgRepo extends BaseRequestParam {

    private String orgName;

    public ParamFetchOrgRepo(boolean cacheResponse, boolean isNetworkAvailable, String orgName) {
        super(cacheResponse, isNetworkAvailable);

        this.orgName = orgName;
    }

    public String getOrgName() {
        return orgName;
    }
}
