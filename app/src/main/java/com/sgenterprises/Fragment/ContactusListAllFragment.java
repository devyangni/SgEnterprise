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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
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
import com.sgenterprises.Adapter.Adapter_contactlist;
import com.sgenterprises.Adapter.Adapter_orderall;
import com.sgenterprises.Model.Contactlist;
import com.sgenterprises.Model.Material;
import com.sgenterprises.Model.MaterialBase;
import com.sgenterprises.Model.UserDatum;
import com.sgenterprises.Model.ContactlistBase;
import com.sgenterprises.R;
import com.sgenterprises.ServiceResideMenu.ResideMenu;
import com.sgenterprises.httpmanager.ApiHandler;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.com.commonlibrary.utils.CommonUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactusListAllFragment extends Fragment {
    Animation animSlideUp;

    GridView gridView;
    int screen_height = 0, screen_width = 0;
    public Dialog popupDialog;
    private View parentView;
    private ResideMenu resideMenu;
    private Toolbar toolbar;
    public  static    RecyclerView RecycleVmavaliableservice;

    private TextView txtDateOrder;
    private TextView datedisplayOrder;
    private TextView filter;
    AutoCompleteTextView datepicker_order;
    private ImageView datepickerOrder;

    int mYear, mMonth, mDay;
    private String date = "";
    public  static Adapter_contactlist adapter_events;
    String TAG = ContactusListAllFragment.class.getName();
    public static List<Contactlist> data = new ArrayList<>();
    ArrayList<String> materiallist;
    ArrayList<String> materialid;
    private String menuId;
    private String menuName = "";

    private TextView datez;
    private AutoCompleteTextView name;
    private EditText qty;
    private EditText price;
    private TextView status;
    public static RecyclerView recyclerList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        parentView = inflater.inflate(R.layout.activity_contacus_listing, container, false);

        //data = new ArrayList<>();
        //
        findViews();
        //getmaterial();
        getEvent();


        return parentView;

    }


    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2018-04-16 01:29:47 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews() {
        /*datez = (TextView) parentView.findViewById(R.id.date);
        name = (AutoCompleteTextView) parentView.findViewById(R.id.name);
        qty = (EditText) parentView.findViewById(R.id.qty);
        price = (EditText) parentView.findViewById(R.id.price);
        status = (TextView) parentView.findViewById(R.id.status);*/
        recyclerList = (RecyclerView) parentView.findViewById(R.id.recycler_list);
        recyclerList.setHasFixedSize(true);
        recyclerList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerList.setNestedScrollingEnabled(false);


    }


    protected Map<String, String> getParamsorder() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("id", "");
        params.put("date", "");
        params.put("material", "");

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
            Call<ContactlistBase> call = ApiHandler.getApiService().contact_list(getParamsorder());
            call.enqueue(new Callback<ContactlistBase>() {
                @Override
                public void onResponse(Call<ContactlistBase> call, Response<ContactlistBase> response) {
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
                                    adapter_events = new Adapter_contactlist(getActivity(), data, recyclerList);
                                    recyclerList.setAdapter(adapter_events);
                                }

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
                public void onFailure(Call<ContactlistBase> call, Throwable t) {
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