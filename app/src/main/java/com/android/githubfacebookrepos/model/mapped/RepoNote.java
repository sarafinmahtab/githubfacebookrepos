package com.android.githubfacebookrepos.model.mapped;

/*
 * Created by Arafin Mahtab on 6/21/20.
 */

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Repo Note model keeps note corresponding to repoId
 * <p>
 * Annotation @Index will add a search index to the field.
 * A search index will make the Realm file larger and inserts slower but queries will be faster.
 */
public class RepoNote implements Parcelable {

    //    @PrimaryKey
    private String noteId;
    private String note;
    private long dateUpdated;
    //    @Index
    private int repoId;


    /**
     * Public constructor with no arguments
     */
    public RepoNote() {
    }

    public RepoNote(String noteId, String note, long dateUpdated, int repoId) {
        this.noteId = noteId;
        this.note = note;
        this.dateUpdated = dateUpdated;
        this.repoId = repoId;
    }

    protected RepoNote(Parcel in) {
        noteId = in.readString();
        note = in.readString();
        dateUpdated = in.readLong();
        repoId = in.readInt();
    }

    public static final Creator<RepoNote> CREATOR = new Creator<RepoNote>() {
        @Override
        public RepoNote createFromParcel(Parcel in) {
            return new RepoNote(in);
        }

        @Override
        public RepoNote[] newArray(int size) {
            return new RepoNote[size];
        }
    };

    public String getNoteId() {
        return noteId;
    }

    public void setNoteId(String noteId) {
        this.noteId = noteId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public long getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(long dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public int getRepoId() {
        return repoId;
    }

    public void setRepoId(int repoId) {
        this.repoId = repoId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(noteId);
        dest.writeString(note);
        dest.writeLong(dateUpdated);
        dest.writeInt(repoId);
    }
}
