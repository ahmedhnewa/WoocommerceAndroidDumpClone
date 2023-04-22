package com.alreyada.app.model.commons;

import com.google.gson.annotations.SerializedName;

public class ShippingCommon {
    @SerializedName("first_name")
    private String firstName;
    @SerializedName("last_name")
    private String lastName;
    @SerializedName("company")
    private String company;
    @SerializedName("address_1")
    private String addressOne = "";
    @SerializedName("address_2")
    private String addressTow = "";
    @SerializedName("city")
    private String city;
    @SerializedName("postcode")
    private String postcode;
    @SerializedName("country")
    private String country;
    @SerializedName("state")
    private String state;

    public ShippingCommon() {
    }

    public ShippingCommon(String firstName, String lastName, String company, String addressOne, String addressTow, String city, String postcode, String country, String state) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.company = company;
        this.addressOne = addressOne;
        this.addressTow = addressTow;
        this.city = city;
        this.postcode = postcode;
        this.country = country;
        this.state = state;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getAddressOne() {
        return addressOne;
    }

    public void setAddressOne(String addressOne) {
        this.addressOne = addressOne;
    }

    public String getAddressTow() {
        return addressTow;
    }

    public void setAddressTow(String addressTow) {
        this.addressTow = addressTow;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

}
