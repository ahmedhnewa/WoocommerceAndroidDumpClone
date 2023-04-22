package com.alreyada.app.model.authentication.jwt;

import com.google.gson.annotations.SerializedName;

public class JwtGetToken {

    /*
     * if response is successful
     */

    @SerializedName("success")
    private boolean success;

    @SerializedName("statusCode")
    private int statusCode;

    @SerializedName("code")
    private String code;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private JwtDataToken jwtDataToken;

    public boolean isSuccess() {
        return success;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public JwtDataToken getJwtDataToken() {
        return jwtDataToken;
    }
}
