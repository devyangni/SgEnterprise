package com.sgenterprises.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.GridView;

import com.sgenterprises.R;
import com.sgenterprises.ServiceResideMenu.ResideMenu;

public class AdminHomeFragment extends Fragment {

    Animation animSlideUp;
 //   HomeListAdapter homeListAdapter;
    GridView gridView;
    private View parentView;
    private ResideMenu resideMenu;
    private Toolbar toolbar;
    private RecyclerView RecycleVmavaliableservice;
    private int StartLimit = 0;
    int screen_height = 0, screen_width = 0;
  //  MyProgressDialog myProgressDialog;
   // private List<CategorieResult> requestbean = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.home, container, false);
        getdisplayheightWidth();
        setUpViews();

       /* myProgressDialog = new MyProgressDialog(getContext());
        myProgressDialog.setIndeterminate(true);*/

       // CategotieService();
        return parentView;
    }

    private void setUpViews() {

        AdminmenuActivity parentActivity = (AdminmenuActivity) getActivity();
        resideMenu = parentActivity.getResideMenu();

        // Instance of ImageAdapter Class
    }


    private void getdisplayheightWidth() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screen_height = Math.round(displayMetrics.heightPixels);
        screen_width = Math.round(displayMetrics.widthPixels);
        Log.e("Tag", "screen_width=" + screen_width + "-------screen_height=" + screen_height);
    }





}
