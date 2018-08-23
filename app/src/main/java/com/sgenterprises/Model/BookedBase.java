package com.sgenterprises.Model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BookedBase {

@SerializedName("success")
@Expose
private String success;
@SerializedName("msg")
@Expose
private String msg;
@SerializedName("data")
@Expose
private List<bookedlistdata> data = null;

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

public List<bookedlistdata> getData() {
return data;
}

public void setData(List<bookedlistdata> data) {
this.data = data;
}

}
