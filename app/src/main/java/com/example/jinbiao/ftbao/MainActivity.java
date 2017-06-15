package com.example.jinbiao.ftbao;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioGroup;

import com.example.jinbiao.ftbao.fragment.AccountFragment;
import com.example.jinbiao.ftbao.fragment.CartFragment;
import com.example.jinbiao.ftbao.fragment.DetailsPageFragment;
import com.example.jinbiao.ftbao.fragment.DiscoveryFragment;
import com.example.jinbiao.ftbao.fragment.HomeFragment;
import com.example.jinbiao.ftbao.utils.FragmentUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.rg)
    RadioGroup radioGroup;
    FragmentManager fragmentManager = this.getSupportFragmentManager();
    HomeFragment homeFragment;
    DiscoveryFragment discoveryFragment;
    CartFragment cartFragment;
//    AccountFragment accountFragment;
    DetailsPageFragment detailsPageFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //绑定ButterKnife
        ButterKnife.bind(this);
        initView();
        homeFragment = (HomeFragment) FragmentUtils.loadFragment(homeFragment, HomeFragment.class, fragmentManager, R.id.content_main);



    }

    private void initView() {
        radioGroup.setOnCheckedChangeListener(new RadioGroupListerner());
    }

    class RadioGroupListerner implements RadioGroup.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
            switch (checkedId) {
                case R.id.rb_home:
                    homeFragment = (HomeFragment) FragmentUtils.loadFragment(homeFragment, HomeFragment.class, fragmentManager, R.id.content_main);
                    break;
                case R.id.rb_discovery:
                    discoveryFragment = (DiscoveryFragment) FragmentUtils.loadFragment(discoveryFragment, DiscoveryFragment.class, fragmentManager, R.id.content_main);
                    break;
                case R.id.rb_cart:
                    cartFragment = (CartFragment) FragmentUtils.loadFragment(cartFragment, CartFragment.class, fragmentManager, R.id.content_main);
                    break;
            /*    case R.id.rb_account:
                    accountFragment = (AccountFragment) FragmentUtils.loadFragment(accountFragment, AccountFragment.class, fragmentManager, R.id.content_main);
                    break;*/
                case R.id.rb_account:
                    detailsPageFragment = (DetailsPageFragment) FragmentUtils.loadFragment(detailsPageFragment, DetailsPageFragment.class, fragmentManager, R.id.content_main);
                    break;
            }
        }
    }
}