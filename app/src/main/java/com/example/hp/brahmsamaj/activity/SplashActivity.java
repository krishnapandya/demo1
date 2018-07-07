package com.example.hp.brahmsamaj.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;

import com.example.hp.brahmsamaj.BaseActivity;
import com.example.hp.brahmsamaj.R;
import com.example.hp.brahmsamaj.constatns.AppConstants;
import com.example.hp.brahmsamaj.utility.UserPreferenceManager;

/**
 * Created by HP on 07-06-2018.
 */

public class SplashActivity extends BaseActivity {

    private View view;
    private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = LayoutInflater.from(this).inflate(R.layout.activity_splash, frameLayout);
        showToolbar(false);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                if (UserPreferenceManager.preferenceGetBoolean(AppConstants.SharedPreferenceKey.IS_LOGGED_IN, false)) {
                    Intent i = new Intent(SplashActivity.this, HomeActivity.class);
                    startActivity(i);
                    finish();
                } else {
                    Intent i1 = new Intent(SplashActivity.this, LoginActivityNew.class);
                    startActivity(i1);
                    finish();
                }
            }
        }, SPLASH_TIME_OUT);
    }
}
