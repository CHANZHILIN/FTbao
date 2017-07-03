package com.example.jinbiao.ftbao.utils;

import android.util.Log;

/**
 * Log工具类  可以设置当前LEVEL常量级别控制Log的打印输出
 * Created by CHEN_ on 2017/6/28.
 */

public class LogUtils {
    private static final int VERBOSE = 1;
    private static final int DEBUG = 2;
    private static final int INFO = 3;
    private static final int WARN = 4;
    private static final int ERROR = 5;
    private static final int NOTHING = 6;

    //当LEVEL的级别为VERBOSE时，则全部显示
    //当LEVEL的级别为NOTHING时，则全部不显示
    private static final int LEVEL = VERBOSE;
    public static void v(String tag, String msg){
        if (LEVEL <= VERBOSE){
            Log.v(tag,msg);
        }
    }

    public static void d(String tag, String msg){
        if (LEVEL <= DEBUG){
            Log.d(tag,msg);
        }
    }

    public static void i(String tag, String msg){
        if (LEVEL <= INFO){
            Log.i(tag,msg);
        }
    }

    public static void w(String tag, String msg){
        if (LEVEL <= WARN){
            Log.w(tag,msg);
        }
    }

    public static void e(String tag, String msg){
        if (LEVEL <= ERROR){
            Log.e(tag,msg);
        }
    }
}
