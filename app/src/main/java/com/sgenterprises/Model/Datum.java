package com.sgenterprises.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

@SerializedName("user_id")
@Expose
private String userId;
@SerializedName("username")
@Expose
private String username;

public String getUserId() {
return userId;
}

public void setUserId(String userId) {
this.userId = userId;
}

public String getUsername() {
return username;
}

public void setUsername(String username) {
this.username = username;
}

}