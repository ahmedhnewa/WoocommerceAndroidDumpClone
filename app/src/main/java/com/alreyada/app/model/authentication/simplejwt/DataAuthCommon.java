package com.alreyada.app.model.authentication.simplejwt;

import com.google.gson.annotations.SerializedName;

public class DataAuthCommon {
    @SerializedName("jwt")
    private String token;
    @SerializedName("message")
    private String message;
    @SerializedName("errorCode")
    private Integer errorCode;

    public DataAuthCommon(String jwt, String message, Integer errorCode) {
        this.token = jwt;
        this.message = message;
        this.errorCode = errorCode;
    }

    public String getToken() {
        return token;
    }

    public String getMessage() {
        return message;
    }


    public Integer getErrorCode() {
        return errorCode;
    }

}
