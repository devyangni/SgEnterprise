package com.sgenterprises.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class bookedlistdata {

@SerializedName("booking_id")
@Expose
private String bookingId;
@SerializedName("phone_no")
@Expose
private String phoneNo;
@SerializedName("booking_date")
@Expose
private String bookingDate;
@SerializedName("quantity")
@Expose
private String quantity;
@SerializedName("created_at")
@Expose
private String createdAt;
@SerializedName("status")
@Expose
private String status;
@SerializedName("material_name")
@Expose
private String materialName;

public String getBookingId() {
return bookingId;
}

public void setBookingId(String bookingId) {
this.bookingId = bookingId;
}

public String getPhoneNo() {
return phoneNo;
}

public void setPhoneNo(String phoneNo) {
this.phoneNo = phoneNo;
}

public String getBookingDate() {
return bookingDate;
}

public void setBookingDate(String bookingDate) {
this.bookingDate = bookingDate;
}

public String getQuantity() {
return quantity;
}

public void setQuantity(String quantity) {
this.quantity = quantity;
}

public String getCreatedAt() {
return createdAt;
}

public void setCreatedAt(String createdAt) {
this.createdAt = createdAt;
}

public String getStatus() {
return status;
}

public void setStatus(String status) {
this.status = status;
}

public String getMaterialName() {
return materialName;
}

public void setMaterialName(String materialName) {
this.materialName = materialName;
}

}