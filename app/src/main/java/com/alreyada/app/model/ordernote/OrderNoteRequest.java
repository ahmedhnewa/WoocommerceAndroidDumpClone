package com.alreyada.app.model.ordernote;

import com.google.gson.annotations.SerializedName;

public class OrderNoteRequest {
    @SerializedName("note")
    private String note;

    public OrderNoteRequest() {
    }

    public OrderNoteRequest(String note) {
        this.note = note;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
