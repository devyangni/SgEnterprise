package com.sgenterprises.Fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.sgenterprises.Adapter.Adapter_orderall;
import com.sgenterprises.Model.UserList;
import com.sgenterprises.Model.UserlistBase;
import com.sgenterprises.Model.Datum;
import com.sgenterprises.Model.Material;
import com.sgenterprises.Model.MaterialBase;
import com.sgenterprises.Model.UserDatum;
import com.sgenterprises.Model.UserlistBase;
import com.sgenterprises.Model.UsersBase;
import com.sgenterprises.R;
import com.sgenterprises.ServiceResideMenu.ResideMenu;
import com.sgenterprises.httpmanager.ApiHandler;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.com.commonlibrary.utils.CommonUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderListAllFragment extends Fragment {
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
    AutoCompleteTextView datepicker_order;
    private ImageView datepickerOrder;

    int mYear, mMonth, mDay;
    private String date = "";
    public static Adapter_orderall adapter_events;
    String TAG = OrderListAllFragment.class.getName();
    public static   List<UserDatum> data = new ArrayList<>();
    ArrayList<String> materiallist;
    ArrayList<String> materialid;
    private ImageView refresh;

    ArrayList<String> Customerlist;
    ArrayList<String> customerid;


    ArrayList<String> Customerlista;
    ArrayList<String> customerida;

    String cname;
    String cid;
    ArrayList<String> clist;

    private String menuId;
    private String menuName = "";

  private String customer_id;
    private String customer_name = "";

    private TextView datez;
    private AutoCompleteTextView name;
    private EditText qty;
    private EditText price;
    private TextView status;
    public static  RecyclerView recyclerList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        parentView = inflater.inflate(R.layout.activity_order_listing, container, false);

        data = new ArrayList<>();
        findViews();


        getcustomerid();
        getmaterial();
        getEvent();

        return parentView;

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


            Call<UsersBase> call = ApiHandler.getApiService().get_order_list(getParamsorder());
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
                                CommonUtils.commonToast(getActivity(), message);

                                final List<UserDatum> data = response.body().getUserData();
                                Customerlist = new ArrayList<String>();
                                customerid = new ArrayList<String>();
                                for (int i = 0; i < data.size(); i++) {

                                    Customerlist.add(data.get(i).getFirstName());

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

                                            customer_name = Customerlist.get(pos).toString();


                                        }
                                        menuName ="";
                                        date = "";
                                        getEvent();

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
                public void onFailure(Call<UsersBase> call, Throwable t) {

                    dialog.dismiss();
                }
            });

        } else {

            CommonUtils.commonToast(getActivity(), getResources().getString(R.string.no_internet_exist));

        }

    }
    private void findViews() {
        datez = (TextView) parentView.findViewById(R.id.date);
        name = (AutoCompleteTextView) parentView.findViewById(R.id.name);
        datepicker_order = (AutoCompleteTextView) parentView.findViewById(R.id.qty);

        refresh = (ImageView)parentView.findViewById( R.id.refresh );
        recyclerList = (RecyclerView) parentView.findViewById(R.id.recycler_list);
        recyclerList.setHasFixedSize(true);
        recyclerList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerList.setNestedScrollingEnabled(false);
        datez.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                showDatePicker();

            }
        });
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customer_name ="";
                menuName ="";
                date="";
                name.setText("");
                datepicker_order.setText("");
                datez.setText("");
                getEvent();
            }
        });

        name.setOnEditorActionListener(new AutoCompleteTextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    customer_name = name.getText().toString().trim();
                    cid ="";
                    menuName ="";
                    date="";
                    datepicker_order.setText("");
                    datez.setText("");
                    getEvent();
                    return true;
                }
                return false;
            }
        });



    }

    private void showDatePicker() {
        DatePickerFragment date = new DatePickerFragment();
        /**
         * Set Up Current Date Into dialog
         */
        Calendar calender = Calendar.getInstance();
        Bundle args = new Bundle();
        args.putInt("year", calender.get(Calendar.YEAR));
        args.putInt("month", calender.get(Calendar.MONTH));
        args.putInt("day", calender.get(Calendar.DAY_OF_MONTH));
        date.setArguments(args);
        /**
         * Set Call back to capture selected date
         */
        date.setCallBack(ondate);
        date.show(getFragmentManager(), "Date Picker");


    }

    protected Map<String, String> getParamsorder() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("id", "");
        params.put("date", date);
        params.put("material", menuName);
        params.put("customer_name", customer_name);

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
            Call<UsersBase> call = ApiHandler.getApiService().get_order_list(getParamsorder());
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
                                    adapter_events = new Adapter_orderall(getActivity(), data, recyclerList);
                                    recyclerList.setAdapter(adapter_events);
                                }
                                else
                                {
                                    recyclerList.setVisibility(View.GONE);
                                }

                            } else {

                                CommonUtils.commonToast(getActivity(),message);
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

            CommonUtils.commonToast(getActivity(), getResources().getString(R.string.no_internet_exist));

        }

    }


    DatePickerDialog.OnDateSetListener ondate = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {

            datez.setText(String.valueOf(dayOfMonth) + "-" + String.valueOf(monthOfYear + 1)
                    + "-" + String.valueOf(year));
            date = datez.getText().toString().trim();

            SimpleDateFormat spf = new SimpleDateFormat("dd-MM-yyyy");
            Date newDate = null;
            try {
                newDate = spf.parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            SimpleDateFormat spf_date = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat spf_time = new SimpleDateFormat("hh:mm a");
            String newDateString = spf_date.format(newDate);

            date = newDateString;
            Log.e("date", newDateString);
            customer_name = "";
            menuName = "";
            getEvent();
        }
    };


    public void showImageDialog(final Activity mActivity) {
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


    protected Map<String, String> getParams() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("email", "w");
        params.put("password", "w");
        Log.e("PArams", params.toString());
        return params;

    }

    public void getmaterial() {

        if (CommonUtils.isConnectingToInternet(getActivity())) {
            final Dialog dialog = new Dialog(getContext());
            dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
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
                                    materialid.add(data.get(i).getMaterialId());
                                }


                                datepicker_order.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, materiallist));
                                datepicker_order.setThreshold(1);

                                datepicker_order.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                                        String item = (String) adapterView.getItemAtPosition(position);

                                        int pos = 0;

                                        for (int i = 0; i < materialid.size(); i++) {
                                            pos = materiallist.indexOf(item);
                                            menuId = materialid.get(pos).toString();
                                            menuName = materiallist.get(pos).toString();


                                        }
                                        customer_name ="";
                                        date = "";
                                        getEvent();

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
}