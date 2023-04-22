package com.alreyada.app.model.commons;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class DownloadsCommon implements Parcelable {
    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("file")
    private String file;


    public DownloadsCommon(String id, String name, String file) {
        this.id = id;
        this.name = name;
        this.file = file;
    }

    protected DownloadsCommon(Parcel in) {
        id = in.readString();
        name = in.readString();
        file = in.readString();
    }

    public static final Creator<DownloadsCommon> CREATOR = new Creator<DownloadsCommon>() {
        @Override
        public DownloadsCommon createFromParcel(Parcel in) {
            return new DownloadsCommon(in);
        }

        @Override
        public DownloadsCommon[] newArray(int size) {
            return new DownloadsCommon[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(file);
    }
}
