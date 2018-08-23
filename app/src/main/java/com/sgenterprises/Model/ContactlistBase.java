package com.sgenterprises.Model;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ContactlistBase {

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

@SerializedName("data")
@Expose
private List<Contactlist> data = null;

public String getSuccess() {
return success;
}

public void setSuccess(String success) {
this.success = success;
}

public List<Contactlist> getData() {
return data;
}

public void setData(List<Contactlist> data) {
this.data = data;
}

}
