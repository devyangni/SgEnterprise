
package com.sgenterprises.Fragment;


import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;



import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;


import com.google.gson.Gson;
import com.sgenterprises.Model.Material;
import com.sgenterprises.Model.MaterialBase;
import com.sgenterprises.R;
import com.sgenterprises.ServiceResideMenu.ResideMenu;
import com.sgenterprises.httpmanager.ApiHandler;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import app.com.commonlibrary.utils.CommonUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactusFragment extends Fragment {

    Animation animSlideUp;
    ArrayList<String> calendarList = new ArrayList<String>();
    private View parentView;
    private ResideMenu resideMenu;
    private Toolbar toolbar;
    String name,captcha1,captch2,email,msg;
    private EditText edtName;
    private EditText edtEmail;
    private Spinner getAw;
    private EditText edtCaptch;
    private EditText edt_msg;
    private Button btnSubmit;
    private TextView txt_capch;
    String generatedPassword;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.activity_contactus, container, false);

        findViews();
        randomno();
        return parentView;
    }

    public  void randomno()
    {
        Random random = new Random();
        generatedPassword = String.format("%04d", random.nextInt(10000));
        txt_capch.setText("Captcha code : "+ generatedPassword);;

        Log.d("MyApp", "Generated Password : " + generatedPassword);
    }

    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2018-04-10 23:51:29 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews() {
        edtName = (EditText)parentView.findViewById( R.id.edt_name );
        edtEmail = (EditText)parentView.findViewById( R.id.edt_email );
        txt_capch = (TextView) parentView.findViewById( R.id.txt_capch );

        edtCaptch = (EditText)parentView.findViewById( R.id.edt_captch );
        edt_msg = (EditText)parentView.findViewById( R.id.edt_msg );
        btnSubmit = (Button)parentView.findViewById( R.id.btn_submit );
        txt_capch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                randomno();
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                name = edtName.getText().toString().trim();
                email = edtEmail.getText().toString().trim();
                captch2 = edtCaptch.getText().toString().trim();
                msg = edt_msg.getText().toString().trim();


                if (!CommonUtils.isTextAvailable(name)) {
                    CommonUtils.commonToast(getActivity(), "Enter name");

                } else if (!CommonUtils.isTextAvailable(email)) {
                    CommonUtils.commonToast(getActivity(), "Enter emailid");

                }
                else if (!CommonUtils.isEmailValid(email)) {
                    CommonUtils.commonToast(getActivity(), "Enter Valid emailid");

                }else if (!CommonUtils.isTextAvailable(generatedPassword)) {
                    CommonUtils.commonToast(getActivity(), "Select captcha");

                } else if (!CommonUtils.isTextAvailable(captch2)) {
                    CommonUtils.commonToast(getActivity(), "Enter captcha  number");

                } else if (!generatedPassword.equalsIgnoreCase(captch2)) {
                    CommonUtils.commonToast(getActivity(), "Captcha code does not match");

                }else if (!CommonUtils.isTextAvailable(msg)) {
                    CommonUtils.commonToast(getActivity(), "Enter message ");

                }else if (msg.length() < 50) {
                    CommonUtils.commonToast(getActivity(), "Minimum message lenght should be 50 characters");

                } else {

                    sendorder();
                }

            }
        });
    }


    protected Map<String, String> getParamsorder() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("name", name);
        params.put("email", email);
        params.put("message", msg);
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


            Call<MaterialBase> call = ApiHandler.getApiService().contact(getParamsorder());
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

                                edtName.setText("");
                                edtEmail.setText("");
                                edtCaptch.setText("");
                                edt_msg.setText("");


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



}

