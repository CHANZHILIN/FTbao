package com.example.jinbiao.ftbao.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.jinbiao.ftbao.R;
import com.example.jinbiao.ftbao.base.BaseActivity;
import com.example.jinbiao.ftbao.fragment.DetailsPageFragment;
import com.example.jinbiao.ftbao.utils.ActivityCollector;

import butterknife.ButterKnife;

public class TshirtDetailsActivity extends BaseActivity {
    private DetailsPageFragment detailsPageFragment;
    private FragmentManager fragmentManager = this.getSupportFragmentManager();
    private static final String TAG = "TshirtDetailsActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_details_page);
        ActivityCollector.addActivity(this);
        //绑定ButterKnife
        ButterKnife.bind(this);
        initView();
        detailsPageFragment =new DetailsPageFragment();
        fragmentManager.beginTransaction().add(R.id.details_content,detailsPageFragment).commit();
    }

    private void initView() {

    }

}
