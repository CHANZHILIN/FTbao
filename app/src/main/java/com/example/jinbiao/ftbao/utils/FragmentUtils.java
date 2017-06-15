package com.example.jinbiao.ftbao.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017-6-5.
 */

public class FragmentUtils {

    static List<Fragment> fragmentList=new ArrayList<>();
    private static final String TAG = "FragmentUtils";
    /**
     * 实现 当Fragment 不存在就new一个 Fragment 存在就show
     * @param fragment
     * @param FragmentClass
     * @param fragmentManager
     * @param layoutid
     * @return
     */
    public static Fragment loadFragment(Fragment fragment, Class<? extends Fragment> FragmentClass, FragmentManager fragmentManager, int layoutid) {
        //先把所有fragment隐藏掉
        hideAllFragment(fragmentManager);
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        if(fragment==null){
            try {
                fragment=FragmentClass.newInstance();
                fragmentList.add(fragment);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            fragmentTransaction.add(layoutid,fragment);

        }else
            fragmentTransaction.show(fragment);
        fragmentTransaction.commit();
        return fragment;
    }

    public static void hideAllFragment(FragmentManager fragmentManager){
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        for (int i=0;i<fragmentList.size();i++){
            Fragment fragment=fragmentList.get(i);
            fragmentTransaction.hide(fragment);
        }
        fragmentTransaction.commit();
    }
}
