package com.sgenterprises.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Error {

    @SerializedName("first_name")
    @Expose
    private String firstName ="";
    @SerializedName("last_name")
    @Expose
    private String lastName  ="";
    @SerializedName("company_name")
    @Expose
    private String companyName  ="";
    @SerializedName("email")
    @Expose
    private String email  ="";
    @SerializedName("password")
    @Expose
    private String password ="";
    @SerializedName("phone_no")
    @Expose
    private String phoneNo ="";
    @SerializedName("address")
    @Expose
    private String address ="";

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

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}