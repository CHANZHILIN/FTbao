package com.example.jinbiao.ftbao.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

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

    public static void saveInfo(Context context,String username, String key, boolean isAutoLogin) {
        SharedPreferences sp = context.getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("username", username);
        editor.putString("key", key);
        editor.putBoolean("autologin",isAutoLogin);
        editor.putLong("logintime", System.currentTimeMillis());
        editor.commit();

        Log.e("SharedHelper", "saveInfo: username:"+ username);
        Log.e("SharedHelper", "saveInfo: uuid:"+ key);

    }

    public static Long getTime(){

        SharedPreferences sp = context.getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        Long time = sp.getLong("time",0);
        return time;

    }


}
