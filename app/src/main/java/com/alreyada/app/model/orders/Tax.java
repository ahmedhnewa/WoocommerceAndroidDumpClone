
package com.alreyada.app.model.orders;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Tax implements Parcelable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("total")
    @Expose
    private String total;
    @SerializedName("subtotal")
    @Expose
    private String subtotal;
    public final static Parcelable.Creator<Tax> CREATOR = new Creator<Tax>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Tax createFromParcel(Parcel in) {
            return new Tax(in);
        }

        public Tax[] newArray(int size) {
            return (new Tax[size]);
        }

    };

    protected Tax(Parcel in) {
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.total = ((String) in.readValue((String.class.getClassLoader())));
        this.subtotal = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     */
    public Tax() {
    }

    /**
     * @param total
     * @param subtotal
     * @param id
     */
    public Tax(Integer id, String total, String subtotal) {
        super();
        this.id = id;
        this.total = total;
        this.subtotal = subtotal;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(String subtotal) {
        this.subtotal = subtotal;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(total);
        dest.writeValue(subtotal);
    }

    public int describeContents() {
        return 0;
    }

}
