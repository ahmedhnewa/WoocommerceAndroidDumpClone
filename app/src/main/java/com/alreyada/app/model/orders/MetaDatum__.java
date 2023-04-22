
package com.alreyada.app.model.orders;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MetaDatum__ implements Parcelable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("key")
    @Expose
    private String key;
    @SerializedName("value")
    @Expose
    private String value;
    public final static Parcelable.Creator<MetaDatum__> CREATOR = new Creator<MetaDatum__>() {


        @SuppressWarnings({
                "unchecked"
        })
        public MetaDatum__ createFromParcel(Parcel in) {
            return new MetaDatum__(in);
        }

        public MetaDatum__[] newArray(int size) {
            return (new MetaDatum__[size]);
        }

    };

    protected MetaDatum__(Parcel in) {
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.key = ((String) in.readValue((String.class.getClassLoader())));
        this.value = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     */
    public MetaDatum__() {
    }

    /**
     * @param id
     * @param value
     * @param key
     */
    public MetaDatum__(Integer id, String key, String value) {
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
