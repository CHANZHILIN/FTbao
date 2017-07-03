package com.example.jinbiao.ftbao.utils;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Activity集合类
 * Created by CHEN_ on 2017/6/30.
 */

public class ActivityCollector {
    public static List<Activity> activites = new ArrayList<>();

    public static void addActivity(Activity activity){
        activites.add(activity);
    }

    public static void removeActivity(Activity activity){
        activites.remove(activity);
    }

    public static void finishAll(){
        for (Activity activity : activites){
            if (!activity.isFinishing()){
                activity.finish();;
            }
        }
        activites.clear();
    }
}
