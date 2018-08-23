package com.sgenterprises.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Registerbase {

@SerializedName("success")
@Expose
private String success;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @SerializedName("msg")
    @Expose
    private String msg;
@SerializedName("error")
@Expose
private Error error;

public String getSuccess() {
return success;
}

public void setSuccess(String success) {
this.success = success;
}

public Error getError() {
return error;
}

public void setError(Error error) {
this.error = error;
}

}


