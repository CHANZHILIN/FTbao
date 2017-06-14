package com.example.jinbiao.ftbao.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by BAO on 2017/6/14.
 */

public class SharedHelper {
    private Context context;

    public SharedHelper(Context context) {
        this.context = context;
    }

    public void saveinfo(String username,String key){
        SharedPreferences sp = context.getSharedPreferences("userinfo",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("username",username);
        editor.putString("key",key);
        editor.commit();

    }
}
