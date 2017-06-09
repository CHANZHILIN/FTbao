package com.example.jinbiao.ftbao.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jinbiao.ftbao.R;
import com.example.jinbiao.ftbao.activity.LoginActivity;
import com.example.jinbiao.ftbao.activity.RegisterActivity;
import com.example.jinbiao.ftbao.base.BaseFragment;

import butterknife.ButterKnife;


public class AccountFragment extends BaseFragment {
    TextView username;


    @Override
    public AccountFragment newInstance(){
        return new AccountFragment();
    }

    @Override
    protected void initView(View view) {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_account,null);
        username= (TextView) view.findViewById(R.id.user_name);
        username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(getActivity().getApplicationContext(),LoginActivity.class);
                startActivity(intent);
            }
        });

        //先绑定ButterKnife 要不然不能用
        ButterKnife.bind(this,view);
        initView(view);
        return view;
    }
}
