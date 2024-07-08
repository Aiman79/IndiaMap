package com.digitalhorizons.indiamapapp.register.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.digitalhorizons.indiamapapp.R;
import com.digitalhorizons.indiamapapp.home.view.ClsMainActivity;
import com.digitalhorizons.indiamapapp.common.utils.SessionManager;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class ClsRegisterWithOTPActivity extends AppCompatActivity {
    private AppCompatButton btnNext;
    private AppCompatEditText etMob;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cls_register_with_otpactivity);

        registerViews();
        addListeners();
    }

    public void registerViews(){
        sessionManager = new SessionManager(this);

        etMob = findViewById(R.id.et_mob);
        btnNext = findViewById(R.id.btn_next);
    }

    public void addListeners(){
        btnNext.setOnClickListener(view -> {
            if (isValidMob(etMob.getText().toString())){
                openBottomSheetDialogForAddress();
            } else {
                etMob.setError(getMobNoError());
            }
        });
    }

    public void openBottomSheetDialogForAddress() {
        View view = getLayoutInflater().inflate(R.layout.bottomsheet_fragment_otp, null);
        BottomSheetDialog dialog = new BottomSheetDialog(this);
        dialog.setContentView(view);
        dialog.setCancelable(false);

        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) view.getParent())
                .getLayoutParams();
        CoordinatorLayout.Behavior behavior = params.getBehavior();
        ((View) view.getParent()).setBackgroundColor(ContextCompat.getColor(this,
                R.color.colorWhiteTransparentDark));

        dialog.show();

        AppCompatEditText etOTP = view.findViewById(R.id.et_otp);
        AppCompatImageView ivBack = view.findViewById(R.id.iv_back);
        AppCompatButton btnRegister = view.findViewById(R.id.btn_submit);

        btnRegister.setOnClickListener(view1 -> {
            if (isValidOTP(etOTP.getText().toString())){
                sessionManager.setIsLoggedIn(true);
                sessionManager.setKEY_MOBILE_NO(etMob.getText().toString());
                startMainScreen();
            } else {
                etOTP.setError(getOTPError());
            }
        });

        ivBack.setOnClickListener(view1 -> {
            dialog.dismiss();
        });
    }

    public void startMainScreen(){
        Intent intent = new Intent(ClsRegisterWithOTPActivity.this, ClsMainActivity.class);
        startActivity(intent);
        finish();
    }

    public boolean isValidMob(String no){
        if (no.length() != 10){
            return false;
        } else return true;
    }

    public boolean isValidOTP(String otp){
        if (otp.isEmpty()){
            return false;
        } else return true;
    }

    public String getMobNoError(){
        return getString(R.string.error_mob_no);
    }

    public String getOTPError(){
        return getString(R.string.error_otp);
    }

}