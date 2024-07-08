package com.digitalhorizons.indiamapapp.common.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.digitalhorizons.indiamapapp.R;


public class SessionManager {
    private String IsLoggedIn = "IsLoggedIn";
    //
    private String KEY_ID = "id";
    private String KEY_NAME = "name";
    private String KEY_EMAIL = "email";
    private String KEY_PASSWORD = "password";
    private String KEY_MOBILE_NO = "mob_no";

    private SharedPreferences sharedpreferences;

    public SessionManager(Context paramContext) {
        this.sharedpreferences = paramContext.getSharedPreferences(paramContext.getResources().getString(R.string.app_name), 0);
    }


    public String getKEY_MOBILE_NO() {
        return this.sharedpreferences.getString(this.KEY_MOBILE_NO, "");
    }

    public void setKEY_MOBILE_NO(String paramString) {
        this.sharedpreferences.edit().putString(this.KEY_MOBILE_NO, paramString).apply();
    }

    public boolean getIsLoggedIn() {
        return this.sharedpreferences.getBoolean(this.IsLoggedIn, false);
    }

    public void setIsLoggedIn(boolean isLoggedIn) {
        this.sharedpreferences.edit().putBoolean(this.IsLoggedIn, isLoggedIn).apply();
    }

  /*  public String getKEY_EMAIL() {
        return this.sharedpreferences.getString(this.KEY_EMAIL, "");
    }

    public void setKEY_EMAIL(String paramString) {
        this.sharedpreferences.edit().putString(this.KEY_EMAIL, paramString).apply();
    }


    public int getKEY_ID() {
        return this.sharedpreferences.getInt(this.KEY_ID, 0);
    }

    public void setKEY_ID(int paramString) {
        this.sharedpreferences.edit().putInt(this.KEY_ID, paramString).apply();
    }


    public String getKEY_NAME() {
        return this.sharedpreferences.getString(this.KEY_NAME, "");
    }

    public void setKEY_NAME(String paramString) {
        this.sharedpreferences.edit().putString(this.KEY_NAME, paramString).apply();
    }



    *//* public String getKEY_ID() {
        return this.sharedpreferences.getString(this.KEY_ID, "");
    }

    public void setKEY_ID(String paramString) {
        this.sharedpreferences.edit().putString(this.KEY_ID, paramString).apply();
    }*//*

    public void setSession(UserModel userData){
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(this.KEY_EMAIL, userData.getEmail());

        editor.putInt(this.KEY_ID, userData.getId());
        editor.putString(this.KEY_NAME, userData.getName());
        editor.apply();
    }*/

//    public UserData getSession(){
//        UserData userData = new UserData();
//        userData.setId(sharedpreferences.getInt(KEY_ID, 0));
//        userData.setCategoryId(sharedpreferences.getString(KEY_CAT_ID, "0"));
//        userData.setCatName(sharedpreferences.getString(KEY_CAT_NAME, ""));
//        userData.setName(sharedpreferences.getString(KEY_NAME, ""));
//        userData.setEmail(sharedpreferences.getString(KEY_EMAIL, ""));
//        userData.setRole(sharedpreferences.getString(KEY_ROLE, ""));
//        userData.setAvatar(sharedpreferences.getString(KEY_AVATAR, ""));
//        userData.setMobileNumber(sharedpreferences.getString(KEY_MOBILE_NO, ""));
//        userData.setAlternateNumber(sharedpreferences.getString(KEY_ALTERNATE_MOBILE_NO, ""));
//        userData.setAadharCardNo(sharedpreferences.getString(KEY_AADHAR_NO, ""));
//        userData.setVisiting_charge(Double.parseDouble(sharedpreferences.getString(KEY_VISITING_CHARGE, "0.0")));
//        userData.setHouseNo(sharedpreferences.getString(KEY_HOUSE, ""));
//        userData.setAddress(sharedpreferences.getString(KEY_ADDRESS, ""));
//        userData.setStreet(sharedpreferences.getString(KEY_STREET, ""));
//        userData.setColony(sharedpreferences.getString(KEY_COLONY, ""));
//        userData.setLandmark(sharedpreferences.getString(KEY_LANDMARK, ""));
//        userData.setCity(sharedpreferences.getString(KEY_CITY, ""));
//        userData.setState(sharedpreferences.getString(KEY_STATE, ""));
//        userData.setCountry(sharedpreferences.getString(KEY_COUNTRY, ""));
////        userData.setColony(sharedpreferences.getString(KEY_COLONY, ""));
//        userData.setPincode(sharedpreferences.getString(KEY_PINCODE, ""));
//        userData.setLatitude(sharedpreferences.getString(KEY_LAT, ""));
//        userData.setLongitude(sharedpreferences.getString(KEY_LNG, ""));
//        userData.setWalletBalance(Integer.parseInt(sharedpreferences.getString(KEY_WALLET, "0")));
//        userData.setOtp(sharedpreferences.getInt(KEY_OTP, 0));
//        userData.setReferral_code(sharedpreferences.getString(KEY_REFERRAL_ID, ""));
//        userData.setReferrel_id(sharedpreferences.getString(KEY_REFERRAL_CODE, ""));
//
//        return userData;
//    }

    public void logOut() {
        this.sharedpreferences.edit().clear().apply();
    }
}
