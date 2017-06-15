package com.example.jinbiao.ftbao.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.PopupWindow;

import com.example.jinbiao.ftbao.R;

/**
 * Created by shushu on 2017/6/15.
 */

public class Details_PopupWindow extends PopupWindow {


    private View mView;
    public Button btnSaveProject, btnAbandonProject, btnCancelProject;

    public Details_PopupWindow(Activity context
                                    ) {
        super(context);
       // Log.i(TAG, "Details_PopupWindow 方法已被调用");, OnClickListener itemsOnClick

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mView = inflater.inflate(R.layout.details_buy_pop, null);

//
//        btnSaveProject.setOnClickListener(itemsOnClick);
//        btnAbandonProject.setOnClickListener(itemsOnClick);


        //设置PopupWindow的View
        this.setContentView(mView);
        //设置PopupWindow弹出窗体的宽
        this.setWidth(LayoutParams.MATCH_PARENT);
        //设置PopupWindow弹出窗体的高
        this.setHeight(LayoutParams.WRAP_CONTENT);
        //设置PopupWindow弹出窗体可点击
        this.setFocusable(true);
        //设置SelectPicPopupWindow弹出窗体动画效果
         //this.setAnimationStyle(R.style);
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
    }

}
