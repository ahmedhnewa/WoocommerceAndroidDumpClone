package com.alreyada.app.model.authentication.simplejwt;

import com.google.gson.annotations.SerializedName;

public class JWTCommon {
    @SerializedName("token")
    private String token;

    public JWTCommon(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

}
