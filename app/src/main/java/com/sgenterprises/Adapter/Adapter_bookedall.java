package com.sgenterprises.Adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.sgenterprises.Model.BookedBase;
import com.sgenterprises.Model.bookedlistdata;
import com.sgenterprises.R;
import com.sgenterprises.httpmanager.ApiHandler;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.com.commonlibrary.utils.CommonUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.sgenterprises.Common.BaseClass.commonSession;
import static com.sgenterprises.Fragment.BookedAllOrderListFragment.adapter_events;
import static com.sgenterprises.Fragment.BookedAllOrderListFragment.data;
import static com.sgenterprises.Fragment.BookedAllOrderListFragment.recyclerList;


public  class Adapter_bookedall extends RecyclerView.Adapter {
    private static final String TAG = "log_tag";
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    FragmentActivity mFragmentActivity;
 String   status = "";
    private boolean isLoading;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    String success="";
    private List<bookedlistdata> mlist = new ArrayList<>();
    public Dialog popupDialog;
    private ImageView txtStatus;

    public Adapter_bookedall(FragmentActivity mFragmentActivity, List<bookedlistdata> mlist, RecyclerView rv_events) {

        this.mFragmentActivity = mFragmentActivity;
        this.mlist = mlist;

        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) rv_events.getLayoutManager();

        /*rv_events.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                totalItemCount = linearLayoutManager.getItemCount();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();

                if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                    if (mOnLoadMoreListener != null) {
                        mOnLoadMoreListener.onLoadMore();
                    }
                    isLoading = true;
                }
            }
        });
*/

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {

            // create a new view
            View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.item_bookedlist, parent, false);

