package com.sgenterprises.Home;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.sgenterprises.Adapter.Adapter_userlist;
import com.sgenterprises.Common.BaseClass;
import com.sgenterprises.Common.CompressImageUtil;
import com.sgenterprises.Model.Error;
import com.sgenterprises.Model.LoginBase;
import com.sgenterprises.Model.Registerbase;
import com.sgenterprises.Model.UserList;
import com.sgenterprises.Model.UserlistBase;
import com.sgenterprises.Model.UserlistBasea;
import com.sgenterprises.Model.Userlista;
import com.sgenterprises.R;
import com.sgenterprises.httpmanager.ApiHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.com.commonlibrary.utils.CommonUtils;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateProfileActivity extends BaseClass{
    public ArrayList<String> filterAddressListDisallow;


    String selectedImagePathFront = "";
    Bitmap bitmapImagePathFront = null;
    private Toolbar toolbar;
    private Uri imageUri;
    private int PICK_FROM_CAMERA = 123;
    private int PICK_FROM_GALLARY = 1;
    private CircleImageView imagProfile;


    public TextView txtDialogReport;
    public TextView txtDialogSetting;
    public TextView txtDialogCancel;
    public TextView profile;

    private EditText edtFname;
    private EditText edtLname;
    private EditText edtCompany;
    private EditText email;
    private EditText password;
    private EditText cpassword;
    private EditText edtAddress;
    private EditText edtCity;
    private EditText edtState;
    private EditText edtZip;
    private EditText edtfile;
    private TextView edtprofile;
    private Button btnUpload;
    private Button btnLogin;
    public static UpdateProfileActivity activity;
    String TAG = UpdateProfileActivity.class.getName();
    String first_name = null;
    String last_name = null;
    String user_role = "1";
    String company_name = null;
    String emailid = null;
    String passwordd = null;
    String cpasswordd = null;
    String phone_no = null;
    String address = null;
    String city = null;
    String state = null;
    String zip = null;
    String Company = null;
    View parentView;
    String user_id="";
    
    Bundle b;

    public Dialog popupDialog;
    Userlista userLists = new Userlista();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragmentuser);
        activity =this;
        init(activity);
        findViews();


        b =getIntent().getExtras();
        if (b!= null){

            user_id = b.getString("list");
            Log.e("user_id",user_id);

        }

        findViews();
        getEvent();
        setAction();


        //setAction();
    }


 
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK)

            if (resultCode == RESULT_OK && requestCode == PICK_FROM_GALLARY) {

                bitmapImagePathFront = null;
                selectedImagePathFront = null;

                try {
                    if (data != null) {

                        Uri selectedImage = data.getData();
                        String[] filePath = {MediaStore.Images.Media.DATA};
                        Cursor c = UpdateProfileActivity.this.getContentResolver().query(selectedImage, filePath,
                                null, null, null);
                        c.moveToFirst();
                        int columnIndex = c.getColumnIndex(filePath[0]);
                        selectedImagePathFront = c.getString(columnIndex);
                        c.close();

                        CompressImageUtil compressUtil = new CompressImageUtil(UpdateProfileActivity.this);
                        selectedImagePathFront = compressUtil.compressImage(selectedImagePathFront, imagProfile);
                        bitmapImagePathFront = compressUtil.getBitmap();

                        Log.e("path", selectedImagePathFront);


                        if (!selectedImagePathFront.equalsIgnoreCase("")) {


                        } else {

                        }


                    } else {
                       /* Toast.makeText(getApplicationContext(), getResources().getString(R.string.camera_cancelled),
                                Toast.LENGTH_SHORT).show();*/
                    }
                } catch (Exception e) {

                    //if third party gallery  then this code will execute.

                    Log.e("ad", "gallery app message=" + e.getMessage());
                    Log.e("ad", "gallery app exception=" + e.toString());
//                Toast.makeText(getApplicationContext(), getResources().getString(R.string.gallary_error),
//                        Toast.LENGTH_SHORT).show();

                    Uri selectedImageUri = data.getData();
                    // selectedImagePathFront = uriToFilename(selectedImageUri);

                  /*  CompressImageUtil compressUtil = new CompressImageUtil(getApplicationContext());
                    selectedImagePathFront = compressUtil.compressImage(selectedImagePathFront, img_card_front);
                    bitmapImagePathFront = compressUtil.getBitmap();
*/
                    e.printStackTrace();
                } finally {

                }
            }
            //  onSelectFromGalleryResult(data);

            else if (requestCode == PICK_FROM_CAMERA) {
                bitmapImagePathFront = null;
                selectedImagePathFront = null;
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                imagProfile.setImageBitmap(photo);
                Uri imageUri =  getImageUri(UpdateProfileActivity.this,photo);

                Log.e("selectedImagePathFront", String.valueOf(imageUri));
                selectedImagePathFront = getRealPathFromURI(imageUri);



                if (!selectedImagePathFront.equalsIgnoreCase("")) {


                } else {

                }

            }
    }
    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = UpdateProfileActivity.this.getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }
    protected Map<String, String> getParamsorder() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("user_id",user_id);
        Log.e("PArams", params.toString());
        return params;

    }

    private void setdata() {



        if (CommonUtils.isTextAvailable(userLists.getFirstName())) {

            edtFname.setText(userLists.getFirstName());
        }
        if (CommonUtils.isTextAvailable(userLists.getLastName())) {

            edtLname.setText(userLists.getLastName());
        }
        if (CommonUtils.isTextAvailable(userLists.getPhoneNo())) {

            edtfile.setText(userLists.getPhoneNo());
        }
        if (CommonUtils.isTextAvailable(userLists.getCompanyName())) {

            edtCompany.setText(userLists.getCompanyName());
        }
        if (CommonUtils.isTextAvailable(userLists.getEmail())) {

            email.setText(userLists.getEmail());
        }
        if (CommonUtils.isTextAvailable(userLists.getAddress())) {

            edtAddress.setText(userLists.getAddress());
        }if (CommonUtils.isTextAvailable(userLists.getPassword())) {

            password.setText(userLists.getPassword());
        }
        if (CommonUtils.isTextAvailable(userLists.getPassword())) {

            cpassword.setText(userLists.getPassword());
        }
        if (CommonUtils.isTextAvailable(userLists.getCity())) {

            edtCity.setText(userLists.getCity());
        }

        if (CommonUtils.isTextAvailable(userLists.getState())) {

            edtState.setText(userLists.getState());
        }
        if (CommonUtils.isTextAvailable(userLists.getZip())) {

            edtZip.setText(userLists.getZip());
        }if (CommonUtils.isTextAvailable(userLists.getProfileImage())) {


            Picasso.with(UpdateProfileActivity.this)
                    .load(userLists.getProfileImage())
                    .error(R.drawable.uprofile)         // optional
                    .into(imagProfile);
        }

    }

    public void getEvent() {

        if (CommonUtils.isConnectingToInternet(UpdateProfileActivity.this)) {
            final Dialog dialog = new Dialog(UpdateProfileActivity.this);
            dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setContentView(R.layout.dialog_wait);
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
            Call<UserlistBasea> call = ApiHandler.getApiService().user_profile(getParamsorder());
            call.enqueue(new Callback<UserlistBasea>() {
                @Override
                public void onResponse(Call<UserlistBasea> call, Response<UserlistBasea> response) {
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
                                userLists = response.body().getData();


                                setdata();


                            } else {

                                CommonUtils.commonToast(UpdateProfileActivity.this, message);

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
                public void onFailure(Call<UserlistBasea> call, Throwable t) {
                    Log.e(TAG, "Unable to submit post to API.");

                    dialog.dismiss();
                }
            });

        } else {
            Log.e(TAG, "error" + "no internet");

            CommonUtils.commonToast(UpdateProfileActivity.this, getResources().getString(R.string.no_internet_exist));

        }

    }

    private void setAction() {


        edtprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtFname.setFocusable(true);
                edtLname.setFocusable(true);
                edtCompany.setFocusable(true);
                email.setFocusable(true);
                password.setFocusable(true);
                cpassword.setFocusable(true);
                edtAddress.setFocusable(true);
                edtCity.setFocusable(true);
                edtState.setFocusable(true);
                edtZip.setFocusable(true);
                edtfile.setFocusable(true);
                edtFname.setFocusableInTouchMode(true);
                edtLname.setFocusableInTouchMode(true);
                edtCompany.setFocusableInTouchMode(true);
                email.setFocusableInTouchMode(true);
                password.setFocusableInTouchMode(true);
                cpassword.setFocusableInTouchMode(true);
                edtAddress.setFocusableInTouchMode(true);
                edtCity.setFocusableInTouchMode(true);
                edtState.setFocusableInTouchMode(true);
                edtZip.setFocusableInTouchMode(true);
                edtfile.setFocusableInTouchMode(true);
                profile.setVisibility(View.VISIBLE);
                edtprofile.setVisibility(View.GONE);
                btnLogin.setVisibility(View.VISIBLE);


            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                first_name = edtFname.getText().toString();
                last_name = edtLname.getText().toString();
                Company = edtCompany.getText().toString();
                emailid = email.getText().toString();
                passwordd = password.getText().toString();
                cpasswordd = cpassword.getText().toString();
                phone_no = edtfile.getText().toString();
                address = edtAddress.getText().toString();
                city = edtCity.getText().toString();
                state = edtState.getText().toString();
                zip = edtZip.getText().toString();

                if (TextUtils.isEmpty(first_name)) {
                    CommonUtils.commonToast(UpdateProfileActivity.this, getResources().getString(R.string.enterfname));
                } else if (TextUtils.isEmpty(last_name)) {
                    CommonUtils.commonToast(UpdateProfileActivity.this, getResources().getString(R.string.enterfname));
                } else if (TextUtils.isEmpty(Company)) {
                    CommonUtils.commonToast(UpdateProfileActivity.this, getResources().getString(R.string.entercname));
                } else if (TextUtils.isEmpty(phone_no)) {
                    CommonUtils.commonToast(UpdateProfileActivity.this, getResources().getString(R.string.entermobno));
                } else if (phone_no.length() != 10) {
                    CommonUtils.commonToast(UpdateProfileActivity.this, getResources().getString(R.string.entervmobno));
                } else if (TextUtils.isEmpty(emailid)) {
                    CommonUtils.commonToast(UpdateProfileActivity.this, getResources().getString(R.string.enteremailid));

                } else if (!CommonUtils.isEmailValid(emailid)) {
                    CommonUtils.commonToast(UpdateProfileActivity.this, getResources().getString(R.string.validemail));

                } else if (TextUtils.isEmpty(passwordd)) {
                    CommonUtils.commonToast(UpdateProfileActivity.this, getResources().getString(R.string.enterpassword));
                } else if (passwordd.length() < 6) {
                    CommonUtils.commonToast(UpdateProfileActivity.this, getResources().getString(R.string.minpassword));
                } else if (TextUtils.isEmpty(cpasswordd)) {
                    CommonUtils.commonToast(UpdateProfileActivity.this, getResources().getString(R.string.entercpassword));
                } else if (!passwordd.toString().equals(cpasswordd.toString())) {
                    CommonUtils.commonToast(UpdateProfileActivity.this, getResources().getString(R.string.validcpassword));
                } else if (!CommonUtils.isTextAvailable(address)) {
                    CommonUtils.commonToast(UpdateProfileActivity.this, getResources().getString(R.string.enteradd));
                } else if (address.length() < 10) {
                    CommonUtils.commonToast(UpdateProfileActivity.this, getResources().getString(R.string.entervadd));
                } else {
                    try {

                        getregister();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        });
    }


    protected Map<String, String> getParams() {
        Map<String, String> params = new HashMap<String, String>();

        params.put("user_id", user_id);
        params.put("first_name", first_name);
        params.put("last_name", last_name);
        params.put("email", emailid);
        params.put("password", passwordd);
        params.put("cpassword", cpasswordd);
        params.put("phone_no", phone_no);

        if (CommonUtils.isTextAvailable(address)) {
            params.put("address", address);
        } else {
            params.put("address", "");
        }

        if (CommonUtils.isTextAvailable(city)) {
            params.put("city", city);
        } else {
            params.put("city", "");
        }

        if (CommonUtils.isTextAvailable(state)) {
            params.put("state", state);
        } else {
            params.put("state", "");
        }

        if (CommonUtils.isTextAvailable(zip)) {
            params.put("zip", zip);
        } else {
            params.put("zip", "");
        }
        params.put("company_name", Company);

        Log.e("PArams", params.toString());

        return params;

    }

    public void getregister() {

        if (CommonUtils.isConnectingToInternet(UpdateProfileActivity.this)) {
            final Dialog dialog = new Dialog(UpdateProfileActivity.this);
            dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setContentView(R.layout.dialog_wait);
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();

            Call<Registerbase> call ;

            if (selectedImagePathFront.equalsIgnoreCase(""))
            {

                call = ApiHandler.getApiService().profilea(getParams());
            }
            else {
                File file = new File(selectedImagePathFront);


                // create RequestBody instance from file
                RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                RequestBody id = RequestBody.create(MediaType.parse("text/plain"), commonSession.getLoggedUserID());
                RequestBody first_namea = RequestBody.create(MediaType.parse("text/plain"), first_name);
                RequestBody last_namea = RequestBody.create(MediaType.parse("text/plain"), last_name);
                RequestBody emailida = RequestBody.create(MediaType.parse("text/plain"), emailid);
                RequestBody passwordda = RequestBody.create(MediaType.parse("text/plain"), passwordd);
                RequestBody cpasswordda = RequestBody.create(MediaType.parse("text/plain"), cpasswordd);
                RequestBody phone_noa = RequestBody.create(MediaType.parse("text/plain"), phone_no);
                RequestBody addressa = RequestBody.create(MediaType.parse("text/plain"), address);
                RequestBody citya = RequestBody.create(MediaType.parse("text/plain"), city);
                RequestBody statea = RequestBody.create(MediaType.parse("text/plain"), state);
                RequestBody zipa = RequestBody.create(MediaType.parse("text/plain"), zip);
                RequestBody Companya = RequestBody.create(MediaType.parse("text/plain"), Company);
                RequestBody userrolea = RequestBody.create(MediaType.parse("text/plain"), user_role);
                MultipartBody.Part body = MultipartBody.Part.createFormData("profile_image", file.getName(), requestFile);

                call = ApiHandler.getApiService().profile(body, id,
                        first_namea,
                        last_namea,
                        emailida,
                        passwordda,
                        cpasswordda,
                        phone_noa,
                        addressa,
                        citya,
                        statea,
                        zipa,
                        Companya,
                        userrolea);
            }
            call.enqueue(new Callback<Registerbase>() {
                @Override
                public void onResponse(Call<Registerbase> call, Response<Registerbase> response) {
                    try {
                        Log.e(TAG, " Full json gson => " + new Gson().toJson(response));
                        JSONObject jsonObj = new JSONObject(new Gson().toJson(response).toString());
                        Log.e(TAG, " responce => " + jsonObj.getJSONObject("body").toString());
                        dialog.dismiss();
                        if (response.isSuccessful()) {

                            String success = response.body().getSuccess();
                            String message = response.body().getMsg();
                            if (success.equalsIgnoreCase("true")) {
                                CommonUtils.commonToast(UpdateProfileActivity.this, message);
                                edtprofile.setVisibility(View.VISIBLE);
                                btnLogin.setVisibility(View.GONE);
                                profile.setVisibility(View.GONE);

                                edtFname.setFocusable(false);
                                edtLname.setFocusable(false);
                                edtCompany.setFocusable(false);
                                email.setFocusable(false);
                                password.setFocusable(false);
                                cpassword.setFocusable(false);
                                edtAddress.setFocusable(false);
                                edtCity.setFocusable(false);
                                edtState.setFocusable(false);
                                edtZip.setFocusable(false);
                                edtfile.setFocusable(false);
                                edtFname.setFocusableInTouchMode(false);
                                edtLname.setFocusableInTouchMode(false);
                                edtCompany.setFocusableInTouchMode(true);
                                email.setFocusableInTouchMode(false);
                                password.setFocusableInTouchMode(false);
                                cpassword.setFocusableInTouchMode(false);
                                edtAddress.setFocusableInTouchMode(false);
                                edtCity.setFocusableInTouchMode(false);
                                edtState.setFocusableInTouchMode(false);
                                edtZip.setFocusableInTouchMode(false);
                                edtfile.setFocusableInTouchMode(false);
                            } else if (success.equalsIgnoreCase("false")) {

                                showImageDialog(response.body().getError(),UpdateProfileActivity.this);


                            } else {

                                CommonUtils.commonToast(UpdateProfileActivity.this, getResources().getString(R.string.no_internet_exist));
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
                public void onFailure(Call<Registerbase> call, Throwable t) {
                    Log.e(TAG, "Unable to submit post to API.");
                    dialog.dismiss();
                }
            });

        } else {
            Log.e(TAG, "error" + "no internet");

            CommonUtils.commonToast(UpdateProfileActivity.this, getResources().getString(R.string.no_internet_exist));

        }

    }


    private void findViews() {
        profile = (TextView)  findViewById(R.id.profile);
        edtFname = (EditText)  findViewById(R.id.edt_fname);
        edtLname = (EditText)  findViewById(R.id.edt_lname);
        edtCompany = (EditText)  findViewById(R.id.edt_companyname);
        email = (EditText)  findViewById(R.id.email);
        password = (EditText)  findViewById(R.id.password);
        cpassword = (EditText)  findViewById(R.id.cpassword);
        edtAddress = (EditText)  findViewById(R.id.edt_address);
        edtCity = (EditText)  findViewById(R.id.edt_city);
        edtState = (EditText)  findViewById(R.id.edt_state);
        edtZip = (EditText)  findViewById(R.id.edt_zip);
        edtfile = (EditText)  findViewById(R.id.edtfile);
        edtprofile = (TextView)  findViewById(R.id.edtprofile);
        btnUpload = (Button)  findViewById(R.id.btn_upload);
        btnLogin = (Button)  findViewById(R.id.btn_login);
        edtprofile.setVisibility(View.VISIBLE);
        btnLogin.setVisibility(View.GONE);

        imagProfile = (CircleImageView)  findViewById(R.id.imag_profile);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showImageDialog1(UpdateProfileActivity.this);
            }
        });

    }

    public void showImageDialog(Error errors, final Activity mActivity) {
        // custom dialog
        popupDialog = new Dialog(mActivity);
        popupDialog.setContentView(R.layout.dialogregister);
        popupDialog.setCancelable(true);
        TextView edte1,edte2,edte3,edte4,e,edte5,edte6,edte7;
        Button txtDialogCreateMovment;

        edte1 = (TextView)popupDialog.findViewById( R.id.error1 );
        edte2 = (TextView)popupDialog.findViewById( R.id.error2 );
        edte3 = (TextView)popupDialog.findViewById( R.id.error3 );
        edte4 = (TextView)popupDialog.findViewById( R.id.error4 );
        edte5 = (TextView)popupDialog.findViewById( R.id.error5 );
        edte6 = (TextView)popupDialog.findViewById( R.id.error6 );
        edte7 = (TextView)popupDialog.findViewById( R.id.error7 );

        edte1.setVisibility(View.GONE);
        edte2.setVisibility(View.GONE);
        edte3.setVisibility(View.GONE);
        edte4.setVisibility(View.GONE);
        edte5.setVisibility(View.GONE);
        edte6.setVisibility(View.GONE);
        edte7.setVisibility(View.GONE);

        String error ="",error1 ="",error2 ="",error3 ="",error4 ="",error5 ="",error6 ="";
        error =    errors.getFirstName();
        error1 =   errors.getLastName();
        error2 =   errors.getCompanyName();
        error3 =   errors.getPhoneNo();
        error4 =   errors.getPassword();
        error5 =   errors.getEmail();
        error6 =   errors.getAddress();

        if (CommonUtils.isTextAvailable(error))
        {   edte1.setVisibility(View.VISIBLE);
            edte1.setText(error);

        }
        if (CommonUtils.isTextAvailable(error1))
        {
            edte2.setVisibility(View.VISIBLE);
            edte2.setText(error1);

        }
        if (CommonUtils.isTextAvailable(error2))
        {   edte3.setVisibility(View.VISIBLE);
            edte3.setText(error2);
        }

        if (CommonUtils.isTextAvailable(error5))
        {   edte4.setVisibility(View.VISIBLE);
            edte4.setText(error5);

        }
        if (CommonUtils.isTextAvailable(error4))
        {   edte5.setVisibility(View.VISIBLE);
            edte5.setText(error4);
            password.setHint(error4);
        }
        if (CommonUtils.isTextAvailable(error3))
        {   edte6.setVisibility(View.VISIBLE);
            edte6.setText(error3);

        }  if (CommonUtils.isTextAvailable(error6))
        {    edte7.setVisibility(View.VISIBLE);
            edte7.setText(error6);

        }


        txtDialogCreateMovment = (Button)popupDialog.findViewById( R.id.txtDialogCreateMovment );



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


    public void showImageDialog1(final Activity mActivity) {
        // custom dialog
        popupDialog = new Dialog(mActivity);
        popupDialog.setContentView(R.layout.custom_settingdialoga);
        popupDialog.setCancelable(true);


        txtDialogReport = (TextView) popupDialog.findViewById(R.id.txt_dialog_report);
        txtDialogSetting = (TextView) popupDialog.findViewById(R.id.txt_dialog_setting);
        txtDialogCancel = (TextView) popupDialog.findViewById(R.id.txt_dialog_cancel);
        // set the custom dialog components - text, image and button
        txtDialogReport.setText("Take Photo");
        txtDialogSetting.setText("Choose From Library");
        txtDialogReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupDialog.dismiss();

                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, PICK_FROM_CAMERA);
                //  startActivity(new Intent(mActivity, Reporta.class));
            }
        });
        txtDialogSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupDialog.dismiss();

                Intent pictureActionIntent = null;
                pictureActionIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pictureActionIntent, PICK_FROM_GALLARY);
                // startActivity(new Intent(mActivity, SettingFirstActivity.class));
            }
        });

        // Close Button
        txtDialogCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupDialog.dismiss();

                //TODO Close button action
            }
        });
        popupDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));

        popupDialog.show();
    }

}
