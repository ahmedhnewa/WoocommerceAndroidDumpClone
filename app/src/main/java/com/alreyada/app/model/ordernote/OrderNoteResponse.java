package com.alreyada.app.model.ordernote;

import com.google.gson.annotations.SerializedName;

public class OrderNoteResponse {
    @SerializedName("id")
    private Integer id;

    @SerializedName("author")
    private String author;

    @SerializedName("date_created")
    private String dateCreated;

    @SerializedName("date_created_gmt")
    private String dateCreatedGmt;

    @SerializedName("note")
    private String note;

    @SerializedName("customer_note")
    private Boolean customerNote;

    public OrderNoteResponse() {
    }

    public OrderNoteResponse(Integer id, String author, String dateCreated, String dateCreatedGmt, String note, Boolean customerNote) {
        this.id = id;
        this.author = author;
        this.dateCreated = dateCreated;
        this.dateCreatedGmt = dateCreatedGmt;
        this.note = note;
        this.customerNote = customerNote;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Boolean getCustomerNote() {
        return customerNote;
    }

    public void setCustomerNote(Boolean customerNote) {
        this.customerNote = customerNote;
    }
}
