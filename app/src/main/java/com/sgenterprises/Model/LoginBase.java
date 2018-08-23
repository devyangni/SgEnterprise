package com.sgenterprises.Model;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginBase {

@SerializedName("success")
@Expose
private String success;
@SerializedName("msg")
@Expose
private String msg;
@SerializedName("data")
@Expose
private Data data;

public String getSuccess() {
return success;
}

public void setSuccess(String success) {
this.success = success;
}

public String getMsg() {
return msg;
}

public void setMsg(String msg) {
this.msg = msg;
}

public Data getData() {
return data;
}

public void setData(Data data) {
this.data = data;
}

}