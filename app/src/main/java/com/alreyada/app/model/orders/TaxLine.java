
package com.alreyada.app.model.orders;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TaxLine implements Parcelable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("rate_code")
    @Expose
    private String rateCode;
    @SerializedName("rate_id")
    @Expose
    private Integer rateId;
    @SerializedName("label")
    @Expose
    private String label;
    @SerializedName("compound")
    @Expose
    private Boolean compound;
    @SerializedName("tax_total")
    @Expose
    private String taxTotal;
    @SerializedName("shipping_tax_total")
    @Expose
    private String shippingTaxTotal;
    @SerializedName("meta_data")
    @Expose
    private List<Object> metaData = null;
    public final static Parcelable.Creator<TaxLine> CREATOR = new Creator<TaxLine>() {


        @SuppressWarnings({
                "unchecked"
        })
        public TaxLine createFromParcel(Parcel in) {
            return new TaxLine(in);
        }

        public TaxLine[] newArray(int size) {
            return (new TaxLine[size]);
        }

    };

    protected TaxLine(Parcel in) {
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.rateCode = ((String) in.readValue((String.class.getClassLoader())));
        this.rateId = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.label = ((String) in.readValue((String.class.getClassLoader())));
        this.compound = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.taxTotal = ((String) in.readValue((String.class.getClassLoader())));
        this.shippingTaxTotal = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.metaData, (java.lang.Object.class.getClassLoader()));
    }

    /**
     * No args constructor for use in serialization
     */
    public TaxLine() {
    }

    /**
     * @param metaData
     * @param taxTotal
     * @param id
     * @param label
     * @param compound
     * @param rateCode
     * @param rateId
     * @param shippingTaxTotal
     */
    public TaxLine(Integer id, String rateCode, Integer rateId, String label, Boolean compound, String taxTotal, String shippingTaxTotal, List<Object> metaData) {
        super();
        this.id = id;
        this.rateCode = rateCode;
        this.rateId = rateId;
        this.label = label;
        this.compound = compound;
        this.taxTotal = taxTotal;
        this.shippingTaxTotal = shippingTaxTotal;
        this.metaData = metaData;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRateCode() {
        return rateCode;
    }

    public void setRateCode(String rateCode) {
        this.rateCode = rateCode;
    }

    public Integer getRateId() {
        return rateId;
    }

    public void setRateId(Integer rateId) {
        this.rateId = rateId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Boolean getCompound() {
        return compound;
    }

    public void setCompound(Boolean compound) {
        this.compound = compound;
    }

    public String getTaxTotal() {
        return taxTotal;
    }

    public void setTaxTotal(String taxTotal) {
        this.taxTotal = taxTotal;
    }

    public String getShippingTaxTotal() {
        return shippingTaxTotal;
    }

    public void setShippingTaxTotal(String shippingTaxTotal) {
        this.shippingTaxTotal = shippingTaxTotal;
    }

    public List<Object> getMetaData() {
        return metaData;
    }

    public void setMetaData(List<Object> metaData) {
        this.metaData = metaData;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(rateCode);
        dest.writeValue(rateId);
        dest.writeValue(label);
        dest.writeValue(compound);
        dest.writeValue(taxTotal);
        dest.writeValue(shippingTaxTotal);
        dest.writeList(metaData);
    }

    public int describeContents() {
        return 0;
    }

}
