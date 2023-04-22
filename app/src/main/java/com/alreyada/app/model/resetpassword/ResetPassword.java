package com.alreyada.app.model.resetpassword;

import com.alreyada.app.model.resetpassword.commons.Data;
import com.google.gson.annotations.SerializedName;

public class ResetPassword {
    @SerializedName("code")
    private String code;

    @SerializedName("data")
    private Data data;

    @SerializedName("message")
    private String message;

    public Data getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public String getCode() {
        return code;
    }
}