            return new ViewHolder(itemLayoutView);
        } /*else if (viewType == VIEW_TYPE_LOADING) {

            View itemLayoutView = LayoutInflater.from(parent.mFragmentActivity).inflate(
                    R.layout.loading_layout, parent, false);

            return new LoadingViewHolder(itemLayoutView);
        }*/
        return null;
    }



 /*   public void setLoaded() {
        isLoading = false;
    }
*/



   /* public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
        this.mOnLoadMoreListener = mOnLoadMoreListener;
    }*/

  /*  static class LoadingViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public LoadingViewHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar1);
        }
    }
*/
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ViewHolder) {

            try {
            String Datea ="";
            final ViewHolder viewHolder = (ViewHolder) holder;
            viewHolder.singleBean = mlist.get(position);
            viewHolder.pos = position;
            bookedlistdata list = mlist.get(position);
            
            String id = list.getBookingId();
            String date =list.getBookingDate();
            String qty =list.getQuantity();
            String phoneNo =list.getPhoneNo();
            String materialName =list.getMaterialName();
            String Status =list.getStatus();





            SimpleDateFormat spf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date newDate = null;
            try {
                newDate = spf.parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            SimpleDateFormat spf_date = new SimpleDateFormat("dd MMM");
            SimpleDateFormat spf_time = new SimpleDateFormat("hh:mm a");
            String newDateString = spf_date.format(newDate);
            String newTimeString = spf_time.format(newDate);

            Log.e("date", newDateString);
            if (CommonUtils.isTextAvailable(id)) ;
            {

                viewHolder.txtId.setText(id);

            }
            if (CommonUtils.isTextAvailable(newDateString)) ;
            {

              // viewHolder.txtDate.setText(Date);
                viewHolder.txtDate.setText(newDateString);
            }
            if (CommonUtils.isTextAvailable(qty)) ;
            {

                viewHolder.txtQty.setText(qty);
            }
            if (CommonUtils.isTextAvailable(materialName)) ;
            {

                viewHolder.txtPhonenp.setText(materialName);
            }
            if (CommonUtils.isTextAvailable(Status)) ;
            {
                if (Status.equalsIgnoreCase("1"))
                {
                    txtStatus.setImageResource(R.drawable.ic_blue);
                }
               else if (Status.equalsIgnoreCase("2"))
                   
                {
                    txtStatus.setImageResource(R.drawable.ic_yellow);
                }
               else if (Status.equalsIgnoreCase("3"))
                {
                    txtStatus.setImageResource(R.drawable.ic_green);
                }
                else if (Status.equalsIgnoreCase("4"))
                {
                    txtStatus.setImageResource(R.drawable.ic_red);
                }
               
            }



            } catch (Exception e) {
                Log.e(TAG, "error " + e.getMessage());
                e.printStackTrace();
            }
        } /*else if (holder instanceof LoadingViewHolder) {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }*/
    }



    @Override
    public int getItemViewType(int position) {
        return mlist.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return mlist == null ? 0 : mlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public bookedlistdata singleBean;
        int pos;


        private TextView txtId;
        private TextView txtPhonenp;
        private TextView txtDate;
        private TextView txtQty;


        /**
         * Find the Views in the layout<br />
         * <br />
         * Auto-created on 2018-04-15 22:34:57 by Android Layout Finder
         * (http://www.buzzingandroid.com/tools/android-layout-finder)
         */
        private void findViews() {
           
        }



        public ViewHolder(View v) {
            super(v);

            txtId = (TextView)v.findViewById( R.id.txt_id );
            txtPhonenp = (TextView)v.findViewById( R.id.txt_phonenp );
            txtDate = (TextView)v.findViewById( R.id.txt_date );
            txtQty = (TextView)v.findViewById( R.id.txt_qty );
            txtStatus = (ImageView)v.findViewById( R.id.txt_status );

            txtStatus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showImageDialog(mFragmentActivity,mlist.get(pos).getBookingId(),pos);


                }
            });


        }

    }

    public void showImageDialog(final Activity mActivity, final String id, final int pos) {
        // custom dialog
        popupDialog = new Dialog(mActivity);
        popupDialog.setContentView(R.layout.dialog_status);
        popupDialog.setCancelable(true);

        RadioGroup radiogrup;
        final RadioButton rbtPending;
        final RadioButton rbtActive;
        final RadioButton rbtCompleted;
        final RadioButton rbtRejected;

        radiogrup = (RadioGroup)popupDialog.findViewById( R.id.radiogrup );
        rbtPending = (RadioButton)popupDialog.findViewById( R.id.rbt_pending );
        rbtActive = (RadioButton)popupDialog.findViewById( R.id.rbt_active );
        rbtCompleted = (RadioButton)popupDialog.findViewById( R.id.rbt_completed );
        rbtRejected = (RadioButton)popupDialog.findViewById( R.id.rbt_rejected );


        radiogrup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {

                if(checkedId == R.id.rbt_pending) {
                    rbtPending.setChecked(true);

                    popupDialog.dismiss();
                    status ="1";
                    getEvent(status,id ,pos);




                } else if(checkedId == R.id.rbt_active) {

                    rbtActive.setChecked(true);
                    popupDialog.dismiss();
                   
                    status ="2";
                    getEvent(status,id ,pos);
                } else if(checkedId == R.id.rbt_completed){

                    rbtCompleted.setChecked(true);
                    popupDialog.dismiss();

                    status ="3";
                    getEvent(status,id ,pos);

                }else if (checkedId == R.id.rbt_rejected){
                    rbtRejected.setChecked(true);
                    popupDialog.dismiss();
                  
                    status ="4";
                    getEvent(status,id ,pos);

                }

            }



        });


        popupDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));

        popupDialog.show();
    }

    protected Map<String, String> getParamsorder(String status,String id) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("id", id );
        params.put("status", status);
        Log.e("PArams", params.toString());
        return params;

    }
    public void getEvent(final String status, String id, final int pos) {

        if (CommonUtils.isConnectingToInternet(mFragmentActivity)) {
            final Dialog dialog = new android.app.Dialog(mFragmentActivity);
            dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            dialog.setContentView(R.layout.dialog_wait);
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
            Call<BookedBase> call = ApiHandler.getApiService().order_status(getParamsorder(status,id));
            call.enqueue(new Callback<BookedBase>() {
                @Override
                public void onResponse(Call<BookedBase> call, Response<BookedBase> response) {
                    try
                    {
                        Log.e(TAG, " Full json gson => " + new Gson().toJson(response));
                        JSONObject jsonObj = null;
                        jsonObj = new JSONObject(new Gson().toJson(response).toString());
                        Log.e(TAG, " responce => " + jsonObj.getJSONObject("body").toString());
                        dialog.dismiss();
                        if (response.isSuccessful()) {

                           String success = response.body().getSuccess();
                            String message = response.body().getMsg();

                            if ( success.equalsIgnoreCase("true")) {


                                CommonUtils.commonToast(mFragmentActivity,message);


                                getEventa();

                            } else {
                                CommonUtils.commonToast(mFragmentActivity,message);

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
                public void onFailure(Call<BookedBase> call, Throwable t) {
                    Log.e(TAG, "Unable to submit post to API.");



                    dialog.dismiss();
                }
            });

        } else {
            Log.e(TAG, "error" + "no internet");


        }

    }

    public void getEventa() {

        if (CommonUtils.isConnectingToInternet(mFragmentActivity)) {
            final Dialog dialog = new Dialog(mFragmentActivity);
            dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setContentView(R.layout.dialog_wait);
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
            Call<BookedBase> call = ApiHandler.getApiService().booked_orders(getParamsorder("",""));
            call.enqueue(new Callback<BookedBase>() {
                @Override
                public void onResponse(Call<BookedBase> call, Response<BookedBase> response) {
                    try
                    {
                        Log.e(TAG, " Full json gson => " + new Gson().toJson(response));
                        JSONObject jsonObj = null;
                        jsonObj = new JSONObject(new Gson().toJson(response).toString());
                        Log.e(TAG, " responce => " + jsonObj.getJSONObject("body").toString());
                        dialog.dismiss();
                        if (response.isSuccessful()) {

                            String success = response.body().getSuccess();
                            String message = response.body().getMsg();

                            if ( success.equalsIgnoreCase("true")) {
                                data = response.body().getData();

                                if (data.size() > 0) {
                                    recyclerList.setVisibility(View.VISIBLE);
                                    adapter_events = new Adapter_bookedall(mFragmentActivity, data,recyclerList);
                                    recyclerList.setAdapter(adapter_events);
                                }
                                else
                                {
                                    recyclerList.setVisibility(View.GONE);
                                }


                            } else {
                                CommonUtils.commonToast(mFragmentActivity,message);
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
                public void onFailure(Call<BookedBase> call, Throwable t) {
                    Log.e(TAG, "Unable to submit post to API.");


                    dialog.dismiss();
                }
            });

        } else {
            Log.e(TAG, "error" + "no internet");


        }

    }


}
