package com.alreyada.app.model.commons;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class ImageCommon implements Parcelable {
    @SerializedName("id")
    private Integer id;
    @SerializedName("date_created")
    private String dateCreated;
    @SerializedName("date_created_gmt")
    private String dateCreatedGmt;
    @SerializedName("date_modified")
    private String dateModified;
    @SerializedName("date_modified_gmt")
    private String dateModifiedGmt;
    @SerializedName("src")
    private String src;
    @SerializedName("name")
    private String name;
    @SerializedName("alt")
    private String alt;



    public ImageCommon(Integer id, String dateCreated, String dateCreatedGmt, String dateModified, String dateModifiedGmt, String src, String name, String alt) {
        this.id = id;
        this.dateCreated = dateCreated;
        this.dateCreatedGmt = dateCreatedGmt;
        this.dateModified = dateModified;
        this.dateModifiedGmt = dateModifiedGmt;
        this.src = src;
        this.name = name;
        this.alt = alt;
    }

    protected ImageCommon(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        dateCreated = in.readString();
        dateCreatedGmt = in.readString();
        dateModified = in.readString();
        dateModifiedGmt = in.readString();
        src = in.readString();
        name = in.readString();
        alt = in.readString();
    }

    public static final Creator<ImageCommon> CREATOR = new Creator<ImageCommon>() {
        @Override
        public ImageCommon createFromParcel(Parcel in) {
            return new ImageCommon(in);
        }

        @Override
        public ImageCommon[] newArray(int size) {
            return new ImageCommon[size];
        }
    };

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getDateCreatedGmt() {
        return dateCreatedGmt;
    }

    public void setDateCreatedGmt(String dateCreatedGmt) {
        this.dateCreatedGmt = dateCreatedGmt;
    }

    public String getDateModified() {
        return dateModified;
    }

    public void setDateModified(String dateModified) {
        this.dateModified = dateModified;
    }

    public String getDateModifiedGmt() {
        return dateModifiedGmt;
    }

    public void setDateModifiedGmt(String dateModifiedGmt) {
        this.dateModifiedGmt = dateModifiedGmt;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(id);
        }
        dest.writeString(dateCreated);
        dest.writeString(dateCreatedGmt);
        dest.writeString(dateModified);
        dest.writeString(dateModifiedGmt);
        dest.writeString(src);
        dest.writeString(name);
        dest.writeString(alt);
    }
}
