package com.material.components.data;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

import com.material.components.activity.login.LoginCardLight;
import com.material.components.helper.User;

public class SharedPrefManager extends AppCompatActivity{

    //the constants
    private static final String SHARED_PREF_NAME = "waecsharedpref";
    private static final String KEY_PHONENUMBER = "keyphonenumber";
    private static final String KEY_VERIFYCODE = "keyverifycode";
    private static final String KEY_ID = "keyid";

    private static SharedPrefManager mInstance;
    private static Context mCtx;

    private SharedPrefManager(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    //method to let the user login
    //this method will store the user data in shared preferences
    public void userLogin(User user) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_ID, user.getId());
        editor.putString(KEY_PHONENUMBER, user.getPhonenumber());
        editor.putString(KEY_VERIFYCODE, user.getVerify_code());
        editor.apply();
    }

    //this method will checker whether user is already logged in or not
    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_PHONENUMBER, null) != null;
    }

    //this method will give the logged in user
    public User getUser() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new User(
                sharedPreferences.getInt(KEY_ID, -1),
                sharedPreferences.getString(KEY_PHONENUMBER, null),
                sharedPreferences.getString(KEY_VERIFYCODE, null)
        );
    }

    //this method will logout the user
    public void logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        mCtx.startActivity(new Intent(mCtx, LoginCardLight.class));
    }
}