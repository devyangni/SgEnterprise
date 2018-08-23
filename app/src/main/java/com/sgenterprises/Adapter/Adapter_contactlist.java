package com.sgenterprises.Adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
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
import com.sgenterprises.Home.User.Contactdetail;
import com.sgenterprises.Home.User.OrderListdetail;
import com.sgenterprises.Model.BookedBase;
import com.sgenterprises.Model.Contactlist;
import com.sgenterprises.Model.ContactlistBase;
import com.sgenterprises.Model.UserDatum;
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

import static com.sgenterprises.Fragment.ContactusListAllFragment.adapter_events;
import static com.sgenterprises.Fragment.ContactusListAllFragment.data;
import static com.sgenterprises.Fragment.ContactusListAllFragment.recyclerList;


public  class Adapter_contactlist extends RecyclerView.Adapter {
    private static final String TAG = "log_tag";
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    FragmentActivity mFragmentActivity;
    String   status = "";
    private boolean isLoading;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    public Dialog popupDialog;

    private List<Contactlist> mlist = new ArrayList<>();

    public Adapter_contactlist(FragmentActivity mFragmentActivity, List<Contactlist> mlist, RecyclerView rv_events) {

        this.mFragmentActivity = mFragmentActivity;
        this.mlist = mlist;

        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) rv_events.getLayoutManager();



    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {

            // create a new view
            View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.item_contactorderlist, parent, false);

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
                Contactlist list = mlist.get(position);
            
            String id = list.getId();
            String  date=list.getUsername();
            String  customername =list.getEmail();
            String materialName =list.getMaterialName();
            String type =list.getType();
            String Status =list.getStatus();



            if (CommonUtils.isTextAvailable(id)) ;
            {

                viewHolder.txtId.setText(id);

            }
                if (CommonUtils.isTextAvailable(date)) ;
                {

                    viewHolder.txtPhonenp.setText(date);
                }
            if (CommonUtils.isTextAvailable(customername)) ;
            {

              // viewHolder.txtDate.setText(Date);
                viewHolder.txtDate.setText(customername);
            }
            if (CommonUtils.isTextAvailable(materialName)) ;
            {

                viewHolder.txtQty.setText(materialName);
            }
                if (CommonUtils.isTextAvailable(type)) ;
                {

                    viewHolder.txt_type.setText(type);
                }
                if (CommonUtils.isTextAvailable(Status)) ;
                {
                    if (Status.equalsIgnoreCase("1"))
                    {
                        viewHolder.txtStatus.setImageResource(R.drawable.ic_blue);
                    }
                    else if (Status.equalsIgnoreCase("2"))

                    {
                        viewHolder.txtStatus.setImageResource(R.drawable.ic_yellow);
                    }
                    else if (Status.equalsIgnoreCase("3"))
                    {
                        viewHolder.txtStatus.setImageResource(R.drawable.ic_green);
                    }
                    else if (Status.equalsIgnoreCase("4"))
                    {
                        viewHolder.txtStatus.setImageResource(R.drawable.ic_red);
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

    public void delete(int position) { //removes the row
        Log.e("Position : ", "" + position);
        mlist.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mlist.size());

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
        public Contactlist singleBean;
        int pos;


        private TextView txtId;
        private TextView txtPhonenp;
        private TextView txtDate;
        private TextView txtQty;
        private ImageView txtStatus;
        private TextView txt_type;



        public ViewHolder(View v) {
            super(v);

            txtId = (TextView)v.findViewById( R.id.txt_id );
            txtPhonenp = (TextView)v.findViewById( R.id.txt_phonenp );
            txtDate = (TextView)v.findViewById( R.id.txt_date );
            txtQty = (TextView)v.findViewById( R.id.txt_qty );
            txt_type = (TextView)v.findViewById( R.id.txt_type );
            txtStatus = (ImageView)v.findViewById( R.id.txt_status );

            txtStatus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   showImageDialog(mFragmentActivity,mlist.get(pos).getId(),pos);

                }
            });

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent i = new Intent(mFragmentActivity,Contactdetail.class);
                    i.putExtra("list",mlist.get(pos));
                    mFragmentActivity.startActivity(i);

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

    protected Map<String, String> getParamsorder(String status, String id) {
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
            Call<BookedBase> call = ApiHandler.getApiService().contact_status(getParamsorder(status,id));
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
    protected Map<String, String> getParamsorder() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("id", "");
        params.put("date", "");
        params.put("material", "");

        Log.e("PArams", params.toString());
        return params;

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
                                    adapter_events = new Adapter_contactlist(mFragmentActivity, data, recyclerList);
                                    recyclerList.setAdapter(adapter_events);
                                }

                            } else {

                                CommonUtils.commonToast(mFragmentActivity, message);

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

            CommonUtils.commonToast(mFragmentActivity, mFragmentActivity.getResources().getString(R.string.no_internet_exist));

        }

    }


 
}
