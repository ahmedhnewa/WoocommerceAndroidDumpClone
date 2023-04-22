package com.alreyada.app.model.authentication.simplejwt;

import com.google.gson.annotations.SerializedName;

public class AuthenticationSimpleJwt {
    @SerializedName("success")
    private Boolean success;
    @SerializedName("data")
    private DataAuthCommon data;

    public AuthenticationSimpleJwt(Boolean success, DataAuthCommon data) {
        this.success = success;
        this.data = data;
    }

    public Boolean isSuccess() {
        return success;
    }


    public DataAuthCommon getData() {
        return data;
    }

    // no needed because i will use JWT Authentication for WP-API

}
