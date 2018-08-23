package com.sgenterprises.Fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.sgenterprises.Common.CommonSession;
import com.sgenterprises.Home.LoginActivity;
import com.sgenterprises.R;
import com.sgenterprises.ServiceResideMenu.ResideMenu;
import com.sgenterprises.ServiceResideMenu.ResideMenuItem;
import com.sgenterprises.SplashActivity;

import java.util.ArrayList;

import app.com.commonlibrary.utils.CommonUtils;

import static app.com.commonlibrary.utils.CommonUtils.hideSoftKeyboard;

public class AdminmenuActivity extends FragmentActivity implements View.OnClickListener {
    private boolean doubleBackToExitPressedOnce = false;
    TextView menuheader;
    private ResideMenu resideMenu;
    private AdminmenuActivity mContext;
    private ResideMenuItem itemHome;
    private ResideMenuItem itemloginreg;
    private ResideMenuItem itemorderbooking;
    private ResideMenuItem itemorderlisting;
    private ResideMenuItem itemcontactlisting;
    private ResideMenuItem itemuserlisting;
    private ResideMenuItem itemgetaqoute;
    private ResideMenuItem itemcontactus;
    private ResideMenuItem itemcalculategst;
    private ResideMenuItem itemaddorderdetail;
    private ResideMenuItem itembookedorderlisting;
    private ResideMenuItem itemLogout;
    CommonSession commonSession;
    public Dialog popupDialog;
    public ArrayList<String> filterAddressListDisallow;


    public Button txtDialogReport;
    public Button txtDialogCreateMovment;

