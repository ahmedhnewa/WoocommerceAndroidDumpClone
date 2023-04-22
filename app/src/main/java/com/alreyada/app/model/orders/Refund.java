
package com.alreyada.app.model.orders;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Refund implements Parcelable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("refund")
    @Expose
    private String refund;
    @SerializedName("total")
    @Expose
    private String total;
    public final static Parcelable.Creator<Refund> CREATOR = new Creator<Refund>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Refund createFromParcel(Parcel in) {
            return new Refund(in);
        }

        public Refund[] newArray(int size) {
            return (new Refund[size]);
        }

    };

    protected Refund(Parcel in) {
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.refund = ((String) in.readValue((String.class.getClassLoader())));
        this.total = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     */
    public Refund() {
    }

    /**
     * @param total
     * @param id
     * @param refund
     */
    public Refund(Integer id, String refund, String total) {
        super();
        this.id = id;
        this.refund = refund;
        this.total = total;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRefund() {
        return refund;
    }

    public void setRefund(String refund) {
        this.refund = refund;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(refund);
        dest.writeValue(total);
    }

    public int describeContents() {
        return 0;
    }

}
