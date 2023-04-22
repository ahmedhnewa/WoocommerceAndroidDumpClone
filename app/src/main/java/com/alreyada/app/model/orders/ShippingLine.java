
package com.alreyada.app.model.orders;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ShippingLine implements Parcelable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("method_title")
    @Expose
    private String methodTitle;
    @SerializedName("method_id")
    @Expose
    private String methodId;
    @SerializedName("total")
    @Expose
    private String total;
    @SerializedName("total_tax")
    @Expose
    private String totalTax;
    @SerializedName("taxes")
    @Expose
    private List<Object> taxes = null;
    @SerializedName("meta_data")
    @Expose
    private List<MetaDatum__> metaData = null;
    public final static Parcelable.Creator<ShippingLine> CREATOR = new Creator<ShippingLine>() {


        @SuppressWarnings({
                "unchecked"
        })
        public ShippingLine createFromParcel(Parcel in) {
            return new ShippingLine(in);
        }

        public ShippingLine[] newArray(int size) {
            return (new ShippingLine[size]);
        }

    };

    protected ShippingLine(Parcel in) {
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.methodTitle = ((String) in.readValue((String.class.getClassLoader())));
        this.methodId = ((String) in.readValue((String.class.getClassLoader())));
        this.total = ((String) in.readValue((String.class.getClassLoader())));
        this.totalTax = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.taxes, (java.lang.Object.class.getClassLoader()));
        in.readList(this.metaData, (com.alreyada.app.model.orders.MetaDatum__.class.getClassLoader()));
    }

    /**
     * No args constructor for use in serialization
     */
    public ShippingLine() {
    }

    /**
     * @param totalTax
     * @param metaData
     * @param total
     * @param methodTitle
     * @param methodId
     * @param taxes
     * @param id
     */
    public ShippingLine(Integer id, String methodTitle, String methodId, String total, String totalTax, List<Object> taxes, List<MetaDatum__> metaData) {
        super();
        this.id = id;
        this.methodTitle = methodTitle;
        this.methodId = methodId;
        this.total = total;
        this.totalTax = totalTax;
        this.taxes = taxes;
        this.metaData = metaData;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMethodTitle() {
        return methodTitle;
    }

    public void setMethodTitle(String methodTitle) {
        this.methodTitle = methodTitle;
    }

    public String getMethodId() {
        return methodId;
    }

    public void setMethodId(String methodId) {
        this.methodId = methodId;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getTotalTax() {
        return totalTax;
    }

    public void setTotalTax(String totalTax) {
        this.totalTax = totalTax;
    }

    public List<Object> getTaxes() {
        return taxes;
    }

    public void setTaxes(List<Object> taxes) {
        this.taxes = taxes;
    }

    public List<MetaDatum__> getMetaData() {
        return metaData;
    }

    public void setMetaData(List<MetaDatum__> metaData) {
        this.metaData = metaData;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(methodTitle);
        dest.writeValue(methodId);
        dest.writeValue(total);
        dest.writeValue(totalTax);
        dest.writeList(taxes);
        dest.writeList(metaData);
    }

    public int describeContents() {
        return 0;
    }

}
