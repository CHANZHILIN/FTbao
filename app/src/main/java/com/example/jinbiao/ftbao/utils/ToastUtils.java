package com.example.jinbiao.ftbao.utils;

import android.content.Context;
import android.widget.Toast;

import com.example.jinbiao.ftbao.R;

/**
 * Created by BAO on 2017/6/11.
 */

public class ToastUtils {

    public static void toastLong(Context context,String string){
        Toast.makeText(context, string, Toast.LENGTH_LONG).show();
    }

    public static void toastShort(Context context,String string){
        Toast.makeText(context, string, Toast.LENGTH_SHORT).show();
    }


}
