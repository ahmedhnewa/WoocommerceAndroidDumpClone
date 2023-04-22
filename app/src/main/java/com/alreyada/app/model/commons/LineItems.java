package com.alreyada.app.model.commons;

import com.google.gson.annotations.SerializedName;

public class LineItems {
    @SerializedName("product_id")
    private Integer productId;
    @SerializedName("quantity")
    private Integer quantity;
    @SerializedName("variation_id")
    private Integer variationId;

    public LineItems(Integer productId, Integer quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public LineItems(Integer productId, Integer quantity, Integer variationId) {
        this.productId = productId;
        this.quantity = quantity;
        this.variationId = variationId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getVariationId() {
        return variationId;
    }

    public void setVariationId(Integer variationId) {
        this.variationId = variationId;
    }
}
