package com.alreyada.app.model;

public class SliderItem {

    public String title;
    public String imageUrl;

    public SliderItem() {
    }

    public SliderItem(String imageUrl, String description) {
        this.imageUrl = imageUrl;
        this.title = description;
    }

    public String getDescription() {
        return title;
    }

    public void setDescription(String description) {
        this.title = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}