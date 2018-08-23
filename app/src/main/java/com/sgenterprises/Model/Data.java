package com.sgenterprises.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

@SerializedName("email")
@Expose
private String email;
@SerializedName("user_id")
@Expose
private String userId;
@SerializedName("user_role")
@Expose
private String userRole;
@SerializedName("is_logged_in")
@Expose
private Boolean isLoggedIn;

public String getEmail() {
return email;
}

public void setEmail(String email) {
this.email = email;
}

public String getUserId() {
return userId;
}

public void setUserId(String userId) {
this.userId = userId;
}

public String getUserRole() {
return userRole;
}

public void setUserRole(String userRole) {
this.userRole = userRole;
}

public Boolean getIsLoggedIn() {
return isLoggedIn;
}

public void setIsLoggedIn(Boolean isLoggedIn) {
this.isLoggedIn = isLoggedIn;
}

}