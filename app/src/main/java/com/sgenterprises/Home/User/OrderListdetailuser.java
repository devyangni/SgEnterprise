package com.sgenterprises.Home.User;

import android.app.Dialog;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.sgenterprises.Adapter.Adapter_orderall;
import com.sgenterprises.Common.BaseClass;
import com.sgenterprises.Fragment.OrderListAllFragment;
import com.sgenterprises.Model.MaterialBase;
import com.sgenterprises.Model.UserDatum;
import com.sgenterprises.Model.UsersBase;
import com.sgenterprises.R;
import com.sgenterprises.httpmanager.ApiHandler;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import app.com.commonlibrary.utils.CommonUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.sgenterprises.Fragment.OrderListAllFragment.adapter_events;
import static com.sgenterprises.Fragment.OrderListAllFragment.data;
import static com.sgenterprises.Fragment.OrderListAllFragment.recyclerList;

public class OrderListdetailuser extends BaseClass {
    Bundle b ;
    UserDatum  userDatum ;


    String TAG = OrderListdetailuser.class.getName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookedo);
        findViews();
        b =getIntent().getExtras();
        if (b!= null){

            userDatum = b.getParcelable("list");

            setdata();
        }



    }

    private void setdata() {

        try {
            if (CommonUtils.isTextAvailable(userDatum.getFirstName())) {

                password.setText(userDatum.getFirstName());


            }
            if (CommonUtils.isTextAvailable(userDatum.getMaterialName())) {
                materialname.setText(userDatum.getMaterialName());

            }
            if (CommonUtils.isTextAvailable(userDatum.getLocation())) {
                edtlocation.setText(userDatum.getLocation());

            }
            if (CommonUtils.isTextAvailable(userDatum.getLength())) {
                edtlenght.setText(userDatum.getLength());

            }
            if (CommonUtils.isTextAvailable(userDatum.getBreadth())) {
                edtbreadth.setText(userDatum.getBreadth());


            }
            if (CommonUtils.isTextAvailable(userDatum.getHeight())) {
                edtHeight.setText(userDatum.getHeight());


            }
            if (CommonUtils.isTextAvailable(userDatum.getPrice())) {
                edtPrice.setText(userDatum.getPrice());


            }
            if (CommonUtils.isTextAvailable(userDatum.getPaymentGiven())) {
                edtPaygiven.setText(userDatum.getPaymentGiven());


            }
            if (CommonUtils.isTextAvailable(userDatum.getPaymentDue())) {
                edtPaydue.setText(userDatum.getPaymentDue());
            }

            orderid =userDatum.getOrderId();



        }
        catch (Exception e)
        {
            Log.e("e", String.valueOf(e));
        }




    }


    private EditText password;
    private EditText materialname;
    private EditText edtlocation;
    private EditText edtlenght;
    private EditText edtbreadth;
    private EditText edtHeight;
    private EditText edtPrice;
    private EditText edtPaygiven;
    private EditText edtPaydue;
    public TextView edtprofile;
    public Button btnLogin;


    String cname ="";
    String cid="";
    String orderid="";
    String menuId="";

    String location,lenght,breadth,height,price,pay,paydue;


    private void findViews() {
        password = (EditText)findViewById( R.id.password );
        materialname = (EditText)findViewById( R.id.materialname );
        edtlocation = (EditText)findViewById( R.id.location );
        edtlenght = (EditText)findViewById( R.id.lenght );
        edtbreadth = (EditText)findViewById( R.id.breadth );
        edtHeight = (EditText)findViewById( R.id.edt_height );
        edtPrice = (EditText)findViewById( R.id.edt_price );
        edtPaygiven = (EditText)findViewById( R.id.edt_paygiven );
        edtPaydue = (EditText)findViewById( R.id.edt_paydue );
        edtprofile = (TextView) findViewById(R.id.edtprofile);
        btnLogin = (Button) findViewById(R.id.btn_login);
        edtprofile.setVisibility(View.VISIBLE);
        btnLogin.setVisibility(View.GONE);

        edtprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                materialname.setFocusable(true);
                edtlocation.setFocusable(true);
                edtlenght.setFocusable(true);
                edtbreadth.setFocusable(true);
                password.setFocusable(true);
                edtHeight.setFocusable(true);
                edtPrice.setFocusable(true);
                edtPaygiven.setFocusable(true);
                edtPaydue.setFocusable(true);


                password.setFocusableInTouchMode(true);
                materialname.setFocusableInTouchMode(true);
                edtlocation.setFocusableInTouchMode(true);
                edtlenght.setFocusableInTouchMode(true);
                edtbreadth.setFocusableInTouchMode(true);
                edtHeight.setFocusableInTouchMode(true);
                edtPrice.setFocusableInTouchMode(true);
                edtPaygiven.setFocusableInTouchMode(true);
                edtPaydue.setFocusableInTouchMode(true);

                edtprofile.setVisibility(View.GONE);
                btnLogin.setVisibility(View.VISIBLE);


            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                cname = password.getText().toString().trim();
                location = edtlocation.getText().toString().trim();
                lenght = edtlenght.getText().toString().trim();
                breadth = edtbreadth.getText().toString().trim();
                height = edtHeight.getText().toString().trim();
                price = edtPrice.getText().toString().trim();
                pay = edtPaygiven.getText().toString().trim();
                paydue = edtPaydue.getText().toString().trim();



                if (!CommonUtils.isTextAvailable(cname)) {
                    CommonUtils.commonToast(OrderListdetailuser.this, "Enter Customer name");

                } else if (!CommonUtils.isTextAvailable(location)) {
                    CommonUtils.commonToast(OrderListdetailuser.this, "Enter location");

                } else if (!CommonUtils.isTextAvailable(lenght)) {
                    CommonUtils.commonToast(OrderListdetailuser.this, "Enter lenght");

                } else if (!CommonUtils.isTextAvailable(breadth)) {
                    CommonUtils.commonToast(OrderListdetailuser.this, "Enter breadth");

                } else if (!CommonUtils.isTextAvailable(height)) {
                    CommonUtils.commonToast(OrderListdetailuser.this, "Enter height");

                } else if (!CommonUtils.isTextAvailable(price)) {
                    CommonUtils.commonToast(OrderListdetailuser.this, "Enter price");

                } else {

                    sendorder();
                }
            }
        });
    }



    protected Map<String, String> getParamsorder() {
        Map<String, String> params = new HashMap<String, String>();
cid =userDatum.getCustomer_id();
menuId =userDatum.getMaterial_id();

        if (CommonUtils.isTextAvailable(cid))

        {
            params.put("customer_id", cid);
        }
        else
        {
            cid ="24";
            params.put("customer_id", cid);
        }

        if (CommonUtils.isTextAvailable(menuId))

        {
            params.put("material_id", menuId);
        }
        else
        {
            menuId ="1";
            params.put("material_id", menuId);
        }

        params.put("customer_name", cname);
        params.put("location", location);
        params.put("length", lenght);
        params.put("breadth", breadth);
        params.put("height", height);
        params.put("price", price);
        params.put("order_id", orderid);
        if (CommonUtils.isTextAvailable(pay))
        {
            params.put("payment_given", pay);
        }
        else
        {
            pay ="0";
            params.put("payment_given", pay);
        }
        Log.e("PArams", params.toString());
        return params;

    }

    public void sendorder() {

        if (CommonUtils.isConnectingToInternet(OrderListdetailuser.this)) {
            final Dialog dialog = new Dialog(OrderListdetailuser.this);
            dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            dialog.setContentView(R.layout.dialog_wait);
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();


            Call<MaterialBase> call = ApiHandler.getApiService().order(getParamsorder());
            call.enqueue(new Callback<MaterialBase>() {
                @Override
                public void onResponse(Call<MaterialBase> call, Response<MaterialBase> response) {
                    try {
                        Log.e("m", " Full json gson => " + new Gson().toJson(response));
                        JSONObject jsonObj = new JSONObject(new Gson().toJson(response).toString());
                        Log.e("n", " responce => " + jsonObj.getJSONObject("body").toString());
                        dialog.dismiss();
                        if (response.isSuccessful()) {

                            String success = response.body().getSuccess();
                            String message = response.body().getMsg();
                            if (success.equalsIgnoreCase("true")) {


                                CommonUtils.commonToast(OrderListdetailuser.this, message);
                                edtprofile.setVisibility(View.VISIBLE);
                                btnLogin.setVisibility(View.GONE);

                                materialname.setFocusable(false);
                                edtlocation.setFocusable(false);
                                edtlenght.setFocusable(false);
                                edtbreadth.setFocusable(false);
                                password.setFocusable(false);
                                edtHeight.setFocusable(false);
                                edtPrice.setFocusable(false);
                                edtPaygiven.setFocusable(false);
                                edtPaydue.setFocusable(false);


                                password.setFocusableInTouchMode(false);
                                materialname.setFocusableInTouchMode(false);
                                edtlocation.setFocusableInTouchMode(false);
                                edtlenght.setFocusableInTouchMode(false);
                                edtbreadth.setFocusableInTouchMode(false);
                                edtHeight.setFocusableInTouchMode(false);
                                edtPrice.setFocusableInTouchMode(false);
                                edtPaygiven.setFocusableInTouchMode(false);
                                edtPaydue.setFocusableInTouchMode(false);


                                getEvent();

                            } else if (success.equalsIgnoreCase("false"))

                            {

                                CommonUtils.commonToast(OrderListdetailuser.this, message);
                            } else if (success.equalsIgnoreCase("0"))

                            {

                                CommonUtils.commonToast(OrderListdetailuser.this, getResources().getString(R.string.no_internet_exist));
                            } else

                            {

                            }


                        }
                    } catch (Exception e) {

                    }
                }

                @Override
                public void onFailure(Call<MaterialBase> call, Throwable t) {

                    dialog.dismiss();
                }
            });

        } else {

            CommonUtils.commonToast(OrderListdetailuser.this, getResources().getString(R.string.no_internet_exist));

        }

    }

    protected Map<String, String> getParamsordera() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("id", "");
        params.put("date", "");
        params.put("material", "");
        params.put("customer_name", "");

        Log.e("PArams", params.toString());
        return params;

    }

    public void getEvent() {

        if (CommonUtils.isConnectingToInternet(OrderListdetailuser.this)) {
            final Dialog dialog = new Dialog(OrderListdetailuser.this);
            dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setContentView(R.layout.dialog_wait);
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
            Call<UsersBase> call = ApiHandler.getApiService().get_order_list(getParamsordera());
            call.enqueue(new Callback<UsersBase>() {
                @Override
                public void onResponse(Call<UsersBase> call, Response<UsersBase> response) {
                    try {
                        Log.e(TAG, " Full json gson => " + new Gson().toJson(response));
                        JSONObject jsonObj = null;
                        jsonObj = new JSONObject(new Gson().toJson(response).toString());
                        Log.e(TAG, " responce => " + jsonObj.getJSONObject("body").toString());
                        dialog.dismiss();
                        if (response.isSuccessful()) {

                            String success = response.body().getSuccess();
                            String message = response.body().getMsg();

                            if (success.equalsIgnoreCase("true")) {
                                data = response.body().getUserData();

                                if (data.size() > 0) {
                                    recyclerList.setVisibility(View.VISIBLE);
                                    adapter_events = new Adapter_orderall(OrderListdetailuser.this, data, recyclerList);
                                    recyclerList.setAdapter(adapter_events);
                                }
                                else
                                {
                                    recyclerList.setVisibility(View.GONE);
                                }

                            } else {

                                CommonUtils.commonToast(OrderListdetailuser.this,message);
                                recyclerList.setVisibility(View.GONE);

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
                public void onFailure(Call<UsersBase> call, Throwable t) {
                    Log.e(TAG, "Unable to submit post to API.");

                    dialog.dismiss();
                }
            });

        } else {
            Log.e(TAG, "error" + "no internet");

            CommonUtils.commonToast(OrderListdetailuser.this, getResources().getString(R.string.no_internet_exist));

        }

    }




}
