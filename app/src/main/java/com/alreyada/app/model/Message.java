package com.alreyada.app.model;

public class Message {

    private String senderUid;
    /*private String receiverUid;*/
    private String imageUrl;
    private String message;
    private String date;
    private String senderFirstName;
    private String senderLastName;
    /*private String receiverName;*/
    private String photoUrl;
    /*private String messageType;*/


    public Message() {
        // Required Empty For Firebase Ui
    }

    public Message(String senderUid, String imageUrl, String message, String date, String senderFirstName, String senderLastName, String photoUrl) {
        this.senderUid = senderUid;
        this.imageUrl = imageUrl;
        this.message = message;
        this.date = date;
        this.senderFirstName = senderFirstName;
        this.senderLastName = senderLastName;
        this.photoUrl = photoUrl;
    }

    public String getSenderUid() {
        return senderUid;
    }

    public void setSenderUid(String senderUid) {
        this.senderUid = senderUid;
    }


    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String data) {
        this.date = data;
    }

    public String getSenderName() {
        return senderFirstName;
    }

    public void setSenderName(String senderName) {
        this.senderFirstName = senderName;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getSenderFirstName() {
        return senderFirstName;
    }

    public void setSenderFirstName(String senderFirstName) {
        this.senderFirstName = senderFirstName;
    }

    public String getSenderLastName() {
        return senderLastName;
    }

    public void setSenderLastName(String senderLastName) {
        this.senderLastName = senderLastName;
    }
}
