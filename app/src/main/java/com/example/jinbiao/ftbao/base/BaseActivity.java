package com.example.jinbiao.ftbao.base;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.jinbiao.ftbao.utils.ActivityCollector;
import com.example.jinbiao.ftbao.utils.ToastUtils;

/**
 * Created by CHEN_ on 2017/6/30.
 */

public class BaseActivity extends AppCompatActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        ActivityCollector.addActivity(this);
        ToastUtils.toastShort(getBaseContext(),getClass().getSimpleName());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
}
