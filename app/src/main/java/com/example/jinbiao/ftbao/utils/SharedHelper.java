package com.example.jinbiao.ftbao.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by BAO on 2017/6/14.
 */

public class SharedHelper {
    private static Context context;

    public SharedHelper(Context context) {
        this.context = context;
    }

    public static void saveInfo(String username, String key) {
        SharedPreferences sp = context.getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("username", username);
        editor.putString("key", key);
        editor.putBoolean("autologin",true);
        editor.putLong("logintime", System.currentTimeMillis());
        editor.commit();

    }

    public static Long getTime(){

        SharedPreferences sp = context.getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        Long time = sp.getLong("time",0);
        return time;

    }


}
