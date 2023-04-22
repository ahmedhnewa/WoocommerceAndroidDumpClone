package com.alreyada.app.model.commons;

import com.google.gson.annotations.SerializedName;

public class DataCommon {
    @SerializedName("status")
    private Integer status;

    public Integer getStatus() {
        return status;
    }
}
