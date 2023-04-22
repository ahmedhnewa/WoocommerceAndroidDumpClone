package com.alreyada.app.model.systemstatus;

import com.google.gson.annotations.SerializedName;

public class SecurityCommon {
    @SerializedName("secure_connection")
    private Boolean secureConnection;

    @SerializedName("hide_errors")
    private Boolean hideErrors;

    public Boolean isSecureConnection() {
        return secureConnection;
    }

    public Boolean isHideErrors() {
        return hideErrors;
    }
}
