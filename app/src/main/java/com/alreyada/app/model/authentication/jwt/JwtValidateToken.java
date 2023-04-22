package com.alreyada.app.model.authentication.jwt;

import com.google.gson.annotations.SerializedName;

public class JwtValidateToken {
    @SerializedName("code")
    private String code;

    @SerializedName("data")
    private JwtDataToken data;

    public String getCode() {
        return code;
    }

    public JwtDataToken getData() {
        return data;
    }
}
