package com.sgenterprises.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CalculateBase {

@SerializedName("success")
@Expose
private String success;
@SerializedName("msg")
@Expose
private Msg msg;

public String getSuccess() {
return success;
}

public void setSuccess(String success) {
this.success = success;
}

public Msg getMsg() {
return msg;
}

public void setMsg(Msg msg) {
this.msg = msg;
}

}
