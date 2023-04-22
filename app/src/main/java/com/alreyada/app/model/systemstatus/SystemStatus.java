package com.alreyada.app.model.systemstatus;

import com.google.gson.annotations.SerializedName;

public class SystemStatus {

    @SerializedName("settings")
    private SettingsCommon settings;

    @SerializedName("security")
    private SecurityCommon security;

    public SettingsCommon getSettings() {
        return settings;
    }

    public SecurityCommon getSecurity() {
        return security;
    }
}
