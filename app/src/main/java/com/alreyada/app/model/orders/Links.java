
package com.alreyada.app.model.orders;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Links implements Parcelable {

    @SerializedName("self")
    @Expose
    private List<Self> self = null;
    @SerializedName("collection")
    @Expose
    private List<Collection> collection = null;
    @SerializedName("customer")
    @Expose
    private List<Customer> customer = null;
    public final static Parcelable.Creator<Links> CREATOR = new Creator<Links>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Links createFromParcel(Parcel in) {
            return new Links(in);
        }

        public Links[] newArray(int size) {
            return (new Links[size]);
        }

    };

    protected Links(Parcel in) {
        in.readList(this.self, (com.alreyada.app.model.orders.Self.class.getClassLoader()));
        in.readList(this.collection, (com.alreyada.app.model.orders.Collection.class.getClassLoader()));
        in.readList(this.customer, (com.alreyada.app.model.orders.Customer.class.getClassLoader()));
    }

    /**
     * No args constructor for use in serialization
     */
    public Links() {
    }

    /**
     * @param self
     * @param collection
     * @param customer
     */
    public Links(List<Self> self, List<Collection> collection, List<Customer> customer) {
        super();
        this.self = self;
        this.collection = collection;
        this.customer = customer;
    }

    public List<Self> getSelf() {
        return self;
    }

    public void setSelf(List<Self> self) {
        this.self = self;
    }

    public List<Collection> getCollection() {
        return collection;
    }

    public void setCollection(List<Collection> collection) {
        this.collection = collection;
    }

    public List<Customer> getCustomer() {
        return customer;
    }

    public void setCustomer(List<Customer> customer) {
        this.customer = customer;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(self);
        dest.writeList(collection);
        dest.writeList(customer);
    }

    public int describeContents() {
        return 0;
    }

}
