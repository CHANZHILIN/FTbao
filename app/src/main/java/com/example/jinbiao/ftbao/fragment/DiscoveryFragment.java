package com.example.jinbiao.ftbao.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jinbiao.ftbao.R;
import com.example.jinbiao.ftbao.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;


public class DiscoveryFragment extends BaseFragment {

    @Override
    public DiscoveryFragment newInstance(){
        return new DiscoveryFragment();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_discovery,null);
        //先绑定ButterKnife 要不然不能用
        ButterKnife.bind(this,view);
        initView(view);
        return view;
    }

    @Override
    protected void initView(View view) {
    }
}
