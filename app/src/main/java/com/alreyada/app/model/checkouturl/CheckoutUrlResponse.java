package com.alreyada.app.model.checkouturl;

import com.alreyada.app.model.checkouturl.common.CheckoutDataCommon;
import com.google.gson.annotations.SerializedName;

public class CheckoutUrlResponse {

    /**
     * if response is successful
     */

    @SerializedName("checkout_url")
    private String checkoutUrl;


    @SerializedName("data")
    private CheckoutDataCommon data;

    /**
     * if response is not successful
     */

    @SerializedName("code")
    private String code;

    @SerializedName("message")
    private String message;

    // checkoutDataCommon is also when response is not successful


    public String getCheckoutUrl() {
        return checkoutUrl;
    }

    public CheckoutDataCommon getData() {
        return data;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
