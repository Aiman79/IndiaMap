package com.digitalhorizons.indiamapapp.splash.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import com.digitalhorizons.indiamapapp.R;
import com.digitalhorizons.indiamapapp.home.view.ClsMainActivity;
import com.digitalhorizons.indiamapapp.register.view.ClsRegisterWithOTPActivity;
import com.digitalhorizons.indiamapapp.common.utils.SessionManager;

public class ClsSplashActivity extends AppCompatActivity {
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary));
        super.onCreate(savedInstanceState);

        sessionManager = new SessionManager(this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (sessionManager.getIsLoggedIn()){
                    startMainScreen();
                } else {
                    startRegisterScreen();
                }
            }
        }, 1000);
    }

    public void startRegisterScreen(){
        Intent intent = new Intent(ClsSplashActivity.this, ClsRegisterWithOTPActivity.class);
        startActivity(intent);
        finish();
    }

    public void startMainScreen(){
        Intent intent = new Intent(ClsSplashActivity.this, ClsMainActivity.class);
        startActivity(intent);
        finish();
    }
}