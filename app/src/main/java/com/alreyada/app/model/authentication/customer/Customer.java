package com.alreyada.app.model.authentication.customer;

import com.alreyada.app.model.commons.BillingCommon;
import com.alreyada.app.model.authentication.commons.DataCreateCustomerCommon;
import com.alreyada.app.model.commons.ShippingCommon;
import com.google.gson.annotations.SerializedName;

public class Customer {

    /**
     * if response is not successful
     */

    @SerializedName("code")
    private String code;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private DataCreateCustomerCommon data;

    /**
     * if response is successful
     */

    @SerializedName("id")
    private Integer id;
    @SerializedName("date_created")
    private String dateCreated;
    @SerializedName("date_created_gmt")
    private String dateCreatedGmt;
    @SerializedName("date_modified")
    private String dateModified;
    @SerializedName("date_modified_gmt")
    private String dateModifiedGmt;
    @SerializedName("email")
    private String email;
    @SerializedName("first_name")
    private String firstName;
    @SerializedName("last_name")
    private String lastName;
    @SerializedName("role")
    private String role;
    @SerializedName("username")
    private String username;
    @SerializedName("password")
    private String password;

    @SerializedName("billing")
    private BillingCommon billing;

    @SerializedName("shipping")
    private ShippingCommon shipping;

    @SerializedName("is_paying_customer")
    private Boolean isPayingCustomer;

    @SerializedName("avatar_url")
    private String avatarUrl;

    public Customer() {
    }

    public Customer(String code, String message, DataCreateCustomerCommon data, Integer id, String dateCreated, String dateCreatedGmt, String dateModified, String dateModifiedGmt, String email, String firstName, String lastName, String role, String username, BillingCommon billing, ShippingCommon shipping, Boolean isPayingCustomer, String avatarUrl) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.id = id;
        this.dateCreated = dateCreated;
        this.dateCreatedGmt = dateCreatedGmt;
        this.dateModified = dateModified;
        this.dateModifiedGmt = dateModifiedGmt;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.username = username;
        this.billing = billing;
        this.shipping = shipping;
        this.isPayingCustomer = isPayingCustomer;
        this.avatarUrl = avatarUrl;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataCreateCustomerCommon getData() {
        return data;
    }

    public void setData(DataCreateCustomerCommon data) {
        this.data = data;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getDateCreatedGmt() {
        return dateCreatedGmt;
    }

    public void setDateCreatedGmt(String dateCreatedGmt) {
        this.dateCreatedGmt = dateCreatedGmt;
    }

    public String getDateModified() {
        return dateModified;
    }

    public void setDateModified(String dateModified) {
        this.dateModified = dateModified;
    }

    public String getDateModifiedGmt() {
        return dateModifiedGmt;
    }

    public void setDateModifiedGmt(String dateModifiedGmt) {
        this.dateModifiedGmt = dateModifiedGmt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public BillingCommon getBilling() {
        return billing;
    }

    public void setBilling(BillingCommon billing) {
        this.billing = billing;
    }

    public ShippingCommon getShipping() {
        return shipping;
    }

    public void setShipping(ShippingCommon shipping) {
        this.shipping = shipping;
    }

    public Boolean getPayingCustomer() {
        return isPayingCustomer;
    }

    public void setPayingCustomer(Boolean payingCustomer) {
        isPayingCustomer = payingCustomer;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
