
package com.android.githubfacebookrepos.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Permissions implements Parcelable {

    @SerializedName("admin")
    @Expose
    private boolean admin;
    @SerializedName("push")
    @Expose
    private boolean push;
    @SerializedName("pull")
    @Expose
    private boolean pull;

    protected Permissions(Parcel in) {
        this.admin = ((boolean) in.readValue((boolean.class.getClassLoader())));
        this.push = ((boolean) in.readValue((boolean.class.getClassLoader())));
        this.pull = ((boolean) in.readValue((boolean.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     */
    public Permissions() {
    }

    public Permissions(boolean admin, boolean push, boolean pull) {
        super();
        this.admin = admin;
        this.push = push;
        this.pull = pull;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public boolean isPush() {
        return push;
    }

    public void setPush(boolean push) {
        this.push = push;
    }

    public boolean isPull() {
        return pull;
    }

    public void setPull(boolean pull) {
        this.pull = pull;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(admin);
        dest.writeValue(push);
        dest.writeValue(pull);
    }

    public int describeContents() {
        return 0;
    }

    public final static Parcelable.Creator<Permissions> CREATOR = new Creator<Permissions>() {

        public Permissions createFromParcel(Parcel in) {
            return new Permissions(in);
        }

        public Permissions[] newArray(int size) {
            return (new Permissions[size]);
        }

    };
}
