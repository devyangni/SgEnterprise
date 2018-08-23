package com.sgenterprises.Fragment;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;


import com.google.gson.Gson;
import com.sgenterprises.Home.LoginActivity;
import com.sgenterprises.Model.CustomerBase;
import com.sgenterprises.Model.Data;
import com.sgenterprises.Model.Datum;
import com.sgenterprises.Model.Material;
import com.sgenterprises.Model.MaterialBase;
import com.sgenterprises.Model.MaterialBase;
import com.sgenterprises.R;
import com.sgenterprises.httpmanager.ApiHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.com.commonlibrary.utils.CommonUtils;
import app.com.commonlibrary.utils.SharedPreferenceHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;
import static com.sgenterprises.Common.BaseClass.commonSession;

public class OrderDetailFragment extends Fragment {
    private static final int PICKFILE_REQUEST_CODE = 1;
    Animation animSlideUp;
    int screen_height = 0, screen_width = 0;
    String userImage, firstName, lastName, email, phone, home, office, other;
    String HomeAddress1 = "";
    String OtherAddress1 = "";
    String OfficeAddress1 = "";
    String filePath = null;
    private View parentView;
    private String materianame;
    private Spinner spCustomer;
    private EditText edtCustomername;
    private Spinner spMaterial;
    private EditText edtLocation;
    private EditText edtLenth;
    private EditText edtBreadth;
    private EditText edtHeight;
    private EditText edtPrice;
    private EditText edtPaygiven;
    private EditText edtPaydue;
    private Button btnInsert;

    String Loc ="";
    TextView txt_cnamee;
    ArrayList<String> materiallist;
    ArrayList<String> materialid;
    ArrayList<String> Customerlist;
    ArrayList<String> customerid;


    ArrayList<String> Customerlista;
    ArrayList<String> customerida;

    String cname ="";
    String cid="";

    String location,lenght,breadth,height,price,pay,paydue;


