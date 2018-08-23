package com.sgenterprises.Home.User;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.sgenterprises.Common.BaseClass;
import com.sgenterprises.Model.UserDatum;
import com.sgenterprises.R;

import app.com.commonlibrary.utils.CommonUtils;

public class OrderListdetail extends BaseClass {
    Bundle b ;
    UserDatum  userDatum ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookedone);
        findViews();
        b =getIntent().getExtras();
        if (b!= null){

            userDatum = b.getParcelable("list");

            setdata();
        }



    }

    private void setdata() {

        try {
            if (CommonUtils.isTextAvailable(userDatum.getFirstName())) {

                password.setText(userDatum.getFirstName());


            }
            if (CommonUtils.isTextAvailable(userDatum.getMaterialName())) {
                materialname.setText(userDatum.getMaterialName());

            }
            if (CommonUtils.isTextAvailable(userDatum.getLocation())) {
                location.setText(userDatum.getLocation());

            }
            if (CommonUtils.isTextAvailable(userDatum.getLength())) {
                lenght.setText(userDatum.getLength());

            }
            if (CommonUtils.isTextAvailable(userDatum.getBreadth())) {
                breadth.setText(userDatum.getBreadth());


            }
            if (CommonUtils.isTextAvailable(userDatum.getHeight())) {
                edtHeight.setText(userDatum.getHeight());


            }
            if (CommonUtils.isTextAvailable(userDatum.getPrice())) {
                edtPrice.setText(userDatum.getPrice());


            }
            if (CommonUtils.isTextAvailable(userDatum.getPaymentGiven())) {
                edtPaygiven.setText(userDatum.getPaymentGiven());


            }
            if (CommonUtils.isTextAvailable(userDatum.getPaymentDue())) {
                edtPaydue.setText(userDatum.getPaymentDue());
            }


        }
        catch (Exception e)
        {
            Log.e("e", String.valueOf(e));
        }




    }


    private TextView password;
    private TextView materialname;
    private TextView location;
    private TextView lenght;
    private TextView breadth;
    private TextView edtHeight;
    private TextView edtPrice;
    private TextView edtPaygiven;
    private TextView edtPaydue;

    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2018-04-22 22:59:58 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews() {
        password = (TextView)findViewById( R.id.password );
        materialname = (TextView)findViewById( R.id.materialname );
        location = (TextView)findViewById( R.id.location );
        lenght = (TextView)findViewById( R.id.lenght );
        breadth = (TextView)findViewById( R.id.breadth );
        edtHeight = (TextView)findViewById( R.id.edt_height );
        edtPrice = (TextView)findViewById( R.id.edt_price );
        edtPaygiven = (TextView)findViewById( R.id.edt_paygiven );
        edtPaydue = (TextView)findViewById( R.id.edt_paydue );
    }


}
