package app.com.commonlibrary;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

public class CoreBaseActivity extends AppCompatActivity {

    public Typeface fontAwesomeFont;


    public void init(Activity mActivity) {
        try {
              fontAwesomeFont = Typeface.createFromAsset(getAssets(), "fontawesome-webfont.ttf");

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = this.getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorLightBlue));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
