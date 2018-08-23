package com.sgenterprises.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Msg {

@SerializedName("total_amount")
@Expose
private String totalAmount;
@SerializedName("cgst")
@Expose
private String cgst;
@SerializedName("sgst")
@Expose
private String sgst;

public String getTotalAmount() {
return totalAmount;
}

public void setTotalAmount(String totalAmount) {
this.totalAmount = totalAmount;
}

public String getCgst() {
return cgst;
}

public void setCgst(String cgst) {
this.cgst = cgst;
}

public String getSgst() {
return sgst;
}

public void setSgst(String sgst) {
this.sgst = sgst;
}

}