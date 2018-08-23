Hello Developer,
Happy Coding....

this library contain many common METHODS,STRINGS, COLORS, DIAMENTIONS, STYLE which are very useful.



-----------------------------------------------------------------------------------------------------------------------
add this into Project level gradle--------

ext {
    compileSdkVersion = 23
    buildToolsVersion = "23.0.3"

    minSdkVersion = 17
    targetSdkVersion = 23


}

add this into Module level gradle-------------


 compileSdkVersion rootProject.compileSdkVersion
    buildToolsVersion rootProject.buildToolsVersion

    defaultConfig {
        applicationId "app.com.commonlibraryproject"
        minSdkVersion rootProject.minSdkVersion
        targetSdkVersion rootProject.targetSdkVersion
        ....
        ....
        ....
    }
-----------------------------------------------------------------------------------------------------------------------
  *********-Toast-**********
  
 CommonUtils.commonToast(context,"Internet Available");
-----------------------------------------------------------------------------------------------------------------------
                *********-For the checking INTERNET**********

1) Add Permissions-

<uses-permission android:name="android.permission.INTERNET"></uses-permission>
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />

2) Call Method

                   if (CommonUtils.isConnectingToInternet(getApplicationContext())) {
                       CommonUtils.commonToast(context,"Internet Available");
                   } else {
                       CommonUtils.showNoInternetSnackBar(rootLayout, mOnClickListener);
                   }



        -----------------------
        showNoInternetSnackBar()
        -----------------------

(1) declare globlay
View.OnClickListener mOnClickListener;

(2) Call Method----CommonUtils.showNoInternetSnackBar(activity_main, mOnClickListener);

(3) onCreate -

mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                CommonUtils.commonToast(context,"retry");
            }
        };

 


-----------------------------------------------------------------------------------------------------------------------

  *********- Progressbar dialog -**********
  
 CommonUtils.showDialog(context,"please wait");


 for Dismiss Dialog------ CommonUtils.dismissProgressDialog();

-----------------------------------------------------------------------------------------------------------------------

*********- Custom dialog -**********

1) declare globlay
private DialogInterface.OnClickListener checkInPositiveButton;
    private DialogInterface.OnClickListener checkInNegativeButton;

(2)

 Call Method------

 LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().
                         getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View convertView = layoutInflater.inflate(R.layout.custom_dialoug_success, null);



                 final Dialog checkInAlert = DialogUtils.createDialog(MainActivity.this, R.string.app_name,
                         R.string.attempting,convertView , checkInPositiveButton, checkInNegativeButton);

                 checkInAlert.show();



    NOTE---if you would not pass Layout view then pass === null
    for i.e

    final Dialog checkInAlert = DialogUtils.createDialog(MainActivity.this, R.string.app_name,
                             R.string.attempting,null , checkInPositiveButton, checkInNegativeButton);

                     checkInAlert.show();


(3) onCreate -

checkInPositiveButton = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // check in
                CommonUtils.commonToast(MainActivity.this,"ok");

            }
        };

        checkInNegativeButton = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
                CommonUtils.commonToast(MainActivity.this,"cancel");

            }
        };


///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
Other methods...
-----------------------------------------------------------------------------------------------------------------------
isValidUrl(String string)
-----------------------------------------------------------------------------------------------------------------------
convertStringToDate(String date)
-----------------------------------------------------------------------------------------------------------------------
finishFragmentOrActivity(View v, Activity activity, FragmentManager fragmentManager)
-----------------------------------------------------------------------------------------------------------------------
roundToOneDigit(float paramFloat)
-----------------------------------------------------------------------------------------------------------------------
isEmailValid(String email)
-----------------------------------------------------------------------------------------------------------------------
isTextAvailable(String text)
-----------------------------------------------------------------------------------------------------------------------
convertDateTime(String fromFormat, String toFormat, String dateOriginalGot)
-----------------------------------------------------------------------------------------------------------------------
forceHide(FragmentActivity activity, EditText editText)
-----------------------------------------------------------------------------------------------------------------------
hideSoftKeyboard(Activity activity)
-----------------------------------------------------------------------------------------------------------------------

For Other common methods refer this class
 ---------------------------------------
app.com.commonlibrary.utils.CommonUtils;

-----------------------------------------------------------------------------------------------------------------------
SharedPreferenceHelper






