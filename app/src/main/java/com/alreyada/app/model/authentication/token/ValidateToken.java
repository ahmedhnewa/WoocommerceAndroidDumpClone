package com.alreyada.app.model.authentication.token;

import com.alreyada.app.model.authentication.simplejwt.DataValidateTokenCommonOld;
import com.google.gson.annotations.SerializedName;

public class ValidateToken {
    @SerializedName("success")
    private Boolean success;

    @SerializedName("data")
    private DataValidateTokenCommonOld data;


    public Boolean isSuccess() {
        return success;
    }

    public DataValidateTokenCommonOld getData() {
        return data;
    }
}
