package com.alreyada.app.model.authentication.simplejwt;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DataValidateTokenCommonOld {

    @SerializedName("user")
    private UserValidateTokenCommon user;
    @SerializedName("jwt")
    private List<JWTCommon> jwt;

    public UserValidateTokenCommon getUser() {
        return user;
    }

    public List<JWTCommon> getJwt() {
        return jwt;
    }
}
