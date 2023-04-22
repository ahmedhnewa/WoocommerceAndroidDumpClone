
package com.alreyada.app.model.orders;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MetaDatum_ implements Parcelable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("key")
    @Expose
    private String key;
    @SerializedName("value")
    @Expose
    private String value;
    public final static Parcelable.Creator<MetaDatum_> CREATOR = new Creator<MetaDatum_>() {


        @SuppressWarnings({
                "unchecked"
        })
        public MetaDatum_ createFromParcel(Parcel in) {
            return new MetaDatum_(in);
        }

        public MetaDatum_[] newArray(int size) {
            return (new MetaDatum_[size]);
        }

    };

    protected MetaDatum_(Parcel in) {
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.key = ((String) in.readValue((String.class.getClassLoader())));
        this.value = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     */
    public MetaDatum_() {
    }

    /**
     * @param id
     * @param value
     * @param key
     */
    public MetaDatum_(Integer id, String key, String value) {
        super();
        this.id = id;
        this.key = key;
        this.value = value;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(key);
        dest.writeValue(value);
    }

    public int describeContents() {
        return 0;
    }

}
