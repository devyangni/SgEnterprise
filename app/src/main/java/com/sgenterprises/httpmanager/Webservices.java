package com.sgenterprises.httpmanager;

import com.google.gson.JsonObject;
import com.sgenterprises.Model.BookedBase;
import com.sgenterprises.Model.CalculateBase;
import com.sgenterprises.Model.ContactlistBase;
import com.sgenterprises.Model.CustomerBase;
import com.sgenterprises.Model.HomeBase;
import com.sgenterprises.Model.LoginBase;
import com.sgenterprises.Model.MaterialBase;
import com.sgenterprises.Model.Registerbase;
import com.sgenterprises.Model.ServicesBase;
import com.sgenterprises.Model.UserlistBase;
import com.sgenterprises.Model.UserlistBasea;
import com.sgenterprises.Model.UsersBase;

import java.util.Map;

import app.com.commonlibrary.utils.CommonUtils;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface Webservices {

    @FormUrlEncoded
    @POST("register")
    Call<Registerbase> register(@FieldMap Map<String, String> fields);
    @FormUrlEncoded
    @POST("update_profile")
    Call<Registerbase> profile(@FieldMap Map<String, String> fields);



    @Multipart
    @POST("update_profile")
    Call<Registerbase> profile(@Part MultipartBody.Part file,
                            @Part("user_id") RequestBody user_id,
                            @Part("first_name") RequestBody first_name,
                            @Part("last_name") RequestBody last_name,
                            @Part("email") RequestBody email,
                            @Part("password") RequestBody password,
                            @Part("cpassword") RequestBody cpassword,
                            @Part("phone_no") RequestBody phone_no,
                            @Part("address") RequestBody address,
                            @Part("city") RequestBody city,
                            @Part("state") RequestBody state,
                            @Part("zip") RequestBody zip,
                            @Part("company_name") RequestBody company_name,
                            @Part("user_role") RequestBody user_role);
    @FormUrlEncoded
    @POST("update_profile")
    Call<Registerbase> profilea(@FieldMap Map<String, String> fields);



    @FormUrlEncoded
    @POST("login")
    Call<LoginBase> login(@FieldMap Map<String, String> fields);

    @FormUrlEncoded
    @POST("material")
    Call<MaterialBase> material(@FieldMap Map<String, String> fields);


    @FormUrlEncoded
    @POST("customer")
    Call<CustomerBase> customer(@FieldMap Map<String, String> fields);

     @FormUrlEncoded
    @POST("home")
    Call<HomeBase> home(@FieldMap Map<String, String> fields);

    @FormUrlEncoded
    @POST("our_services")
    Call<HomeBase> servicezz(@FieldMap Map<String, String> fields);

    @FormUrlEncoded
    @POST("book_order")
    Call<MaterialBase> book_order(@FieldMap Map<String, String> fields);

    @FormUrlEncoded
    @POST("order")
    Call<MaterialBase> order(@FieldMap Map<String, String> fields);

    @FormUrlEncoded
    @POST("contact")
    Call<MaterialBase> contact(@FieldMap Map<String, String> fields);

    @FormUrlEncoded
    @POST("gst")
    Call<CalculateBase> gst(@FieldMap Map<String, String> fields);
    @FormUrlEncoded
    @POST("services")
    Call<ServicesBase> services(@FieldMap Map<String, String> fields);

    @FormUrlEncoded
    @POST("booked_orders")
    Call<BookedBase> booked_orders(@FieldMap Map<String, String> fields);

    @FormUrlEncoded
    @POST("order_status")
    Call<BookedBase> order_status(@FieldMap Map<String, String> fields);


    @FormUrlEncoded
    @POST("contact_status")
    Call<BookedBase> contact_status(@FieldMap Map<String, String> fields);

    @FormUrlEncoded
    @POST("user/get_order_list")
    Call<UsersBase> get_order_list(@FieldMap Map<String, String> fields);


    @FormUrlEncoded
    @POST("contact_list")
    Call<ContactlistBase> contact_list(@FieldMap Map<String, String> fields);

    @FormUrlEncoded
    @POST("user_listing")
    Call<UserlistBase> user_listing(@FieldMap Map<String, String> fields);

    @FormUrlEncoded
    @POST("user_list")
    Call<UserlistBase> userlist(@FieldMap Map<String, String> fields);
    @FormUrlEncoded
    @POST("user_detail")
    Call<UserlistBasea> user_profile(@FieldMap Map<String, String> fields);

}



