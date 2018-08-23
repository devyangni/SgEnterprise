package com.sgenterprises.Fragment;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.sgenterprises.Adapter.Adapter_contactlist;
import com.sgenterprises.Adapter.Adapter_userlist;
import com.sgenterprises.Home.RegistrationActivity;
import com.sgenterprises.Model.Contactlist;
import com.sgenterprises.Model.CustomerBase;
import com.sgenterprises.Model.Datum;
import com.sgenterprises.Model.UserList;
import com.sgenterprises.Model.UserlistBase;
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

public class UserListAllFragment extends Fragment {
    Animation animSlideUp;
    GridView gridView;
    int screen_height = 0, screen_width = 0;
    public Dialog popupDialog;
    private View parentView;
    private ResideMenu resideMenu;
    private Toolbar toolbar;
    private RecyclerView RecycleVmavaliableservice;

    private TextView txtDateOrder;
    private TextView datedisplayOrder;
    private TextView filter;
    private TextView addnew;
    AutoCompleteTextView datepicker_order;
    private ImageView datepickerOrder;

    int mYear, mMonth, mDay;
    private String date = "";
    Adapter_userlist adapter_events;
    String TAG = UserListAllFragment.class.getName();
    List<UserList> data = new ArrayList<>();


    private AutoCompleteTextView datez;
    private AutoCompleteTextView name;
    private AutoCompleteTextView qty;

    private RecyclerView recyclerList;
    ArrayList<String> Customerlist;
    ArrayList<String> customerid;
    ArrayList<String> companyname;

ImageView refresh;
    ArrayList<String> Customerlista;
    ArrayList<String> customerida;

    String cname="";
    String cid="";
    String comapny="";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        parentView = inflater.inflate(R.layout.activity_user_listing, container, false);

        //data = new ArrayList<>();
        //
        findViews();
        //getmaterial();
        getEvent();
        getuserlist();


