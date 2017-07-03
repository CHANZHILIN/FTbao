package com.example.jinbiao.ftbao.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.jinbiao.ftbao.R;
import com.example.jinbiao.ftbao.activity.LoginActivity;
import com.example.jinbiao.ftbao.activity.SettingActivity;
import com.example.jinbiao.ftbao.adapter.CartReCycleViewAdapter;
import com.example.jinbiao.ftbao.base.BaseFragment;
import com.example.jinbiao.ftbao.bean.User;
import com.example.jinbiao.ftbao.bean.UserData;
import com.example.jinbiao.ftbao.interFace.IUser;
import com.example.jinbiao.ftbao.utils.Constant;
import com.example.jinbiao.ftbao.utils.LogUtils;
import com.example.jinbiao.ftbao.utils.ToastUtils;
import com.makeramen.roundedimageview.RoundedImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class AccountFragment extends BaseFragment {

    @BindView(R.id.user_image)
    RoundedImageView mUserImage;
    public List<User> mUserDatas = new ArrayList<>();
    public static String uuid = "";
    public static boolean isLogin = false;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.accountCollapsingTollbar)
    CollapsingToolbarLayout mAccountCollapsingTollbar;

    @Override
    public AccountFragment newInstance() {
        return new AccountFragment();
    }


    @Override
    protected void initView(View view) {
        SharedPreferences sp = getActivity().getSharedPreferences("userinfo", Context.MODE_PRIVATE);
       // boolean autoLogin = sp.getBoolean("autologin",false);
      // uuid = getActivity().getIntent().getStringExtra("uuid");
//        userName = sp.getString("username", "");


             uuid = sp.getString("key", "");
        if (!uuid.equals(""))
            isLogin = true;

        LogUtils.i("AccountFragment", uuid);

            //获取用户数据
            getWebUserData(uuid);




    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, null);
        //先绑定ButterKnife 要不然不能用
        ButterKnife.bind(this, view);
        initView(view);
        //判断是否登录  登录则打开设置页面  否则打开登录页面
        mAccountCollapsingTollbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLogin) {
                    startActivity(new Intent(getActivity(), SettingActivity.class));
                }else {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }
            }
        });
        mToolbar.setNavigationIcon(R.drawable.setting);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLogin) {
                    startActivity(new Intent(getActivity(), SettingActivity.class));
                }else {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }
            }
        });

        mUserImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLogin) {
                    startActivity(new Intent(getActivity(), SettingActivity.class));
                }else {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }
            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    /**
     * 联网获取用户个人信息
     *
     * @param uuid
     */
    private void getWebUserData(String uuid) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        IUser user = retrofit.create(IUser.class);
        Call<UserData> call = user.getUserData(uuid);   //根据uuid查询用户信息
        call.enqueue(new Callback<UserData>() {
            @Override
            public void onResponse(Call<UserData> call, Response<UserData> response) {
                mUserDatas = response.body().getData();
                //设置用户名和头像   如果登录则显示头像  否则显示网络上的一张图片
                Picasso.with(getContext()).load(isLogin ? mUserDatas.get(0).getImage(): "http://d9.yihaodianimg.com/N05/M06/E4/D6/CgQI0lU3u1OAVPlOAAG4QCJE9VI55700_230*322.jpg").resize(80, 80).into(mUserImage);
                mAccountCollapsingTollbar.setTitle(isLogin ? mUserDatas.get(0).getUsername() : "请登录");
                SettingActivity.setUserData(mUserDatas);
            }

            @Override
            public void onFailure(Call<UserData> call, Throwable t) {
                Toast.makeText(getContext(), "获取数据失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        //每次onResume都更新一下数据界面
        initView(getView());
       // getWebUserData(uuid);
    }
}
