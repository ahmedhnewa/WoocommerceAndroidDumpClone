
package com.alreyada.app.model.orders;

import android.os.Parcel;
import android.os.Parcelable;

import com.alreyada.app.model.commons.BillingCommon;
import com.alreyada.app.model.commons.DataCommon;
import com.alreyada.app.model.commons.ShippingCommon;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Order implements Parcelable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("parent_id")
    @Expose
    private Integer parentId;
    @SerializedName("number")
    @Expose
    private String number;
    @SerializedName("order_key")
    @Expose
    private String orderKey;
    @SerializedName("created_via")
    @Expose
    private String createdVia;
    @SerializedName("version")
    @Expose
    private String version;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("currency")
    @Expose
    private String currency;
    @SerializedName("date_created")
    @Expose
    private String dateCreated;
    @SerializedName("date_created_gmt")
    @Expose
    private String dateCreatedGmt;
    @SerializedName("date_modified")
    @Expose
    private String dateModified;
    @SerializedName("date_modified_gmt")
    @Expose
    private String dateModifiedGmt;
    @SerializedName("discount_total")
    @Expose
    private String discountTotal;
    @SerializedName("discount_tax")
    @Expose
    private String discountTax;
    @SerializedName("shipping_total")
    @Expose
    private String shippingTotal;
    @SerializedName("shipping_tax")
    @Expose
    private String shippingTax;
    @SerializedName("cart_tax")
    @Expose
    private String cartTax;
    @SerializedName("total")
    @Expose
    private String total;
    @SerializedName("total_tax")
    @Expose
    private String totalTax;
    @SerializedName("prices_include_tax")
    @Expose
    private Boolean pricesIncludeTax;
    @SerializedName("customer_id")
    @Expose
    private Integer customerId;
    @SerializedName("customer_ip_address")
    @Expose
    private String customerIpAddress;
    @SerializedName("customer_user_agent")
    @Expose
    private String customerUserAgent;
    @SerializedName("customer_note")
    @Expose
    private String customerNote;
    @SerializedName("billing")
    @Expose
    private BillingCommon billing;
    @SerializedName("shipping")
    @Expose
    private ShippingCommon shipping;
    @SerializedName("payment_method")
    @Expose
    private String paymentMethod;
    @SerializedName("payment_method_title")
    @Expose
    private String paymentMethodTitle;
    @SerializedName("transaction_id")
    @Expose
    private String transactionId;
    @SerializedName("date_paid")
    @Expose
    private String datePaid;
    @SerializedName("date_paid_gmt")
    @Expose
    private String datePaidGmt;
    @SerializedName("date_completed")
    @Expose
    private String dateCompleted;
    @SerializedName("date_completed_gmt")
    @Expose
    private String dateCompletedGmt;
    @SerializedName("cart_hash")
    @Expose
    private String cartHash;
    @SerializedName("meta_data")
    @Expose
    private List<MetaDatum> metaData = null;
    @SerializedName("line_items")
    @Expose
    private List<LineItem> lineItems = null;
    @SerializedName("tax_lines")
    @Expose
    private List<TaxLine> taxLines = null;
    @SerializedName("shipping_lines")
    @Expose
    private List<ShippingLine> shippingLines = null;
    @SerializedName("fee_lines")
    @Expose
    private List<Object> feeLines = null;
    @SerializedName("coupon_lines")
    @Expose
    private List<Object> couponLines = null;
    @SerializedName("refunds")
    @Expose
    private List<Refund> refunds = null;
    @SerializedName("_links")
    @Expose
    private Links links;
    public final static Parcelable.Creator<Order> CREATOR = new Creator<Order>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Order createFromParcel(Parcel in) {
            return new Order(in);
        }

        public Order[] newArray(int size) {
            return (new Order[size]);
        }

    };

    /**
     * if response is not successful
     */

    @SerializedName("code")
    private String code;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private DataCommon data;

    protected Order(Parcel in) {
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.parentId = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.number = ((String) in.readValue((String.class.getClassLoader())));
        this.orderKey = ((String) in.readValue((String.class.getClassLoader())));
        this.createdVia = ((String) in.readValue((String.class.getClassLoader())));
        this.version = ((String) in.readValue((String.class.getClassLoader())));
        this.status = ((String) in.readValue((String.class.getClassLoader())));
        this.currency = ((String) in.readValue((String.class.getClassLoader())));
        this.dateCreated = ((String) in.readValue((String.class.getClassLoader())));
        this.dateCreatedGmt = ((String) in.readValue((String.class.getClassLoader())));
        this.dateModified = ((String) in.readValue((String.class.getClassLoader())));
        this.dateModifiedGmt = ((String) in.readValue((String.class.getClassLoader())));
        this.discountTotal = ((String) in.readValue((String.class.getClassLoader())));
        this.discountTax = ((String) in.readValue((String.class.getClassLoader())));
        this.shippingTotal = ((String) in.readValue((String.class.getClassLoader())));
        this.shippingTax = ((String) in.readValue((String.class.getClassLoader())));
        this.cartTax = ((String) in.readValue((String.class.getClassLoader())));
        this.total = ((String) in.readValue((String.class.getClassLoader())));
        this.totalTax = ((String) in.readValue((String.class.getClassLoader())));
        this.pricesIncludeTax = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.customerId = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.customerIpAddress = ((String) in.readValue((String.class.getClassLoader())));
        this.customerUserAgent = ((String) in.readValue((String.class.getClassLoader())));
        this.customerNote = ((String) in.readValue((String.class.getClassLoader())));
        this.billing = ((BillingCommon) in.readValue((BillingCommon.class.getClassLoader())));
        this.shipping = ((ShippingCommon) in.readValue((ShippingCommon.class.getClassLoader())));
        this.paymentMethod = ((String) in.readValue((String.class.getClassLoader())));
        this.paymentMethodTitle = ((String) in.readValue((String.class.getClassLoader())));
        this.transactionId = ((String) in.readValue((String.class.getClassLoader())));
        this.datePaid = ((String) in.readValue((Object.class.getClassLoader())));
        this.datePaidGmt = ((String) in.readValue((Object.class.getClassLoader())));
        this.dateCompleted = ((String) in.readValue((String.class.getClassLoader())));
        this.dateCompletedGmt = ((String) in.readValue((String.class.getClassLoader())));
        this.cartHash = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.metaData, (com.alreyada.app.model.orders.MetaDatum.class.getClassLoader()));
        in.readList(this.lineItems, (com.alreyada.app.model.orders.LineItem.class.getClassLoader()));
        in.readList(this.taxLines, (com.alreyada.app.model.orders.TaxLine.class.getClassLoader()));
        in.readList(this.shippingLines, (com.alreyada.app.model.orders.ShippingLine.class.getClassLoader()));
        in.readList(this.feeLines, (java.lang.Object.class.getClassLoader()));
        in.readList(this.couponLines, (java.lang.Object.class.getClassLoader()));
        in.readList(this.refunds, (com.alreyada.app.model.orders.Refund.class.getClassLoader()));
        this.links = ((Links) in.readValue((Links.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     */
    public Order() {
    }

    /**
     * @param datePaid
     * @param couponLines
     * @param billing
     * @param refunds
     * @param totalTax
     * @param cartHash
     * @param lineItems
     * @param number
     * @param shippingTax
     * @param metaData
     * @param total
     * @param dateCreated
     * @param shipping
     * @param dateCompleted
     * @param customerNote
     * @param customerId
     * @param currency
     * @param links
     * @param id
     * @param feeLines
     * @param orderKey
     * @param cartTax
     * @param dateCompletedGmt
     * @param dateCreatedGmt
     * @param shippingLines
     * @param createdVia
     * @param dateModifiedGmt
     * @param datePaidGmt
     * @param dateModified
     * @param customerIpAddress
     * @param discountTax
     * @param pricesIncludeTax
     * @param version
     * @param parentId
     * @param transactionId
     * @param shippingTotal
     * @param paymentMethodTitle
     * @param customerUserAgent
     * @param discountTotal
     * @param paymentMethod
     * @param taxLines
     * @param status
     */
    public Order(Integer id, Integer parentId, String number, String orderKey, String createdVia, String version, String status, String currency, String dateCreated, String dateCreatedGmt, String dateModified, String dateModifiedGmt, String discountTotal, String discountTax, String shippingTotal, String shippingTax, String cartTax, String total, String totalTax, Boolean pricesIncludeTax, Integer customerId, String customerIpAddress, String customerUserAgent, String customerNote, BillingCommon billing, ShippingCommon shipping, String paymentMethod, String paymentMethodTitle, String transactionId, String datePaid, String datePaidGmt, String dateCompleted, String dateCompletedGmt, String cartHash, List<MetaDatum> metaData, List<LineItem> lineItems, List<TaxLine> taxLines, List<ShippingLine> shippingLines, List<Object> feeLines, List<Object> couponLines, List<Refund> refunds, Links links) {
        super();
        this.id = id;
        this.parentId = parentId;
        this.number = number;
        this.orderKey = orderKey;
        this.createdVia = createdVia;
        this.version = version;
        this.status = status;
        this.currency = currency;
        this.dateCreated = dateCreated;
        this.dateCreatedGmt = dateCreatedGmt;
        this.dateModified = dateModified;
        this.dateModifiedGmt = dateModifiedGmt;
        this.discountTotal = discountTotal;
        this.discountTax = discountTax;
        this.shippingTotal = shippingTotal;
        this.shippingTax = shippingTax;
        this.cartTax = cartTax;
        this.total = total;
        this.totalTax = totalTax;
        this.pricesIncludeTax = pricesIncludeTax;
        this.customerId = customerId;
        this.customerIpAddress = customerIpAddress;
        this.customerUserAgent = customerUserAgent;
        this.customerNote = customerNote;
        this.billing = billing;
        this.shipping = shipping;
        this.paymentMethod = paymentMethod;
        this.paymentMethodTitle = paymentMethodTitle;
        this.transactionId = transactionId;
        this.datePaid = datePaid;
        this.datePaidGmt = datePaidGmt;
        this.dateCompleted = dateCompleted;
        this.dateCompletedGmt = dateCompletedGmt;
        this.cartHash = cartHash;
        this.metaData = metaData;
        this.lineItems = lineItems;
        this.taxLines = taxLines;
        this.shippingLines = shippingLines;
        this.feeLines = feeLines;
        this.couponLines = couponLines;
        this.refunds = refunds;
        this.links = links;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getOrderKey() {
        return orderKey;
    }

    public void setOrderKey(String orderKey) {
        this.orderKey = orderKey;
    }

    public String getCreatedVia() {
        return createdVia;
    }

    public void setCreatedVia(String createdVia) {
        this.createdVia = createdVia;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
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

    public String getDiscountTotal() {
        return discountTotal;
    }

    public void setDiscountTotal(String discountTotal) {
        this.discountTotal = discountTotal;
    }

    public String getDiscountTax() {
        return discountTax;
    }

    public void setDiscountTax(String discountTax) {
        this.discountTax = discountTax;
    }

    public String getShippingTotal() {
        return shippingTotal;
    }

    public void setShippingTotal(String shippingTotal) {
        this.shippingTotal = shippingTotal;
    }

    public String getShippingTax() {
        return shippingTax;
    }

    public void setShippingTax(String shippingTax) {
        this.shippingTax = shippingTax;
    }

    public String getCartTax() {
        return cartTax;
    }

    public void setCartTax(String cartTax) {
        this.cartTax = cartTax;
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

    public Boolean getPricesIncludeTax() {
        return pricesIncludeTax;
    }

    public void setPricesIncludeTax(Boolean pricesIncludeTax) {
        this.pricesIncludeTax = pricesIncludeTax;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getCustomerIpAddress() {
        return customerIpAddress;
    }

    public void setCustomerIpAddress(String customerIpAddress) {
        this.customerIpAddress = customerIpAddress;
    }

    public String getCustomerUserAgent() {
        return customerUserAgent;
    }

    public void setCustomerUserAgent(String customerUserAgent) {
        this.customerUserAgent = customerUserAgent;
    }

    public String getCustomerNote() {
        return customerNote;
    }

    public void setCustomerNote(String customerNote) {
        this.customerNote = customerNote;
    }

    public BillingCommon getBilling() {
        return billing;
    }

    public void setBilling(BillingCommon billing) {
        this.billing = billing;
    }

    public ShippingCommon getShipping() {
        return shipping;
    }

    public void setShipping(ShippingCommon shipping) {
        this.shipping = shipping;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentMethodTitle() {
        return paymentMethodTitle;
    }

    public void setPaymentMethodTitle(String paymentMethodTitle) {
        this.paymentMethodTitle = paymentMethodTitle;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getDatePaid() {
        return datePaid;
    }

    public void setDatePaid(String datePaid) {
        this.datePaid = datePaid;
    }

    public String getDatePaidGmt() {
        return datePaidGmt;
    }

    public void setDatePaidGmt(String datePaidGmt) {
        this.datePaidGmt = datePaidGmt;
    }

    public String getDateCompleted() {
        return dateCompleted;
    }

    public void setDateCompleted(String dateCompleted) {
        this.dateCompleted = dateCompleted;
    }

    public String getDateCompletedGmt() {
        return dateCompletedGmt;
    }

    public void setDateCompletedGmt(String dateCompletedGmt) {
        this.dateCompletedGmt = dateCompletedGmt;
    }

    public String getCartHash() {
        return cartHash;
    }

    public void setCartHash(String cartHash) {
        this.cartHash = cartHash;
    }

    public List<MetaDatum> getMetaData() {
        return metaData;
    }

    public void setMetaData(List<MetaDatum> metaData) {
        this.metaData = metaData;
    }

    public List<LineItem> getLineItems() {
        return lineItems;
    }

    public void setLineItems(List<LineItem> lineItems) {
        this.lineItems = lineItems;
    }

    public List<TaxLine> getTaxLines() {
        return taxLines;
    }

    public void setTaxLines(List<TaxLine> taxLines) {
        this.taxLines = taxLines;
    }

    public List<ShippingLine> getShippingLines() {
        return shippingLines;
    }

    public void setShippingLines(List<ShippingLine> shippingLines) {
        this.shippingLines = shippingLines;
    }

    public List<Object> getFeeLines() {
        return feeLines;
    }

    public void setFeeLines(List<Object> feeLines) {
        this.feeLines = feeLines;
    }

    public List<Object> getCouponLines() {
        return couponLines;
    }

    public void setCouponLines(List<Object> couponLines) {
        this.couponLines = couponLines;
    }

    public List<Refund> getRefunds() {
        return refunds;
    }

    public void setRefunds(List<Refund> refunds) {
        this.refunds = refunds;
    }

    public Links getLinks() {
        return links;
    }

    public void setLinks(Links links) {
        this.links = links;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public DataCommon getData() {
        return data;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(parentId);
        dest.writeValue(number);
        dest.writeValue(orderKey);
        dest.writeValue(createdVia);
        dest.writeValue(version);
        dest.writeValue(status);
        dest.writeValue(currency);
        dest.writeValue(dateCreated);
        dest.writeValue(dateCreatedGmt);
        dest.writeValue(dateModified);
        dest.writeValue(dateModifiedGmt);
        dest.writeValue(discountTotal);
        dest.writeValue(discountTax);
        dest.writeValue(shippingTotal);
        dest.writeValue(shippingTax);
        dest.writeValue(cartTax);
        dest.writeValue(total);
        dest.writeValue(totalTax);
        dest.writeValue(pricesIncludeTax);
        dest.writeValue(customerId);
        dest.writeValue(customerIpAddress);
        dest.writeValue(customerUserAgent);
        dest.writeValue(customerNote);
        dest.writeValue(billing);
        dest.writeValue(shipping);
        dest.writeValue(paymentMethod);
        dest.writeValue(paymentMethodTitle);
        dest.writeValue(transactionId);
        dest.writeValue(datePaid);
        dest.writeValue(datePaidGmt);
        dest.writeValue(dateCompleted);
        dest.writeValue(dateCompletedGmt);
        dest.writeValue(cartHash);
        dest.writeList(metaData);
        dest.writeList(lineItems);
        dest.writeList(taxLines);
        dest.writeList(shippingLines);
        dest.writeList(feeLines);
        dest.writeList(couponLines);
        dest.writeList(refunds);
        dest.writeValue(links);
    }

    public int describeContents() {
        return 0;
    }

}
