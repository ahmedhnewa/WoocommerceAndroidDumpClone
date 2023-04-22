package com.alreyada.app.model;

import com.alreyada.app.model.commons.DataCommon;
import com.google.gson.annotations.SerializedName;

public class WpError {
    @SerializedName("code")
    private String code = "";
    @SerializedName("message")
    private String message = "";
    @SerializedName("data")
    private DataCommon data;

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public DataCommon getData() {
        return data;
    }
}
