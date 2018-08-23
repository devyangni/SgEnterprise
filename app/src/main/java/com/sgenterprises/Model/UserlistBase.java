package com.sgenterprises.Model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserlistBase {

@SerializedName("success")
@Expose
private String success;
@SerializedName("data")
@Expose
private List<UserList> data = null;

public String getSuccess() {
return success;
}
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @SerializedName("msg")
    @Expose
    private String msg;



public void setSuccess(String success) {
this.success = success;
}

public List<UserList> getData() {
return data;
}

public void setData(List<UserList> data) {
this.data = data;
}

}