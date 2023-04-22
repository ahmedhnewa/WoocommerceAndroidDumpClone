package com.alreyada.app.model.authentication.simplejwt;

import com.google.gson.annotations.SerializedName;

public class UserValidateTokenCommon {

    @SerializedName("ID")
    private Integer ID;
    @SerializedName("user_login")
    private String userLogin;
    @SerializedName("user_nicename")
    private String userNiceName;
    @SerializedName("user_email")
    private String userEmail;
    @SerializedName("user_url")
    private String userUrl;
    @SerializedName("user_registered")
    private String userRegistered;
    @SerializedName("user_activation_key")
    private String userActivationKey;
    @SerializedName("user_status")
    private String userStatus;
    @SerializedName("display_name")
    private String displayName;


    public UserValidateTokenCommon(Integer ID, String userLogin, String userNiceName, String userEmail, String userUrl, String userRegistered, String userActivationKey, String userStatus, String displayName) {
        this.ID = ID;
        this.userLogin = userLogin;
        this.userNiceName = userNiceName;
        this.userEmail = userEmail;
        this.userUrl = userUrl;
        this.userRegistered = userRegistered;
        this.userActivationKey = userActivationKey;
        this.userStatus = userStatus;
        this.displayName = displayName;
    }

    public Integer getID() {
        return ID;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public String getUserNiceName() {
        return userNiceName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getUserUrl() {
        return userUrl;
    }

    public String getUserRegistered() {
        return userRegistered;
    }

    public String getUserActivationKey() {
        return userActivationKey;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public String getDisplayName() {
        return displayName;
    }


}
