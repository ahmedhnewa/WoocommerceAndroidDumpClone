package com.alreyada.app.model.checkouturl.common;

import com.google.gson.annotations.SerializedName;

public class CheckoutDataCommon {

    /**
     * if response is successful
     */

    @SerializedName("order_id")
    private Integer orderId;

    @SerializedName("order_status")
    private String orderStatus;

    @SerializedName("parent_id")
    private Integer parentId;

    @SerializedName("user_id")
    private Integer userId;

    @SerializedName("currency")
    private String currency;

    @SerializedName("currency_symbol")
    private String currencySymbol;

    @SerializedName("payment_method")
    private String paymentMethod;

    @SerializedName("payment_title")
    private String paymentTitle;

    @SerializedName("order_total")
    private String orderTotal;

    @SerializedName("order_discount_total")
    private String orderDiscountTotal;

    @SerializedName("order_shipping_total")
    private String orderShippingTotal;

    @SerializedName("date_created")
    private CheckoutDateCommon dateCreated;

    @SerializedName("date_modified")
    private CheckoutDateCommon dateModified;

    @SerializedName("billing_country")
    private String billingCountry;

    /**
     * if response is not successful
     */

    @SerializedName("status")
    private Integer status;

    // orderStatus is use also when response is not successful

    public Integer getOrderId() {
        return orderId;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public Integer getParentId() {
        return parentId;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getCurrency() {
        return currency;
    }

    public String getCurrencySymbol() {
        return currencySymbol;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public String getPaymentTitle() {
        return paymentTitle;
    }

    public String getOrderTotal() {
        return orderTotal;
    }

    public String getOrderDiscountTotal() {
        return orderDiscountTotal;
    }

    public String getOrderShippingTotal() {
        return orderShippingTotal;
    }

    public CheckoutDateCommon getDateCreated() {
        return dateCreated;
    }

    public CheckoutDateCommon getDateModified() {
        return dateModified;
    }

    public String getBillingCountry() {
        return billingCountry;
    }

    public Integer getStatus() {
        return status;
    }
}
