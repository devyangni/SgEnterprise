package com.sgenterprises.Home;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.sgenterprises.Common.BaseClass;
import com.sgenterprises.Fragment.AdminmenuActivity;
import com.sgenterprises.Fragment.MenuActivity;
import com.sgenterprises.Home.User.UserNavigationDrawer;
import com.sgenterprises.Model.Data;
import com.sgenterprises.Model.LoginBase;
import com.sgenterprises.R;
import com.sgenterprises.SplashActivity;
import com.sgenterprises.httpmanager.ApiHandler;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.com.commonlibrary.utils.CommonUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends BaseClass implements View.OnClickListener {
    public static LoginActivity activity;
    String TAG = LoginActivity.class.getName();
    private EditText email;
    private EditText password;
    private Button btnLogin;
    private Button btnSignup;

    String emailid = null;
    String passwordd = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        activity =this;
        init(LoginActivity.this);
        findViews();
    }

    private void findViews() {
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        btnLogin = (Button) findViewById(R.id.btn_login);
       // btnResetPassword = (Button) findViewById(R.id.btn_reset_password);
        btnSignup = (Button) findViewById(R.id.btn_signup);
        btnLogin.setOnClickListener(this);
        btnSignup.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == btnLogin) {
            emailid = email.getText().toString();
            passwordd = password.getText().toString();
            if (TextUtils.isEmpty(emailid)) {
                CommonUtils.commonToast(LoginActivity.this, getResources().getString(R.string.enteremailid));

            } else if (!CommonUtils.isEmailValid(emailid)) {
                CommonUtils.commonToast(LoginActivity.this, getResources().getString(R.string.validemail));

            }  else if (TextUtils.isEmpty(passwordd)) {

                CommonUtils.commonToast(LoginActivity.this, getResources().getString(R.string.minpassword));
            }
            else

            {


                getLoginApi();
            }
        }  else if (v == btnSignup) {
            startActivity(new Intent(this,RegistrationActivity.class));
        }
    }



    protected Map<String, String> getParams() {
        Map<String, String> params = new HashMap<String, String>();

        params.put("email", emailid);
        params.put("password", passwordd);
        SharedPreferences preferences=getSharedPreferences("FCM",MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        String value = preferences.getString("token", "");
        if (CommonUtils.isTextAvailable(value)) {
            params.put("device_token",value );
        }
        else
        {
            params.put("device_token","0" );
        }

        Log.e("PArams", params.toString());

        return params;

    }

    public void getLoginApi() {

        if (CommonUtils.isConnectingToInternet(LoginActivity.this)) {
            final Dialog dialog = new android.app.Dialog(activity);
            dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            dialog.setContentView(R.layout.dialog_wait);
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();


            Call<LoginBase> call = ApiHandler.getApiService().login(getParams());
            call.enqueue(new Callback<LoginBase>() {
                @Override
                public void onResponse(Call<LoginBase> call, Response<LoginBase> response) {
                    try {
                        Log.e(TAG, " Full json gson => " + new Gson().toJson(response));
                        JSONObject jsonObj = new JSONObject(new Gson().toJson(response).toString());
                        Log.e(TAG, " responce => " + jsonObj.getJSONObject("body").toString());
                        dialog.dismiss();
                        if (response.isSuccessful()) {

                            String success = response.body().getSuccess();
                            String message = response.body().getMsg();
                            if (success.equalsIgnoreCase("true")) {
                                CommonUtils.commonToast(LoginActivity.this, message);

                                Data data = response.body().getData();

                                commonSession.storeLoggedUserID(data.getUserId());
                                commonSession.setActivityType(data.getUserRole());
                                commonSession.storeLoggedEmail(data.getEmail());

                                String userrole = commonSession.getActivityType();
                                if (userrole.equalsIgnoreCase("1"))
                                {
                                    Intent a = new Intent(LoginActivity.this,AdminmenuActivity.class);
                                    a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(a);
                                    finish();
                                }
                                else
                                {
                                    Intent a = new Intent(LoginActivity.this,MenuActivity.class);
                                    a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(a);
                                    finish();
                                }



                            } else if (success.equalsIgnoreCase("false")) {

                                CommonUtils.commonToast(LoginActivity.this, message);
                            } else if (success.equalsIgnoreCase("0")) {

                                CommonUtils.commonToast(LoginActivity.this, getResources().getString(R.string.no_internet_exist));
                            } else {

                            }

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        dialog.dismiss();
                        try {
                            Log.e(TAG, "error=" + e.toString());

                        } catch (Resources.NotFoundException e1) {
                            e1.printStackTrace();
                        }

                    }
                }

                @Override
                public void onFailure(Call<LoginBase> call, Throwable t) {
                    Log.e(TAG, "Unable to submit post to API.");
                    dialog.dismiss();
                }
            });

        } else {
            Log.e(TAG, "error" + "no internet");

            CommonUtils.commonToast(LoginActivity.this, getResources().getString(R.string.no_internet_exist));

        }

    }
}
