package com.sgenterprises.Model;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HomeBase {

@SerializedName("success")
@Expose
private String success;
@SerializedName("content")
@Expose
private String content;

public String getSuccess() {
return success;
}

public void setSuccess(String success) {
this.success = success;
}

public String getContent() {
return content;
}

public void setContent(String content) {
this.content = content;
}

}