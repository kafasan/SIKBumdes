package com.sikbumdes.bumdes.api;

import android.content.Context;
import android.content.SharedPreferences;

import com.sikbumdes.bumdes.model.User;

public class SharedPrefManager {

    private static final String SIK_BUMDES = "SIKBumdes";

    private static SharedPrefManager mInstance;
    private Context mContext;

    public SharedPrefManager(Context mContext) {
        this.mContext = mContext;
    }

    public static synchronized SharedPrefManager getInstance(Context mContext){
        if (mInstance == null){
            mInstance = new SharedPrefManager(mContext);
        }
        return mInstance;
    }

    public void saveUser(User user){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SIK_BUMDES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("id", user.getId());
        editor.putString("token", user.getToken());
        editor.putString("company", user.getCompany());

        editor.apply();
    }

    public boolean isLoggedIn(){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SIK_BUMDES, Context.MODE_PRIVATE);
        return sharedPreferences.getString("id", null) != null;
    }

    public User getUser(){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SIK_BUMDES, Context.MODE_PRIVATE);
        return new User(
                sharedPreferences.getString("id", null),
                sharedPreferences.getString("token", null),
                sharedPreferences.getString("company", null)
        );
    }

    public void clear(){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SIK_BUMDES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
