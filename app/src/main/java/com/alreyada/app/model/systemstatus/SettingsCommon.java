package com.alreyada.app.model.systemstatus;

import com.google.gson.annotations.SerializedName;

public class SettingsCommon {

    @SerializedName("api_enabled")
    private Boolean apiEnabled;

    @SerializedName("force_ssl")
    private Boolean forceSsl;

    @SerializedName("currency")
    private String currency;

    @SerializedName("currency_symbol")
    private String currencySymbol;

    @SerializedName("currency_position")
    private String currencyPosition;

    @SerializedName("thousand_separator")
    private String thousandSeparator;

    @SerializedName("decimal_separator")
    private String decimalSeparator;

    public Boolean isApiEnabled() {
        return apiEnabled;
    }

    public Boolean isForceSsl() {
        return forceSsl;
    }

    public String getCurrency() {
        return currency;
    }

    public String getCurrencySymbol() {
        return currencySymbol;
    }

    public String getCurrencyPosition() {
        return currencyPosition;
    }

    public String getThousandSeparator() {
        return thousandSeparator;
    }

    public String getDecimalSeparator() {
        return decimalSeparator;
    }
}
