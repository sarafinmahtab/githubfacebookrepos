package com.android.githubfacebookrepos.model.mapped;

/*
 * Created by Arafin Mahtab on 6/17/20.
 */

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


public class GithubRepoMin extends RealmObject implements Parcelable {
    @PrimaryKey
    private int repoId;
    private String repoName;
    private boolean isPrivate;
    private int orgId;
    private String orgName;
    private String orgAvatarUrl;
    private String updatedDate;
    private String language;
    private String description;
    private boolean isForked;

    public GithubRepoMin() {

    }

    public GithubRepoMin(
            int repoId,
            String repoName,
            boolean isPrivate,
            int orgId,
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
        this.orgId = orgId;
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
        orgId = in.readInt();
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

    public void setRepoId(int repoId) {
        this.repoId = repoId;
    }

    public String getRepoName() {
        return repoName;
    }

    public void setRepoName(String repoName) {
        this.repoName = repoName;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public int getOrgId() {
        return orgId;
    }

    public void setOrgId(int orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgAvatarUrl() {
        return orgAvatarUrl;
    }

    public void setOrgAvatarUrl(String orgAvatarUrl) {
        this.orgAvatarUrl = orgAvatarUrl;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isForked() {
        return isForked;
    }

    public void setForked(boolean forked) {
        isForked = forked;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GithubRepoMin that = (GithubRepoMin) o;
        return repoId == that.repoId &&
                orgId == that.orgId &&
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
        return Objects.hash(repoId, repoName, isPrivate, orgId, orgName, orgAvatarUrl, updatedDate, language, description, isForked);
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
        dest.writeInt(orgId);
        dest.writeString(orgName);
        dest.writeString(orgAvatarUrl);
        dest.writeString(updatedDate);
        dest.writeString(language);
        dest.writeString(description);
        dest.writeByte((byte) (isForked ? 1 : 0));
    }
}