    private ResideMenu.OnMenuListener menuListener = new ResideMenu.OnMenuListener() {
        @Override
        public void openMenu() {
            // Toast.makeText(mContext, "Menu is opened!", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void closeMenu() {
            //  Toast.makeText(mContext, "Menu is closed!", Toast.LENGTH_SHORT).show();
        }
    };

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mContext = this;
        setUpMenu();
        commonSession = new CommonSession(AdminmenuActivity.this);
        try {
            String userId = commonSession.getLoggedUserID();

            if (CommonUtils.isTextAvailable(userId)) {

                if (savedInstanceState == null)
                    menuheader.setText(getResources().getString(R.string.OrdeDetail));
                    changeFragment(new OrderDetailFragment());
                itemloginreg.setVisibility(View.GONE);
                itemLogout.setVisibility(View.VISIBLE);
            } else {
                if (savedInstanceState == null)
                    menuheader.setText(getResources().getString(R.string.OrdeDetail));
                    changeFragment(new OrderDetailFragment());
                itemloginreg.setVisibility(View.VISIBLE);
                itemLogout.setVisibility(View.GONE);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void setUpMenu() {

        // attach to current activity;
        resideMenu = new ResideMenu(this);
        resideMenu.setUse3D(true);
        resideMenu.setBackground(R.drawable.gradiant);
        resideMenu.attachToActivity(this);
        resideMenu.setMenuListener(menuListener);
        //valid scale factor is between 0.0f and 1.0f. leftmenu'width is 150dip.
        resideMenu.setScaleValue(0.6f);

        // create menu items;
        menuheader = (TextView) findViewById(R.id.menuheader);

        itemHome     = new ResideMenuItem(this, R.drawable.home, mContext.getResources().getString(R.string.updateprofile));
        itemloginreg = new ResideMenuItem(this, R.drawable.login, mContext.getResources().getString(R.string.login));
        itemorderlisting = new ResideMenuItem(this, R.drawable.list, mContext.getResources().getString(R.string.OrderListingall));
        itemcontactlisting = new ResideMenuItem(this, R.drawable.list, mContext.getResources().getString(R.string.contactListing));
        itemuserlisting = new ResideMenuItem(this, R.drawable.list, mContext.getResources().getString(R.string.userlist));
        itemaddorderdetail = new ResideMenuItem(this, R.drawable.order, mContext.getResources().getString(R.string.Addorderdetail));
        itemorderbooking = new ResideMenuItem(this, R.drawable.order, mContext.getResources().getString(R.string.Profile));
        itembookedorderlisting = new ResideMenuItem(this, R.drawable.list, mContext.getResources().getString(R.string.BookedOrderss));
        itemgetaqoute = new ResideMenuItem(this, R.drawable.ic_chat_black_24dp, mContext.getResources().getString(R.string.Getaquote));
        itemcontactus = new ResideMenuItem(this, R.drawable.phone, mContext.getResources().getString(R.string.Contactus));
        itemcalculategst = new ResideMenuItem(this, R.drawable.order, mContext.getResources().getString(R.string.calculate));
        itemLogout = new ResideMenuItem(this, R.drawable.logout, mContext.getResources().getString(R.string.Logout));

         itemHome.setOnClickListener(this);
        itemloginreg.setOnClickListener(this);
        itemorderlisting.setOnClickListener(this);
        itemcontactlisting.setOnClickListener(this);
        itemuserlisting.setOnClickListener(this);
        itemaddorderdetail.setOnClickListener(this);
        itemorderbooking.setOnClickListener(this);
        itembookedorderlisting.setOnClickListener(this);
        itemcalculategst.setOnClickListener(this);
        itemgetaqoute.setOnClickListener(this);
        itemcontactus.setOnClickListener(this);
        itemLogout.setOnClickListener(this);

        resideMenu.addMenuItem(itemHome, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemloginreg, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemorderlisting, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemcontactlisting, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemuserlisting, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemaddorderdetail, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemorderbooking, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itembookedorderlisting, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemcalculategst, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemgetaqoute, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemcontactus, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemLogout, ResideMenu.DIRECTION_LEFT);


        // You can disable a direction by setting ->
        // resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_RIGHT);

        findViewById(R.id.title_bar_left_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);

            }
        });

//        findViewById(R.id.title_bar_right_menu).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                resideMenu.openMenu(ResideMenu.DIRECTION_RIGHT);
//            }
//        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return resideMenu.dispatchTouchEvent(ev);
    }

    @Override
    public void onClick(View view) {

        if (view == itemHome) {
            menuheader.setText(getResources().getString(R.string.updateprofile));
            changeFragment(new AdminUpdateFragment());
        } else if (view == itemaddorderdetail) {
            try {
                String userId = commonSession.getLoggedUserID();

                if (CommonUtils.isTextAvailable(userId)) {
                    menuheader.setText(getResources().getString(R.string.OrdeDetail));
                    changeFragment(new OrderDetailFragment());
                } else {
                    showImageDialog(AdminmenuActivity.this);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (view == itemorderlisting) {
            try {
                String userId = commonSession.getLoggedUserID();

                if (CommonUtils.isTextAvailable(userId)) {
                    menuheader.setText(getResources().getString(R.string.OrderListingall));
                    changeFragment(new OrderListAllFragment());
                } else {
                    showImageDialog(AdminmenuActivity.this);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (view == itemorderbooking) {

            menuheader.setText(getResources().getString(R.string.Profile));
            changeFragment(new BookingFragment());

        } else if (view == itemcontactlisting) {

            menuheader.setText(getResources().getString(R.string.contactListing));
            changeFragment(new ContactusListAllFragment());
        } else if (view == itemuserlisting) {
            try {
                String userId = commonSession.getLoggedUserID();

                if (CommonUtils.isTextAvailable(userId)) {


                    menuheader.setText(getResources().getString(R.string.userlist));
                    changeFragment(new UserListAllFragment());
                } else {

                    showImageDialog(AdminmenuActivity.this);

                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (view == itembookedorderlisting) {

            try {
                String userId = commonSession.getLoggedUserID();

                if (CommonUtils.isTextAvailable(userId)) {


                    menuheader.setText(getResources().getString(R.string.BookedOrderss));
                    changeFragment(new BookedAllOrderListFragment());
                } else {

                    showImageDialog(AdminmenuActivity.this);

                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (view == itemgetaqoute) {

            menuheader.setText(getResources().getString(R.string.Getaquote));
            changeFragment(new GetqouteFragment());
        } else if (view == itemcontactus) {

            menuheader.setText(getResources().getString(R.string.Contactus));
            changeFragment(new ContactusFragment());
        } else if (view == itemcalculategst) {

            menuheader.setText(getResources().getString(R.string.CalculateGst));
            changeFragment(new calculateFragment());
        } else if (view == itemLogout) {

            commonSession.resetLoggedUserID();
            commonSession.resetActivityType();
            Intent a = new Intent(this, SplashActivity.class);
            a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(a);
            finish();

        } else if (view == itemloginreg) {

            startActivity(new Intent(AdminmenuActivity.this, LoginActivity.class));
        }


        resideMenu.closeMenu();
    }

    private void changeFragment(Fragment targetFragment) {
        resideMenu.clearIgnoredViewList();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_fragment, targetFragment, "fragment")
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }

    // What good method is to access resideMenu？
    public ResideMenu getResideMenu() {
        return resideMenu;
    }

/*

    private void showInputBox1() {
        final Dialog dialog = new Dialog(MenuActivity.this);
        //   dialog.setTitle("Update");
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); //before
        dialog.setContentView(R.layout.signout_dialog);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);

        Button button6,button5;
        button6 = (Button) dialog.findViewById(R.id.btn_cancel);
        button5 = (Button) dialog.findViewById(R.id.btn_yes);


        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                SharedPreferenceHelper.clearAllPrefs();
                finish();

            }
        });

        dialog.show();

    }*/


    public void showImageDialog(final Activity mActivity) {
        // custom dialog
        popupDialog = new Dialog(mActivity);
        popupDialog.setContentView(R.layout.dialoglogin);
        popupDialog.setCancelable(true);


        txtDialogReport = (Button) popupDialog.findViewById(R.id.login);
        txtDialogCreateMovment = (Button) popupDialog.findViewById(R.id.cancel);

        txtDialogReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupDialog.dismiss();

                startActivity(new Intent(mActivity, LoginActivity.class));
            }
        });


        // Close Button
        txtDialogCreateMovment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupDialog.dismiss();

                //TODO Close button action
            }
        });


        popupDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));

        popupDialog.show();
    }


    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }


}
