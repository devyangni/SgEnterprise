package com.sgenterprises.Home;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.sgenterprises.Common.BaseClass;
import com.sgenterprises.Model.Error;
import com.sgenterprises.Model.Registerbase;
import com.sgenterprises.R;
import com.sgenterprises.httpmanager.ApiHandler;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import app.com.commonlibrary.utils.CommonUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationActivity extends BaseClass{

    private EditText edtFname;
    private EditText edtLname;
    private EditText edtCompany;
    private EditText email;
    private EditText password;
    private EditText cpassword;
    private EditText edtAddress;
    private EditText edtCity;
    private EditText edtState;
    private EditText edtZip;
    private EditText edtfile;
    private Button btnUpload;
    private Button btnLogin;
    public static RegistrationActivity activity;
    String TAG = RegistrationActivity.class.getName();
    String first_name = null;
    String last_name = null;
    String user_role = null;
    String company_name = null;
    String emailid = null;
    String passwordd = null;
    String cpasswordd = null;
    String phone_no = null;
    String address = null;
    String city = null;
    String state = null;
    String zip = null;
    String Company = null;
    public Dialog popupDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        activity =this;
        init(activity);

        findViews();

        setAction();
    }

    private void setAction() {


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                first_name = edtFname.getText().toString();
                last_name = edtLname.getText().toString();
                Company = edtCompany.getText().toString();
                emailid = email.getText().toString();
                passwordd = password.getText().toString();
                cpasswordd = cpassword.getText().toString();
                phone_no = edtfile.getText().toString();
                address = edtAddress.getText().toString();
                city = edtCity.getText().toString();
                state = edtState.getText().toString();
                zip = edtZip.getText().toString();

                if (TextUtils.isEmpty(first_name)) {
                    CommonUtils.commonToast(RegistrationActivity.this, getResources().getString(R.string.enterfname));
                } else if (TextUtils.isEmpty(last_name)) {
                    CommonUtils.commonToast(RegistrationActivity.this, getResources().getString(R.string.enterfname));
                }else if (TextUtils.isEmpty(Company)) {
                    CommonUtils.commonToast(RegistrationActivity.this, getResources().getString(R.string.enterfname));
                }else if (TextUtils.isEmpty(phone_no)) {
                    CommonUtils.commonToast(RegistrationActivity.this, getResources().getString(R.string.entermobno));
                }else if (phone_no.length() != 10) {
                    CommonUtils.commonToast(RegistrationActivity.this, getResources().getString(R.string.entervmobno));
                }else if (TextUtils.isEmpty(emailid)) {
                    CommonUtils.commonToast(RegistrationActivity.this, getResources().getString(R.string.enteremailid));

                } else if (!CommonUtils.isEmailValid(emailid)) {
                    CommonUtils.commonToast(RegistrationActivity.this, getResources().getString(R.string.validemail));

                }  else if (TextUtils.isEmpty(passwordd)) {
                    CommonUtils.commonToast(RegistrationActivity.this, getResources().getString(R.string.enterpassword));
                } else if (passwordd.length() < 6) {
                    CommonUtils.commonToast(RegistrationActivity.this, getResources().getString(R.string.minpassword));
                } else if (TextUtils.isEmpty(cpasswordd)) {
                    CommonUtils.commonToast(RegistrationActivity.this, getResources().getString(R.string.entercpassword));
                } else if (!passwordd.toString().equals(cpasswordd.toString())) {
                    CommonUtils.commonToast(RegistrationActivity.this, getResources().getString(R.string.validcpassword));
                }else if (!CommonUtils.isTextAvailable(address)) {
                    CommonUtils.commonToast(RegistrationActivity.this, getResources().getString(R.string.enteradd));
                } else if (address.length()< 10) {
                    CommonUtils.commonToast(RegistrationActivity.this, getResources().getString(R.string.entervadd));
                }  else {
                    try {

                        getregister();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        });
    }


    protected Map<String, String> getParams() {
        Map<String, String> params = new HashMap<String, String>();
        SharedPreferences preferences=getSharedPreferences("FCM",MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        String value = preferences.getString("token", "");
        params.put("first_name", first_name);
        params.put("last_name", last_name);
        params.put("email", emailid);
        params.put("password", passwordd);
         if (CommonUtils.isTextAvailable(value)) {
            params.put("device_token",value );
        }
        else
       {
           params.put("device_token","0" );
       }

        params.put("cpassword", cpasswordd);
        params.put("phone_no", phone_no);
        if(CommonUtils.isTextAvailable(address))
        {
            params.put("address", address);
        }
        else
        {
            params.put("address", "");
        }

        if(CommonUtils.isTextAvailable(city))
        {
            params.put("city", city);
        }
        else
        {
            params.put("city", "");
        }

        if(CommonUtils.isTextAvailable(state))
        {
            params.put("state", state);
        }
        else
        {
            params.put("state", "");
        }

        if(CommonUtils.isTextAvailable(zip))
        {
            params.put("zip", zip);
        }
        else
        {
            params.put("zip", "");
        }


        params.put("company_name", Company);
        params.put("user_role", "2");
        Log.e("PArams", params.toString());

        return params;

    }

    public void getregister() {

        if (CommonUtils.isConnectingToInternet(RegistrationActivity.this)) {
            final Dialog dialog = new android.app.Dialog(activity);
            dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            dialog.setContentView(R.layout.dialog_wait);
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();


            Call<Registerbase> call = ApiHandler.getApiService().register(getParams());
            call.enqueue(new Callback<Registerbase>() {
                @Override
                public void onResponse(Call<Registerbase> call, Response<Registerbase> response) {
                    try {
                        Log.e(TAG, " Full json gson => " + new Gson().toJson(response));
                        JSONObject jsonObj = new JSONObject(new Gson().toJson(response).toString());
                        Log.e(TAG, " responce => " + jsonObj.getJSONObject("body").toString());
                        dialog.dismiss();
                        if (response.isSuccessful()) {

                            String success = response.body().getSuccess();
                            String message = response.body().getMsg();
                            if (success.equalsIgnoreCase("true")) {
                                CommonUtils.commonToast(activity, message);
                                startActivity(new Intent(activity, LoginActivity.class));
                                finish();

                            } else if (success.equalsIgnoreCase("false")) {

                                showImageDialog(response.body().getError(),activity);



                            } else {

                                CommonUtils.commonToast(RegistrationActivity.this, getResources().getString(R.string.no_internet_exist));
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
                public void onFailure(Call<Registerbase> call, Throwable t) {
                    Log.e(TAG, "Unable to submit post to API.");
                    dialog.dismiss();
                }
            });

        } else {
            Log.e(TAG, "error" + "no internet");

            CommonUtils.commonToast(RegistrationActivity.this, getResources().getString(R.string.no_internet_exist));

        }

    }


    private void findViews() {
        edtFname = (EditText) findViewById(R.id.edt_fname);
        edtLname = (EditText) findViewById(R.id.edt_lname);
        edtCompany = (EditText) findViewById(R.id.edt_companyname);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        cpassword = (EditText) findViewById(R.id.cpassword);
        edtAddress = (EditText) findViewById(R.id.edt_address);
        edtCity = (EditText) findViewById(R.id.edt_city);
        edtState = (EditText) findViewById(R.id.edt_state);
        edtZip = (EditText) findViewById(R.id.edt_zip);
        edtfile = (EditText) findViewById(R.id.edtfile);
        btnUpload = (Button) findViewById(R.id.btn_upload);
        btnLogin = (Button) findViewById(R.id.btn_login);


    }
    public void showImageDialog(Error errors, final Activity mActivity) {
        // custom dialog
        popupDialog = new Dialog(mActivity);
        popupDialog.setContentView(R.layout.dialogregister);
        popupDialog.setCancelable(true);
TextView edte1,edte2,edte3,edte4,e,edte5,edte6,edte7;
Button txtDialogCreateMovment;

        edte1 = (TextView)popupDialog.findViewById( R.id.error1 );
        edte2 = (TextView)popupDialog.findViewById( R.id.error2 );
        edte3 = (TextView)popupDialog.findViewById( R.id.error3 );
        edte4 = (TextView)popupDialog.findViewById( R.id.error4 );
        edte5 = (TextView)popupDialog.findViewById( R.id.error5 );
        edte6 = (TextView)popupDialog.findViewById( R.id.error6 );
        edte7 = (TextView)popupDialog.findViewById( R.id.error7 );

        edte1.setVisibility(View.GONE);
        edte2.setVisibility(View.GONE);
        edte3.setVisibility(View.GONE);
        edte4.setVisibility(View.GONE);
        edte5.setVisibility(View.GONE);
        edte6.setVisibility(View.GONE);
        edte7.setVisibility(View.GONE);

        String error ="",error1 ="",error2 ="",error3 ="",error4 ="",error5 ="",error6 ="";
        error =    errors.getFirstName();
        error1 =   errors.getLastName();
        error2 =   errors.getCompanyName();
        error3 =   errors.getPhoneNo();
        error4 =   errors.getPassword();
        error5 =   errors.getEmail();
        error6 =   errors.getAddress();

        if (CommonUtils.isTextAvailable(error))
        {   edte1.setVisibility(View.VISIBLE);
            edte1.setText(error);

        }
        if (CommonUtils.isTextAvailable(error1))
        {
            edte2.setVisibility(View.VISIBLE);
            edte2.setText(error1);

        }
        if (CommonUtils.isTextAvailable(error2))
        {   edte3.setVisibility(View.VISIBLE);
            edte3.setText(error2);
        }

        if (CommonUtils.isTextAvailable(error5))
        {   edte4.setVisibility(View.VISIBLE);
            edte4.setText(error5);

        }
        if (CommonUtils.isTextAvailable(error4))
        {   edte5.setVisibility(View.VISIBLE);
            edte5.setText(error4);
            password.setHint(error4);
        }
        if (CommonUtils.isTextAvailable(error3))
        {   edte6.setVisibility(View.VISIBLE);
            edte6.setText(error3);

        }  if (CommonUtils.isTextAvailable(error6))
        {    edte7.setVisibility(View.VISIBLE);
            edte7.setText(error6);

        }


        txtDialogCreateMovment = (Button)popupDialog.findViewById( R.id.txtDialogCreateMovment );



        // Close Button
        txtDialogCreateMovment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupDialog.dismiss();

                //TODO Close button action
            }
        });


        popupDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));

        popupDialog.show();
    }

}
