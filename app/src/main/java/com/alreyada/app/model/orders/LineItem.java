
package com.alreyada.app.model.orders;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LineItem implements Parcelable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("product_id")
    @Expose
    private Integer productId;
    @SerializedName("variation_id")
    @Expose
    private Integer variationId;
    @SerializedName("quantity")
    @Expose
    private Integer quantity;
    @SerializedName("tax_class")
    @Expose
    private String taxClass;
    @SerializedName("subtotal")
    @Expose
    private String subtotal;
    @SerializedName("subtotal_tax")
    @Expose
    private String subtotalTax;
    @SerializedName("total")
    @Expose
    private String total;
    @SerializedName("total_tax")
    @Expose
    private String totalTax;
    @SerializedName("taxes")
    @Expose
    private List<Tax> taxes = null;
    @SerializedName("meta_data")
    @Expose
    private List<MetaDatum_> metaData = null;
    @SerializedName("sku")
    @Expose
    private String sku;
    @SerializedName("price")
    @Expose
    private Double price;
    public final static Parcelable.Creator<LineItem> CREATOR = new Creator<LineItem>() {


        @SuppressWarnings({
                "unchecked"
        })
        public LineItem createFromParcel(Parcel in) {
            return new LineItem(in);
        }

        public LineItem[] newArray(int size) {
            return (new LineItem[size]);
        }

    };

    protected LineItem(Parcel in) {
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.productId = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.variationId = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.quantity = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.taxClass = ((String) in.readValue((String.class.getClassLoader())));
        this.subtotal = ((String) in.readValue((String.class.getClassLoader())));
        this.subtotalTax = ((String) in.readValue((String.class.getClassLoader())));
        this.total = ((String) in.readValue((String.class.getClassLoader())));
        this.totalTax = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.taxes, (com.alreyada.app.model.orders.Tax.class.getClassLoader()));
        in.readList(this.metaData, (com.alreyada.app.model.orders.MetaDatum_.class.getClassLoader()));
        this.sku = ((String) in.readValue((String.class.getClassLoader())));
        this.price = ((Double) in.readValue((Integer.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     */
    public LineItem() {
    }

    /**
     * @param taxClass
     * @param quantity
     * @param productId
     * @param taxes
     * @param totalTax
     * @param subtotalTax
     * @param metaData
     * @param total
     * @param variationId
     * @param subtotal
     * @param price
     * @param name
     * @param id
     * @param sku
     */
    public LineItem(Integer id, String name, Integer productId, Integer variationId, Integer quantity, String taxClass, String subtotal, String subtotalTax, String total, String totalTax, List<Tax> taxes, List<MetaDatum_> metaData, String sku, Double price) {
        super();
        this.id = id;
        this.name = name;
        this.productId = productId;
        this.variationId = variationId;
        this.quantity = quantity;
        this.taxClass = taxClass;
        this.subtotal = subtotal;
        this.subtotalTax = subtotalTax;
        this.total = total;
        this.totalTax = totalTax;
        this.taxes = taxes;
        this.metaData = metaData;
        this.sku = sku;
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getVariationId() {
        return variationId;
    }

    public void setVariationId(Integer variationId) {
        this.variationId = variationId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getTaxClass() {
        return taxClass;
    }

    public void setTaxClass(String taxClass) {
        this.taxClass = taxClass;
    }

    public String getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(String subtotal) {
        this.subtotal = subtotal;
    }

    public String getSubtotalTax() {
        return subtotalTax;
    }

    public void setSubtotalTax(String subtotalTax) {
        this.subtotalTax = subtotalTax;
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

    public List<Tax> getTaxes() {
        return taxes;
    }

    public void setTaxes(List<Tax> taxes) {
        this.taxes = taxes;
    }

    public List<MetaDatum_> getMetaData() {
        return metaData;
    }

    public void setMetaData(List<MetaDatum_> metaData) {
        this.metaData = metaData;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(name);
        dest.writeValue(productId);
        dest.writeValue(variationId);
        dest.writeValue(quantity);
        dest.writeValue(taxClass);
        dest.writeValue(subtotal);
        dest.writeValue(subtotalTax);
        dest.writeValue(total);
        dest.writeValue(totalTax);
        dest.writeList(taxes);
        dest.writeList(metaData);
        dest.writeValue(sku);
        dest.writeValue(price);
    }

    public int describeContents() {
        return 0;
    }

}
