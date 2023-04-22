package com.alreyada.app.model.commons;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class DimensionsCommon implements Parcelable {
    @SerializedName("length")
    private String length;
    @SerializedName("width")
    private String width;
    @SerializedName("height")
    private String height;


    public DimensionsCommon(String length, String width, String height) {
        this.length = length;
        this.width = width;
        this.height = height;
    }

    protected DimensionsCommon(Parcel in) {
        length = in.readString();
        width = in.readString();
        height = in.readString();
    }

    public static final Creator<DimensionsCommon> CREATOR = new Creator<DimensionsCommon>() {
        @Override
        public DimensionsCommon createFromParcel(Parcel in) {
            return new DimensionsCommon(in);
        }

        @Override
        public DimensionsCommon[] newArray(int size) {
            return new DimensionsCommon[size];
        }
    };

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(length);
        dest.writeString(width);
        dest.writeString(height);
    }
}
