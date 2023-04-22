package com.alreyada.app.model.authentication.jwt;

import com.google.gson.annotations.SerializedName;

public class JwtDataToken {
    @SerializedName("token")
    private String token;

    @SerializedName("id")
    private int id;

    @SerializedName("email")
    private String email;

    @SerializedName("nicename")
    private String niceName;

    @SerializedName("firstName")
    private String firstName;

    @SerializedName("lastName")
    private String lastName;

    @SerializedName("displayName")
    private String displayName;

    public String getToken() {
        return token;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getNiceName() {
        return niceName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
