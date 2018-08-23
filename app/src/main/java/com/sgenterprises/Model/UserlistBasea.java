package com.sgenterprises.Model;

        import com.google.gson.annotations.Expose;
        import com.google.gson.annotations.SerializedName;


public class UserlistBasea {

    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("data")
    @Expose
    private Userlista data;


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @SerializedName("msg")
    @Expose
    private String msg;


    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public Userlista getData() {
        return data;
    }

    public void setData(Userlista data) {
        this.data = data;
    }

}