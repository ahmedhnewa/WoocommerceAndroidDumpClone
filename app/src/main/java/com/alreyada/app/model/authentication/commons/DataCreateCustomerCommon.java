package com.alreyada.app.model.authentication.commons;

import com.google.gson.annotations.SerializedName;

public class DataCreateCustomerCommon {
    @SerializedName("status")
    private Integer status;

    public Integer getStatus() {
        return status;
    }
}
