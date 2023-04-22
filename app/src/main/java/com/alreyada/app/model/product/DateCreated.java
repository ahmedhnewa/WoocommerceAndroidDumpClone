
package com.alreyada.app.model.product;

import com.google.gson.annotations.SerializedName;

public class DateCreated {

    @SerializedName("date")

    private String date;
    @SerializedName("timezone_type")

    private int timezoneType;
    @SerializedName("timezone")

    private String timezone;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getTimezoneType() {
        return timezoneType;
    }

    public void setTimezoneType(int timezoneType) {
        this.timezoneType = timezoneType;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

}
