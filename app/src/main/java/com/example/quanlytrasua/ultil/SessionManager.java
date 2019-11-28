package com.example.quanlytrasua.ultil;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import com.example.quanlytrasua.DanhSachBanActivity;
import com.example.quanlytrasua.LoginActivity;

public class SessionManager {
    SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    public Context context;
    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "LOGIN";
    private static final String LOGIN = "IS_LOGIN";
    public static final String USERNAME = "USERNAME";

    public SessionManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

    public void CreateSession(String username){
        editor.putBoolean(LOGIN, true);
        editor.putString(USERNAME,username);
        editor.apply();
    }
    public boolean isLogin(){
        return sharedPreferences.getBoolean(LOGIN,false);
    }
    public void checkLogin(){
        if(!this.isLogin()){
            Intent i = new Intent(context, LoginActivity.class);
            context.startActivity(i);
            ((DanhSachBanActivity)context).finish();
        }
    }

    public void logout(){
        editor.clear();
        editor.commit();
        Intent i = new Intent(context, LoginActivity.class);
        context.startActivity(i);
        ((DanhSachBanActivity)context).finish();
    }
}
