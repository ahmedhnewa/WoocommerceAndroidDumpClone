package com.alreyada.app.data.model;

public class Store {
    private String currency, currencySymbol, currencyPosition, thousandSeparator, decimalSeparator;
    private Boolean secureConnection, hideErrors;

    public Store(String currency, String currencySymbol, String currencyPosition, String thousandSeparator, String decimalSeparator, Boolean secureConnection, Boolean hideErrors) {
        this.currency = currency;
        this.currencySymbol = currencySymbol;
        this.currencyPosition = currencyPosition;
        this.thousandSeparator = thousandSeparator;
        this.decimalSeparator = decimalSeparator;
        this.secureConnection = secureConnection;
        this.hideErrors = hideErrors;
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

    public Boolean getSecureConnection() {
        return secureConnection;
    }

    public Boolean getHideErrors() {
        return hideErrors;
    }
}
