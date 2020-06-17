package com.android.githubfacebookrepos.model.mapped;

/*
 * Created by Arafin Mahtab on 6/17/20.
 */

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

/**
 * Minimized mapped model of Github Repo
 */
public class GithubRepoMin implements Parcelable {

    private int repoId;
    private String repoName;
    private boolean isPrivate;
    private String orgName;
    private String orgAvatarUrl;
    private String updatedDate;
    private String language;
    private String description;
    private boolean isForked;

    public GithubRepoMin(
            int repoId,
            String repoName,
            boolean isPrivate,
            String orgName,
            String orgAvatarUrl,
            String updatedDate,
            String language,
            String description,
            boolean isForked
    ) {
        this.repoId = repoId;
        this.repoName = repoName;
        this.isPrivate = isPrivate;
        this.orgName = orgName;
        this.orgAvatarUrl = orgAvatarUrl;
        this.updatedDate = updatedDate;
        this.language = language;
        this.description = description;
        this.isForked = isForked;
    }

    protected GithubRepoMin(Parcel in) {
        repoId = in.readInt();
        repoName = in.readString();
        isPrivate = in.readByte() != 0;
        orgName = in.readString();
        orgAvatarUrl = in.readString();
        updatedDate = in.readString();
        language = in.readString();
        description = in.readString();
        isForked = in.readByte() != 0;
    }

    public static final Creator<GithubRepoMin> CREATOR = new Creator<GithubRepoMin>() {
        @Override
        public GithubRepoMin createFromParcel(Parcel in) {
            return new GithubRepoMin(in);
        }

        @Override
        public GithubRepoMin[] newArray(int size) {
            return new GithubRepoMin[size];
        }
    };

    public int getRepoId() {
        return repoId;
    }

    public String getRepoName() {
        return repoName;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public String getOrgName() {
        return orgName;
    }

    public String getOrgAvatarUrl() {
        return orgAvatarUrl;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public String getLanguage() {
        return language;
    }

    public String getDescription() {
        return description;
    }

    public boolean isForked() {
        return isForked;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GithubRepoMin that = (GithubRepoMin) o;
        return repoId == that.repoId &&
                isPrivate == that.isPrivate &&
                isForked == that.isForked &&
                repoName.equals(that.repoName) &&
                orgName.equals(that.orgName) &&
                Objects.equals(orgAvatarUrl, that.orgAvatarUrl) &&
                updatedDate.equals(that.updatedDate) &&
                language.equals(that.language) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(repoId, repoName, isPrivate, orgName, orgAvatarUrl, updatedDate, language, description, isForked);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(repoId);
        dest.writeString(repoName);
        dest.writeByte((byte) (isPrivate ? 1 : 0));
        dest.writeString(orgName);
        dest.writeString(orgAvatarUrl);
        dest.writeString(updatedDate);
        dest.writeString(language);
        dest.writeString(description);
        dest.writeByte((byte) (isForked ? 1 : 0));
    }
}
