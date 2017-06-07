package com.example.jinbiao.ftbao.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jinbiao.ftbao.R;
import com.example.jinbiao.ftbao.base.BaseFragment;

import butterknife.ButterKnife;


public class CartFragment extends BaseFragment {

    @Override
    public CartFragment newInstance(){
        return new CartFragment();
    }

    @Override
    protected void initView(View view) {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_cart,null);
        //先绑定ButterKnife 要不然不能用
        ButterKnife.bind(this,view);
        initView(view);
        return view;
    }
}
