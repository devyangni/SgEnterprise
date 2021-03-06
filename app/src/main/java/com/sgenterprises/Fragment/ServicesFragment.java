package com.sgenterprises.Fragment;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.GridView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.sgenterprises.Common.URLImageParser;
import com.sgenterprises.Home.LoginActivity;
import com.sgenterprises.Model.HomeBase;
import com.sgenterprises.R;
import com.sgenterprises.ServiceResideMenu.ResideMenu;
import com.sgenterprises.httpmanager.ApiHandler;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import app.com.commonlibrary.utils.CommonUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServicesFragment extends Fragment {

    Animation animSlideUp;
 //   HomeListAdapter homeListAdapter;
    GridView gridView;
    private View parentView;
    private ResideMenu resideMenu;
    private Toolbar toolbar;
    private RecyclerView RecycleVmavaliableservice;
    private int StartLimit = 0;
    int screen_height = 0, screen_width = 0;
    TextView txt_logon;
    TextView txt_about_us;
  //  MyProgressDialog myProgressDialog;
   // private List<CategorieResult> requestbean = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.home_service, container, false);


        setUpViews();
       // txt_logon = (TextView)parentView.findViewById(R.id.txt_logon);
        txt_about_us = (TextView)parentView.findViewById(R.id.txt_about_us);


        getAboutus();
        return parentView;
    }

    private void setUpViews() {

        MenuActivity parentActivity = (MenuActivity) getActivity();
        resideMenu = parentActivity.getResideMenu();

        // Instance of ImageAdapter Class
    }

    protected Map<String, String> getParams() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("email", "w");
        params.put("password", "w");
        Log.e("PArams", params.toString());
        return params;

    }

    public void getAboutus() {

        if (CommonUtils.isConnectingToInternet(getActivity())) {
            final Dialog dialog = new Dialog(getActivity());
            dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            dialog.setContentView(R.layout.dialog_wait);
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
            Call<HomeBase> call = ApiHandler.getApiService().servicezz(getParams());
            call.enqueue(new Callback<HomeBase>() {
                @Override
                public void onResponse(Call<HomeBase> call, Response<HomeBase> response) {
                    try

                    {
                        Log.e("d", " Full json gson => " + new Gson().toJson(response));
                        JSONObject jsonObj = new JSONObject(new Gson().toJson(response).toString());

                        Log.e("d", " responce => " + jsonObj.getJSONObject("body").toString());
                        dialog.dismiss();
                        if (response.isSuccessful()) {

                            String success = response.body().getSuccess();

                            if ( success.equalsIgnoreCase("true")) {

                                String contetnt = response.body().getContent();
                                //txt_about_us.setText(Html.fromHtml(contetnt.toString()));

                                URLImageParser p = new URLImageParser(txt_about_us, getActivity());
                                Spanned htmlSpan = Html.fromHtml(contetnt, p, null);
                                txt_about_us.setText(htmlSpan);
                            } else {

                            }

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        dialog.dismiss();
                        try {

                        } catch (Resources.NotFoundException e1) {
                            e1.printStackTrace();
                        }

                    }
                }

                @Override
                public void onFailure(Call<HomeBase> call, Throwable t) {

                    dialog.dismiss();
                }
            });

        } else {


            CommonUtils.commonToast(getActivity(), getResources().getString(R.string.no_internet_exist));

        }

    }



}