        return parentView;

    }

    private void findViews() {
        datez = (AutoCompleteTextView) parentView.findViewById(R.id.date);
        name = (AutoCompleteTextView) parentView.findViewById(R.id.name);
        qty = (AutoCompleteTextView) parentView.findViewById(R.id.qty);
        addnew = (TextView) parentView.findViewById(R.id.addnew);

        refresh = (ImageView)parentView.findViewById( R.id.refresh );
        recyclerList = (RecyclerView) parentView.findViewById(R.id.recycler_list);
        recyclerList.setHasFixedSize(true);
        recyclerList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerList.setNestedScrollingEnabled(false);
        name.setOnEditorActionListener(new AutoCompleteTextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    cname = name.getText().toString().trim();
                    cid ="";
                    comapny ="";
                    getEvent();
                    return true;
                }
                return false;
            }
        });
        addnew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), RegistrationActivity.class));
            }
        });
       /* datez.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                showDatePicker();

            }
        });



        status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showImageDialog(getActivity());
            }
        });*/


        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                cid ="";
                cname="";
                comapny="";
                datez.setText("");
                name.setText("");
                qty.setText("");
                getEvent();
            }
        });


    }

   /* private void showDatePicker() {
        DatePickerFragment date = new DatePickerFragment();
        *//**
         * Set Up Current Date Into dialog
         *//*
        Calendar calender = Calendar.getInstance();
        Bundle args = new Bundle();
        args.putInt("year", calender.get(Calendar.YEAR));
        args.putInt("month", calender.get(Calendar.MONTH));
        args.putInt("day", calender.get(Calendar.DAY_OF_MONTH));
        date.setArguments(args);
        *//**
         * Set Call back to capture selected date
         *//*
        date.setCallBack(ondate);
        date.show(getFragmentManager(), "Date Picker");


    }
*/

    protected Map<String, String> getParamsorder() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("user_id", cid);
        params.put("username", cname);
        params.put("company_name", comapny);
        Log.e("PArams", params.toString());
        return params;

    }

    public void getEvent() {

        if (CommonUtils.isConnectingToInternet(getContext())) {
            final Dialog dialog = new Dialog(getActivity());
            dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setContentView(R.layout.dialog_wait);
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
            Call<UserlistBase> call = ApiHandler.getApiService().user_listing(getParamsorder());
            call.enqueue(new Callback<UserlistBase>() {
                @Override
                public void onResponse(Call<UserlistBase> call, Response<UserlistBase> response) {
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
                                data = response.body().getData();

                                if (data.size() > 0) {
                                    adapter_events = new Adapter_userlist(getActivity(), data, recyclerList);
                                    recyclerList.setAdapter(adapter_events);
                                }


                                final List<UserList> data = response.body().getData();

                                customerid = new ArrayList<String>();
                                companyname = new ArrayList<String>();
                                for (int i = 0; i < data.size(); i++) {


                                    customerid.add( data.get(i).getUserId());
                                    companyname.add( data.get(i).getCompanyName());
                                }

                                datez.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, customerid));
                                datez.setThreshold(1);
                                datez.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                                        String item = (String) adapterView.getItemAtPosition(position);

                                        int pos = 0;

                                        for (int i = 0; i < customerid.size(); i++) {
                                            pos = customerid.indexOf(item);
                                            cid = customerid.get(pos).toString();


                                        }
                                        cname ="";
                                        comapny ="";
                                        getEvent();

                                    }

                                });


                                qty.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, companyname));
                                qty.setThreshold(1);
                                qty.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                                        String item = (String) adapterView.getItemAtPosition(position);

                                        int pos = 0;

                                        for (int i = 0; i < companyname.size(); i++) {
                                            pos = companyname.indexOf(item);
                                            comapny = companyname.get(pos).toString();


                                        }
                                        cname ="";
                                        cid ="";

                                        getEvent();

                                    }

                                });

                            } else {

                                CommonUtils.commonToast(getActivity(), message);

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
                public void onFailure(Call<UserlistBase> call, Throwable t) {
                    Log.e(TAG, "Unable to submit post to API.");

                    dialog.dismiss();
                }
            });

        } else {
            Log.e(TAG, "error" + "no internet");

            CommonUtils.commonToast(getActivity(), getResources().getString(R.string.no_internet_exist));

        }

    }
    public void getuserlist() {

        if (CommonUtils.isConnectingToInternet(getContext())) {
            final Dialog dialog = new Dialog(getActivity());
            dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setContentView(R.layout.dialog_wait);
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
            Call<UserlistBase> call = ApiHandler.getApiService().userlist(getParamsorder());
            call.enqueue(new Callback<UserlistBase>() {
                @Override
                public void onResponse(Call<UserlistBase> call, Response<UserlistBase> response) {
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
                                data = response.body().getData();

                                final List<UserList> data = response.body().getData();
                                Customerlist = new ArrayList<String>();

                                for (int i = 0; i < data.size(); i++) {


                                    Customerlist.add( data.get(i).getUsername());

                                }


                                name.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, Customerlist));
                                name.setThreshold(1);
                                name.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                                        String item = (String) adapterView.getItemAtPosition(position);

                                        int pos = 0;

                                        for (int i = 0; i < Customerlist.size(); i++) {
                                            pos = Customerlist.indexOf(item);
                                            cname = Customerlist.get(pos).toString();


                                        }
                                        cid ="";
                                        comapny ="";
                                        getEvent();

                                    }

                                });



                            } else {

                                CommonUtils.commonToast(getActivity(), message);

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
                public void onFailure(Call<UserlistBase> call, Throwable t) {
                    Log.e(TAG, "Unable to submit post to API.");

                    dialog.dismiss();
                }
            });

        } else {
            Log.e(TAG, "error" + "no internet");

            CommonUtils.commonToast(getActivity(), getResources().getString(R.string.no_internet_exist));

        }

    }



  /*  DatePickerDialog.OnDateSetListener ondate = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {

            datez.setText(String.valueOf(dayOfMonth) + "-" + String.valueOf(monthOfYear + 1)
                    + "-" + String.valueOf(year));

            date = datez.getText().toString().trim();

            menuName = "";
            getEvent();
        }
    };*/


 /*   public void showImageDialog(final Activity mActivity) {
        // custom dialog
        popupDialog = new Dialog(mActivity);
        popupDialog.setContentView(R.layout.dialog_status);
        popupDialog.setCancelable(true);

        RadioGroup radiogrup;
        final RadioButton rbtPending;
        final RadioButton rbtActive;
        final RadioButton rbtCompleted;
        final RadioButton rbtRejected;

        radiogrup = (RadioGroup) popupDialog.findViewById(R.id.radiogrup);
        rbtPending = (RadioButton) popupDialog.findViewById(R.id.rbt_pending);
        rbtActive = (RadioButton) popupDialog.findViewById(R.id.rbt_active);
        rbtCompleted = (RadioButton) popupDialog.findViewById(R.id.rbt_completed);
        rbtRejected = (RadioButton) popupDialog.findViewById(R.id.rbt_rejected);


        radiogrup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {

                if (checkedId == R.id.rbt_pending) {
                    rbtPending.setChecked(true);

                    popupDialog.dismiss();

                    Toast.makeText(getContext(), "choice: Pending",
                            Toast.LENGTH_SHORT).show();

                } else if (checkedId == R.id.rbt_active) {


                    rbtActive.setChecked(true);
                    popupDialog.dismiss();

                    Toast.makeText(getContext(), "choice: Active",

                            Toast.LENGTH_SHORT).show();

                } else if (checkedId == R.id.rbt_completed) {

                    rbtCompleted.setChecked(true);
                    popupDialog.dismiss();

                    Toast.makeText(getContext(), "choice: completed",

                            Toast.LENGTH_SHORT).show();

                } else if (checkedId == R.id.rbt_rejected) {
                    rbtRejected.setChecked(true);
                    popupDialog.dismiss();

                    Toast.makeText(getContext(), "choice: Rejected",

                            Toast.LENGTH_SHORT).show();

                }

            }


        });


        popupDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));

        popupDialog.show();
    }
*/

    protected Map<String, String> getParams() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("email", "w");
        params.put("password", "w");
        Log.e("PArams", params.toString());
        return params;

    }
}