    private String menuId;
    private String menuName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.activity_orderdetail, container, false);
        findViews();
        getcustomerid();
        getmaterial();

        return parentView;
    }



    private void findViews() {
        spCustomer = (Spinner) parentView.findViewById(R.id.sp_customer);
        edtCustomername = (EditText) parentView.findViewById(R.id.edt_customername);
        spMaterial = (Spinner) parentView.findViewById(R.id.sp_material);
        edtLocation = (EditText) parentView.findViewById(R.id.edt_location);
        edtLenth = (EditText) parentView.findViewById(R.id.edt_lenth);
        edtBreadth = (EditText) parentView.findViewById(R.id.edt_breadth);
        edtHeight = (EditText) parentView.findViewById(R.id.edt_height);
        edtPrice = (EditText) parentView.findViewById(R.id.edt_price);
        edtPaygiven = (EditText) parentView.findViewById(R.id.edt_paygiven);
        edtPaydue = (EditText) parentView.findViewById(R.id.edt_paydue);
        btnInsert = (Button) parentView.findViewById(R.id.btn_insert);
        txt_cnamee = (TextView) parentView.findViewById(R.id.txt_cnamee);

        Loc = commonSession.getSelectedLanguage();
        if (CommonUtils.isTextAvailable(Loc))
        {
            edtLocation.setText(Loc);
        }
        edtCustomername.setVisibility(View.GONE);
        txt_cnamee.setVisibility(View.GONE);
        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                location = edtLocation.getText().toString().trim();
                lenght = edtLenth.getText().toString().trim();
                breadth = edtBreadth.getText().toString().trim();
                height = edtHeight.getText().toString().trim();
                price = edtPrice.getText().toString().trim();
                pay = edtPaygiven.getText().toString().trim();
                paydue = edtPaydue.getText().toString().trim();


                if (cid.equalsIgnoreCase("99999")) {
                    cname = edtCustomername.getText().toString().trim();
                } else {
                    edtCustomername.setText(cname);
                }
                if (!CommonUtils.isTextAvailable(location)) {
                    CommonUtils.commonToast(getActivity(), "Enter location");

                } else if (!CommonUtils.isTextAvailable(lenght)) {
                    CommonUtils.commonToast(getActivity(), "Enter lenght");

                } else if (!CommonUtils.isTextAvailable(breadth)) {
                    CommonUtils.commonToast(getActivity(), "Enter breadth");

                } else if (!CommonUtils.isTextAvailable(height)) {
                    CommonUtils.commonToast(getActivity(), "Enter height");

                } else if (!CommonUtils.isTextAvailable(price)) {
                    CommonUtils.commonToast(getActivity(), "Enter price");

                } else {

                    sendorder();
                }
            }
        });
    }

        protected Map<String, String> getParamsorder() {
            Map<String, String> params = new HashMap<String, String>();


            params.put("customer_id", cid);
            params.put("customer_name", cname);
            params.put("material_id", menuId);
            params.put("location", location);
            params.put("length", lenght);
            params.put("breadth", breadth);
            params.put("height", height);
            params.put("price", price);
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

            if (CommonUtils.isConnectingToInternet(getActivity())) {
                final Dialog dialog = new android.app.Dialog(getContext());
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


                                    CommonUtils.commonToast(getActivity(), message);
                                    final List<Material> data = response.body().getData();
                                    edtLocation.setText("");
                                    edtHeight.setText("");
                                    edtBreadth.setText("");
                                    edtLenth.setText("");
                                    edtPaydue.setText("");
                                    edtPaygiven.setText("");
                                    edtPrice.setText("");


                                } else if (success.equalsIgnoreCase("false"))

                                {

                                    CommonUtils.commonToast(getActivity(), message);
                                } else if (success.equalsIgnoreCase("0"))

                                {

                                    CommonUtils.commonToast(getActivity(), getResources().getString(R.string.no_internet_exist));
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

                CommonUtils.commonToast(getActivity(), getResources().getString(R.string.no_internet_exist));

            }

        }






    protected Map<String, String> getParams() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("email", "w");
        params.put("password", "w");
        Log.e("PArams", params.toString());
        return params;

    }

    public void getmaterial() {

        if (CommonUtils.isConnectingToInternet(getActivity())) {
            final Dialog dialog = new android.app.Dialog(getContext());
            dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            dialog.setContentView(R.layout.dialog_wait);
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();


            Call<MaterialBase> call = ApiHandler.getApiService().material(getParams());
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
                                CommonUtils.commonToast(getActivity(), message);

                                final List<Material> data = response.body().getData();
                                materiallist = new ArrayList<String>();
                                materialid = new ArrayList<String>();
                                for (int i = 0; i < data.size(); i++) {

                                    materiallist.add(data.get(i).getMaterialName());
                                    materialid.add( data.get(i).getMaterialId());
                                }


                                spMaterial.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, materiallist));
                                spMaterial.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        String item = (String) parent.getItemAtPosition(position);

                                        int pos = 0;

                                        for (int i = 0; i < materialid.size(); i++) {
                                            pos = materiallist.indexOf(item);
                                            menuId = materialid.get(pos).toString();
                                            menuName = materiallist.get(pos).toString();

                                        }



                                    }
                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });


                            } else if (success.equalsIgnoreCase("false"))

                            {

                                CommonUtils.commonToast(getActivity(), message);
                            } else if (success.equalsIgnoreCase("0"))

                            {

                                CommonUtils.commonToast(getActivity(), getResources().getString(R.string.no_internet_exist));
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

            CommonUtils.commonToast(getActivity(), getResources().getString(R.string.no_internet_exist));

        }

    }

    public void getcustomerid() {

        if (CommonUtils.isConnectingToInternet(getActivity())) {
            final Dialog dialog = new android.app.Dialog(getContext());
            dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            dialog.setContentView(R.layout.dialog_wait);
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();


            Call<CustomerBase> call = ApiHandler.getApiService().customer(getParams());
            call.enqueue(new Callback<CustomerBase>() {
                @Override
                public void onResponse(Call<CustomerBase> call, Response<CustomerBase> response) {
                    try {
                        Log.e("m", " Full json gson => " + new Gson().toJson(response));
                        JSONObject jsonObj = new JSONObject(new Gson().toJson(response).toString());
                        Log.e("n", " responce => " + jsonObj.getJSONObject("body").toString());
                        dialog.dismiss();
                        if (response.isSuccessful()) {

                            String success = response.body().getSuccess();
                            String message = response.body().getMsg();
                            if (success.equalsIgnoreCase("true")) {
                                CommonUtils.commonToast(getActivity(), message);

                                final List<Datum> data = response.body().getData();
                                Customerlist = new ArrayList<String>();
                                customerid = new ArrayList<String>();

                                Customerlista = new ArrayList<String>();
                                customerida = new ArrayList<String>();

                                Customerlista.add("other");
                                customerida.add("99999");
                                for (int i = 0; i < data.size(); i++) {

                                    Customerlist.add(data.get(i).getUsername());
                                    customerid.add( data.get(i).getUserId());
                                }
                                customerid.addAll(customerida);
                                Customerlist.addAll(Customerlista);


                                spCustomer.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, Customerlist));
                                spCustomer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        String item = (String) parent.getItemAtPosition(position);

                                        int pos = 0;

                                        for (int i = 0; i < customerid.size(); i++) {
                                            pos = Customerlist.indexOf(item);
                                            cid = customerid.get(pos).toString();


                                        }

                                        if (cid.equalsIgnoreCase("99999"))
                                        {
                                            edtCustomername.setVisibility(View.VISIBLE);
                                            txt_cnamee.setVisibility(View.VISIBLE);
                                        }
                                        else
                                        {
                                            edtCustomername.setVisibility(View.GONE);
                                            txt_cnamee.setVisibility(View.GONE);
                                        }



                                    }
                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });


                            } else if (success.equalsIgnoreCase("false"))

                            {

                                CommonUtils.commonToast(getActivity(), message);
                            } else if (success.equalsIgnoreCase("0"))

                            {

                                CommonUtils.commonToast(getActivity(), getResources().getString(R.string.no_internet_exist));
                            } else

                            {

                            }


                        }
                    } catch (Exception e) {

                    }
                }

                @Override
                public void onFailure(Call<CustomerBase> call, Throwable t) {

                    dialog.dismiss();
                }
            });

        } else {

            CommonUtils.commonToast(getActivity(), getResources().getString(R.string.no_internet_exist));

        }

    }



}
