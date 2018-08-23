
package com.sgenterprises.Fragment;


import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.sgenterprises.Model.CalculateBase;
import com.sgenterprises.Model.Material;
import com.sgenterprises.Model.MaterialBase;
import com.sgenterprises.Model.Msg;
import com.sgenterprises.R;
import com.sgenterprises.ServiceResideMenu.ResideMenu;
import com.sgenterprises.httpmanager.ApiHandler;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.com.commonlibrary.utils.CommonUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class calculateFragment extends Fragment {

    Animation animSlideUp;
    ArrayList<String> calendarList = new ArrayList<String>();
    private View parentView;
    private ResideMenu resideMenu;
    private Toolbar toolbar;
    String name,captcha1,captch2,email,msg;
    private EditText edtName;
    private TextView edtEmail;
    private Spinner getAw;
    private TextView edtCaptch;
    private TextView edt_msg;
    private Button btnSubmit;
    private TextView txt_capch;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.activity_calculate, container, false);

        findViews();
        return parentView;
    }



    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2018-04-10 23:51:29 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews() {
        edtName = (EditText)parentView.findViewById( R.id.edt_amount );
        edtEmail = (TextView)parentView.findViewById( R.id.edt_email );

        edtCaptch = (TextView)parentView.findViewById( R.id.edt_captch );
        edt_msg = (TextView)parentView.findViewById( R.id.edt_mss );
        btnSubmit = (Button)parentView.findViewById( R.id.btn_submit );

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                name = edtName.getText().toString().trim();


                if (!CommonUtils.isTextAvailable(name)) {
                    CommonUtils.commonToast(getActivity(), "Enter Amount");

                } else {

                    sendorder();
                }

            }
        });
    }


    protected Map<String, String> getParamsorder() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("amount", name);
     ;
        Log.e("PArams", params.toString());
        return params;

    }

    public void sendorder() {

        if (CommonUtils.isConnectingToInternet(getActivity())) {
            final Dialog dialog = new Dialog(getContext());
            dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            dialog.setContentView(R.layout.dialog_wait);
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();


            Call<CalculateBase> call = ApiHandler.getApiService().gst(getParamsorder());
            call.enqueue(new Callback<CalculateBase>() {
                @Override
                public void onResponse(Call<CalculateBase> call, Response<CalculateBase> response) {
                    try {
                        Log.e("m", " Full json gson => " + new Gson().toJson(response));
                        JSONObject jsonObj = new JSONObject(new Gson().toJson(response).toString());
                        Log.e("n", " responce => " + jsonObj.getJSONObject("body").toString());
                        dialog.dismiss();
                        if (response.isSuccessful()) {

                            String success = response.body().getSuccess();

                            if (success.equalsIgnoreCase("true")) {


                                final Msg data = response.body().getMsg();

                               String cgst = data.getCgst();
                               String Sgst = data.getSgst();
                               String TotalAmount = data.getTotalAmount();

                                edtEmail.setText("Total Cost : "+ TotalAmount);
                                edtCaptch.setText("C GST 2.5 % : " + cgst);
                                edt_msg.setText("S GST 2.5 % : "+Sgst);


                            } else if (success.equalsIgnoreCase("false"))

                            {


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
                public void onFailure(Call<CalculateBase> call, Throwable t) {

                    dialog.dismiss();
                }
            });

        } else {

            CommonUtils.commonToast(getActivity(), getResources().getString(R.string.no_internet_exist));

        }

    }



}
