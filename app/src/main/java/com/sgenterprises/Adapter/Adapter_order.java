package com.sgenterprises.Adapter;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sgenterprises.Home.User.OrderListdetail;
import com.sgenterprises.Model.UserDatum;
import com.sgenterprises.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import app.com.commonlibrary.utils.CommonUtils;


public  class Adapter_order extends RecyclerView.Adapter {
    private static final String TAG = "log_tag";
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    FragmentActivity mFragmentActivity;

    private boolean isLoading;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;

    private List<UserDatum> mlist = new ArrayList<>();



    public Adapter_order(FragmentActivity mFragmentActivity, List<UserDatum> mlist, RecyclerView rv_events) {

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
                    R.layout.item_bookedorderlist, parent, false);

            return new ViewHolder(itemLayoutView);
        } /*else if (viewType == VIEW_TYPE_LOADING) {

            View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
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
            UserDatum list = mlist.get(position);
            
            String id = list.getOrderId();
            String  date=list.getCreatedAt();
            String  customername =list.getMaterialName();
            String paymentdue =list.getPaymentDue();




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

                    viewHolder.txtPhonenp.setText(newDateString);
                }
            if (CommonUtils.isTextAvailable(customername)) ;
            {

              // viewHolder.txtDate.setText(Date);
                viewHolder.txtDate.setText(customername);
            }

                viewHolder.txtQty.setVisibility(View.GONE);


            if (CommonUtils.isTextAvailable(paymentdue)) ;
            {
                viewHolder.txtStatus.setText(paymentdue);
               
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
        public UserDatum singleBean;
        int pos;


        private TextView txtId;
        private TextView txtPhonenp;
        private TextView txtDate;
        private TextView txtQty;
        private TextView txtStatus;



        public ViewHolder(View v) {
            super(v);

            txtId = (TextView)v.findViewById( R.id.txt_id );
            txtPhonenp = (TextView)v.findViewById( R.id.txt_phonenp );
            txtDate = (TextView)v.findViewById( R.id.txt_date );
            txtQty = (TextView)v.findViewById( R.id.txt_qty );
            txtStatus = (TextView)v.findViewById( R.id.txt_status );

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    Intent i = new Intent(mFragmentActivity,OrderListdetail.class);
                    i.putExtra("list",mlist.get(pos));
                    mFragmentActivity.startActivity(i);

                }
            });


        }

    }


 
}
