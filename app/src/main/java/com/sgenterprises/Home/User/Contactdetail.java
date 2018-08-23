package com.sgenterprises.Home.User;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.sgenterprises.Common.BaseClass;
import com.sgenterprises.Model.Contactlist;
import com.sgenterprises.Model.UserDatum;
import com.sgenterprises.R;

import app.com.commonlibrary.utils.CommonUtils;

public class Contactdetail extends BaseClass {
    Bundle b ;
    Contactlist userDatum ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contatdetail);
        findViews();
        b =getIntent().getExtras();
        if (b!= null){

            userDatum = b.getParcelable("list");

            setdata();
        }



    }

    private void setdata() {

        try {
            if (CommonUtils.isTextAvailable(userDatum.getUsername())) {

                password.setText(userDatum.getUsername());


            }
            if (CommonUtils.isTextAvailable(userDatum.getEmail())) {
                materialname.setText(userDatum.getEmail());

            }


            String type = userDatum.getType();
            if (type.equalsIgnoreCase("2"))
            {
                if (CommonUtils.isTextAvailable(userDatum.getMaterialName())) {
                    location.setText(userDatum.getMaterialName());

                }
            }
            else
            {
                material.setVisibility(View.GONE);
                location.setVisibility(View.GONE);

            }

            if (CommonUtils.isTextAvailable(userDatum.getMessage())) {
                breadth.setText(userDatum.getMessage());

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
    private TextView material;
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
        breadth = (TextView)findViewById( R.id.breadth );
        material = (TextView)findViewById( R.id.material );

    }


}
