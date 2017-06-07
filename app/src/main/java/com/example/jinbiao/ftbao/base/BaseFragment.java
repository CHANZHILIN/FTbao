package com.example.jinbiao.ftbao.base;

import android.support.v4.app.Fragment;
import android.view.View;

/**
 * Created by Administrator on 2017-6-5.
 */

public abstract class BaseFragment extends Fragment{
    protected abstract Fragment newInstance();
    protected abstract void initView(View view);
}
