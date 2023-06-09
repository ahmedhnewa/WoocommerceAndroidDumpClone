package com.alreyada.app.model.categories;

import android.os.Parcel;
import android.os.Parcelable;

import com.alreyada.app.model.commons.ImageCommon;
import com.google.gson.annotations.SerializedName;

public class Category implements Parcelable {

    @SerializedName("id")
    private Integer id;
    @SerializedName("name")
    private String name;
    @SerializedName("slug")
    private String slug;
    @SerializedName("parent")
    private Integer parent;
    @SerializedName("description")
    private String description;
    @SerializedName("display")
    private String display;
    @SerializedName("image")
    private ImageCommon image;
    @SerializedName("menu_order")
    private Integer menuOrder;
    @SerializedName("count")
    private Integer count;

    public Category() {
    }

    public Category(Integer id, String name, String slug, Integer parent, String description, String display, ImageCommon image, Integer menuOrder, Integer count) {
        this.id = id;
        this.name = name;
        this.slug = slug;
        this.parent = parent;
        this.description = description;
        this.display = display;
        this.image = image;
        this.menuOrder = menuOrder;
        this.count = count;
    }

    protected Category(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        name = in.readString();
        slug = in.readString();
        if (in.readByte() == 0) {
            parent = null;
        } else {
            parent = in.readInt();
        }
        description = in.readString();
        display = in.readString();
        if (in.readByte() == 0) {
            menuOrder = null;
        } else {
            menuOrder = in.readInt();
        }
        if (in.readByte() == 0) {
            count = null;
        } else {
            count = in.readInt();
        }
    }

    public static final Creator<Category> CREATOR = new Creator<Category>() {
        @Override
        public Category createFromParcel(Parcel in) {
            return new Category(in);
        }

        @Override
        public Category[] newArray(int size) {
            return new Category[size];
        }
    };

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public Integer getParent() {
        return parent;
    }

    public void setParent(Integer parent) {
        this.parent = parent;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public ImageCommon getImage() {
        return image;
    }

    public void setImage(ImageCommon image) {
        this.image = image;
    }

    public Integer getMenuOrder() {
        return menuOrder;
    }

    public void setMenuOrder(Integer menuOrder) {
        this.menuOrder = menuOrder;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(id);
        }
        dest.writeString(name);
        dest.writeString(slug);
        if (parent == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(parent);
        }
        dest.writeString(description);
        dest.writeString(display);
        if (menuOrder == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(menuOrder);
        }
        if (count == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(count);
        }
    }
}
