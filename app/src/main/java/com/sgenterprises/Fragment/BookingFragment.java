package com.sgenterprises.Fragment;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Dialog;

import com.google.gson.Gson;
import com.sgenterprises.Home.RegistrationActivity;
import com.sgenterprises.Model.CalculateBase;
import com.sgenterprises.Model.Material;
import com.sgenterprises.Model.MaterialBase;
import com.sgenterprises.Model.Msg;
import com.sgenterprises.R;
import com.sgenterprises.httpmanager.ApiHandler;

import org.json.JSONObject;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.view.View;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import app.com.commonlibrary.utils.CommonUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.sgenterprises.Common.BaseClass.commonSession;

public class BookingFragment extends Fragment {

    private View parentView;
    private ListView listView;
    ArrayList<String> materiallist;
    ArrayList<String> materialid;
    private String menuId;
    private String menuName;
    Spinner spMaterial;

    int mYear, mMonth, mDay;
    static final int DATE_DIALOG_ID = 0;
    static String date ="";
    Date date1;
    ImageView fpickDateAt2Ar;
    static TextView datedisplayOrder;

    EditText edtmobile;
    EditText edtqty;

    String mobilea, datee, qty;


    Button btn_login;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.activity_book_orders, container, false);
        datedisplayOrder = (TextView) parentView.findViewById(R.id.datedisplay_order);
        fpickDateAt2Ar = (ImageView) parentView.findViewById(R.id.datepicker_order);
        spMaterial = (Spinner) parentView.findViewById(R.id.sp_material);
        edtmobile = (EditText) parentView.findViewById(R.id.mobile);
        edtqty = (EditText) parentView.findViewById(R.id.edt_qty);
        btn_login = (Button) parentView.findViewById(R.id.btn_login);
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        updateDisplay();

        fpickDateAt2Ar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment dFragment = new DatePickerFragment();

                // Show the date picker dialog fragment
                dFragment.show(getFragmentManager(), "Date Picker");

              //  showDatePicker();

            }
        });



        getmaterial();

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mobilea = edtmobile.getText().toString().trim();
                datee = datedisplayOrder.getText().toString().trim();
                qty = edtqty.getText().toString().trim();


                if (!CommonUtils.isTextAvailable(mobilea)) {
                    CommonUtils.commonToast(getActivity(), "Enter mobile number");

                } else if (mobilea.length() != 10) {
                    CommonUtils.commonToast(getActivity(), getResources().getString(R.string.entervmobno));
                }else if (!CommonUtils.isTextAvailable(datee)) {
                    CommonUtils.commonToast(getActivity(), "Select Date");

                } else if (!CommonUtils.isTextAvailable(menuId)) {
                    CommonUtils.commonToast(getActivity(), "Select material");

                } else if (!CommonUtils.isTextAvailable(qty)) {
                    CommonUtils.commonToast(getActivity(), "Enter mobile number");

                } else if (qty.startsWith("0")) {
                    CommonUtils.commonToast(getActivity(), "Qty should not start with zero");

                } else {

                    sendorder();
                }
            }
        });
        return parentView;

    }



    private void updateDisplay() {
        datedisplayOrder.setText(
                new StringBuilder()
                        .append(mDay).append("-")
                        // Month is 0 based so add 1
                        .append(mMonth + 1).append("-")
                        .append(mYear));
        date = datedisplayOrder.getText().toString();

        date = datedisplayOrder.getText().toString().trim();
        SimpleDateFormat spf = new SimpleDateFormat("dd-MM-yyyy");
        Date newDate = null;
        try {
            newDate = spf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat spf_date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        SimpleDateFormat spf_time = new SimpleDateFormat("hh:mm a");
        String newDateString = spf_date.format(newDate);

        date = newDateString;
        Log.e("Date", "Date is = " + date);

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
                                    materialid.add(data.get(i).getMaterialId());
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


    protected Map<String, String> getParamsorder() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("phone_no", mobilea);
        params.put("material_id", menuId);
        params.put("booking_date", date);
        params.put("quantity", qty);

        String userId = commonSession.getLoggedUserID();

        if (CommonUtils.isTextAvailable(userId) ) {

            params.put("user_id", userId);
        }
        else

        {
            params.put("user_id", "0");
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


            Call<MaterialBase> call = ApiHandler.getApiService().book_order(getParamsorder());
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

                                edtmobile.setText("");
                                edtqty.setText("");


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


    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener{

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState){
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            /*
                We should use THEME_HOLO_LIGHT, THEME_HOLO_DARK or THEME_TRADITIONAL only.

                The THEME_DEVICE_DEFAULT_LIGHT and THEME_DEVICE_DEFAULT_DARK does not work
                perfectly. This two theme set disable color of disabled dates but users can
                select the disabled dates also.

                Other three themes act perfectly after defined enabled date range of date picker.
                Those theme completely hide the disable dates from date picker object.
             */
            DatePickerDialog dpd = new DatePickerDialog(getActivity(),
                    AlertDialog.THEME_HOLO_LIGHT,this,year,month,day);

            /*
                add(int field, int value)
                    Adds the given amount to a Calendar field.
             */
            // Add 3 days to Calendar
            calendar.add(Calendar.DATE, 0);

            /*
                getTimeInMillis()
                    Returns the time represented by this Calendar,
                    recomputing the time from its fields if necessary.

                getDatePicker()
                Gets the DatePicker contained in this dialog.

                setMinDate(long minDate)
                    Sets the minimal date supported by this NumberPicker
                    in milliseconds since January 1, 1970 00:00:00 in getDefault() time zone.

                setMaxDate(long maxDate)
                    Sets the maximal date supported by this DatePicker in milliseconds
                    since January 1, 1970 00:00:00 in getDefault() time zone.
             */

            // Set the Calendar new date as maximum date of date picker

            // Subtract 6 days from Calendar updated date
            calendar.add(Calendar.DATE, 0);

            // Set the Calendar new date as minimum date of date picker
            dpd.getDatePicker().setMinDate(calendar.getTimeInMillis());

            // So, now date picker selectable date range is 7 days only

            // Return the DatePickerDialog
            return  dpd;
        }

        public void onDateSet(DatePicker view, int year, int month, int day){
            // Do something with the chosen date

            try {


                // Create a Date variable/object with user chosen date
                Calendar cal = Calendar.getInstance();
                cal.setTimeInMillis(0);
                cal.set(year, month, day, 0, 0, 0);
                Date chosenDate = cal.getTime();

                // Format the date using style and locale
                DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.US);
                String formattedDate = df.format(chosenDate);
                Log.e("Date", "Date is = " + formattedDate);
                datedisplayOrder.setText(formattedDate);
                date = datedisplayOrder.getText().toString().trim();

                SimpleDateFormat spf = new SimpleDateFormat("MMM dd,yyyy");
                Date newDate = null;
                try {
                    newDate = spf.parse(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                SimpleDateFormat spf_date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                SimpleDateFormat spf_time = new SimpleDateFormat("hh:mm a");
                String newDateString = spf_date.format(newDate);

                date = newDateString;
                Log.e("Date", "Date is = " + date);
            }
            catch (Exception e)
            {
                Log.e("Date", String.valueOf(e));
            }
            // Display the chosen date to app interface

        }
    }



}




