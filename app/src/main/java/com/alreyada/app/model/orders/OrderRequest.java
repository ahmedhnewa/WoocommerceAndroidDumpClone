package com.alreyada.app.model.orders;

import com.alreyada.app.model.commons.BillingCommon;
import com.alreyada.app.model.commons.LineItems;
import com.alreyada.app.model.commons.ShippingCommon;
import com.alreyada.app.model.commons.ShippingLines;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderRequest {

    @SerializedName("payment_method")
    private String paymentMethod;

    @SerializedName("payment_method_title")
    private String paymentMethodTitle;

    @SerializedName("set_paid")
    private Boolean setPaid;

    @SerializedName("billing")
    private BillingCommon billing;

    @SerializedName("shipping")
    private ShippingCommon shipping;

    @SerializedName("line_items")
    private List<LineItems> lineItems;

    @SerializedName("shipping_lines")
    private List<ShippingLines> shippingLines;

    public OrderRequest() {
    }

    public OrderRequest(String paymentMethod, String paymentMethodTitle, Boolean setPaid, BillingCommon billing, ShippingCommon shipping, List<LineItems> lineItems, List<ShippingLines> shippingLines) {
        this.paymentMethod = paymentMethod;
        this.paymentMethodTitle = paymentMethodTitle;
        this.setPaid = setPaid;
        this.billing = billing;
        this.shipping = shipping;
        this.lineItems = lineItems;
        this.shippingLines = shippingLines;
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

    public Boolean getSetPaid() {
        return setPaid;
    }

    public void setSetPaid(Boolean setPaid) {
        this.setPaid = setPaid;
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

    public List<LineItems> getLineItems() {
        return lineItems;
    }

    public void setLineItems(List<LineItems> lineItems) {
        this.lineItems = lineItems;
    }

    public List<ShippingLines> getShippingLines() {
        return shippingLines;
    }

    public void setShippingLines(List<ShippingLines> shippingLines) {
        this.shippingLines = shippingLines;
    }
}
