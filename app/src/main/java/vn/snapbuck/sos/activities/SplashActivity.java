package vn.snapbuck.sos.activities;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import vn.snapbuck.sos.R;
import vn.snapbuck.sos.app.BaseActivity;
import vn.snapbuck.sos.utils.CommonUtils;

/**
 * Created by sb04 on 1/28/16.
 */
public class SplashActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CommonUtils.hideStatusBar(this);
        setContentView(R.layout.activity_splash);


        if (getIntent().getBooleanExtra("EXIT", false)) {
            SplashActivity.this.finish();
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
            getParent().finish();
            return;
        }
        startNextActivity();
    }



    void startNextActivity() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainIntent = new Intent(getBaseContext(), MainActivity.class);
                mainIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(mainIntent);
                finish();
            }
        }, 2000);
    }

}
