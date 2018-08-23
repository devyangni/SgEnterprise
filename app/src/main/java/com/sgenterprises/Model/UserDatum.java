package com.sgenterprises.Model;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserDatum implements Parcelable {

    @SerializedName("order_id")
    @Expose
    private String orderId ="";
    @SerializedName("location")
    @Expose
    private String location ="";
    @SerializedName("length")
    @Expose
    private String length ="";
    @SerializedName("breadth")
    @Expose
    private String breadth ="";
    @SerializedName("height")
    @Expose
    private String height ="";
    @SerializedName("material_size")
    @Expose
    private String materialSize ="";
    @SerializedName("price")
    @Expose
    private String price ="";
    @SerializedName("payment_given")
    @Expose
    private String paymentGiven ="";
    @SerializedName("payment_due")
    @Expose
    private String paymentDue ="";
    @SerializedName("created_at")
    @Expose
    private String createdAt ="";
    @SerializedName("material_name")
    @Expose
    private String materialName ="";
    @SerializedName("first_name")
    @Expose
    private String firstName =null;
    @SerializedName("last_name")
    @Expose
    private String lastName =null;

    @SerializedName("customer_id")
    @Expose
    private String customer_id ="";

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getMaterial_id() {
        return material_id;
    }

    public void setMaterial_id(String material_id) {
        this.material_id = material_id;
    }

    @SerializedName("material_id")
    @Expose
    private String material_id ="";

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getBreadth() {
        return breadth;
    }

    public void setBreadth(String breadth) {
        this.breadth = breadth;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getMaterialSize() {
        return materialSize;
    }

    public void setMaterialSize(String materialSize) {
        this.materialSize = materialSize;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPaymentGiven() {
        return paymentGiven;
    }

    public void setPaymentGiven(String paymentGiven) {
        this.paymentGiven = paymentGiven;
    }

    public String getPaymentDue() {
        return paymentDue;
    }

    public void setPaymentDue(String paymentDue) {
        this.paymentDue = paymentDue;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
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


    protected UserDatum(Parcel in) {
        orderId = in.readString();
        location = in.readString();
        length = in.readString();
        breadth = in.readString();
        height = in.readString();
        materialSize = in.readString();
        price = in.readString();
        paymentGiven = in.readString();
        paymentDue = in.readString();
        createdAt = in.readString();
        materialName = in.readString();
        firstName = in.readString();
        lastName = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(orderId);
        dest.writeString(location);
        dest.writeString(length);
        dest.writeString(breadth);
        dest.writeString(height);
        dest.writeString(materialSize);
        dest.writeString(price);
        dest.writeString(paymentGiven);
        dest.writeString(paymentDue);
        dest.writeString(createdAt);
        dest.writeString(materialName);
        dest.writeString(firstName);
        dest.writeString(lastName);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<UserDatum> CREATOR = new Parcelable.Creator<UserDatum>() {
        @Override
        public UserDatum createFromParcel(Parcel in) {
            return new UserDatum(in);
        }

        @Override
        public UserDatum[] newArray(int size) {
            return new UserDatum[size];
        }
    };
}