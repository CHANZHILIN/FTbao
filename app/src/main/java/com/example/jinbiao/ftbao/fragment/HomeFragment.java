package com.example.jinbiao.ftbao.fragment;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.jinbiao.ftbao.R;
import com.example.jinbiao.ftbao.adapter.ReCycleViewAdpater;
import com.example.jinbiao.ftbao.base.BaseFragment;
import com.example.jinbiao.ftbao.bean.ADInfo;
import com.example.jinbiao.ftbao.pager.cycleviewpager.CycleViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class HomeFragment extends BaseFragment {

/*
    private List<ImageView> views = new ArrayList<ImageView>();
    private List<ADInfo> infos = new ArrayList<ADInfo>();
    private CycleViewPager cycleViewPager;
*/
    private FragmentManager fragmentManager;
    private RecyclerView.LayoutManager mLayoutManager;
    private ReCycleViewAdpater mAdapter;
    @BindView(R.id.my_recycler_view)
    RecyclerView recyclerView;
    private String[] imageUrls = {"http://img.taodiantong.cn/v55183/infoimg/2013-07/130720115322ky.jpg",
            "http://pic30.nipic.com/20130626/8174275_085522448172_2.jpg",
            "http://pic18.nipic.com/20111215/577405_080531548148_2.jpg",
            "http://pic15.nipic.com/20110722/2912365_092519919000_2.jpg",
            "http://pic.58pic.com/58pic/12/64/27/55U58PICrdX.jpg"};
    private String[] images = {"http://img.taodiantong.cn/v55183/infoimg/2013-07/130720115322ky.jpg",
            "http://pic30.nipic.com/20130626/8174275_085522448172_2.jpg",
            "http://pic18.nipic.com/20111215/577405_080531548148_2.jpg",
            "http://pic15.nipic.com/20110722/2912365_092519919000_2.jpg",
            "http://pic30.nipic.com/20130626/8174275_085522448172_2.jpg",
            "http://pic18.nipic.com/20111215/577405_080531548148_2.jpg",
            "http://pic15.nipic.com/20110722/2912365_092519919000_2.jpg",
            "http://pic.58pic.com/58pic/12/64/27/55U58PICrdX.jpg"};
    @Override
    public HomeFragment newInstance(){
        return new HomeFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_home,null);
        //先绑定ButterKnife 要不然不能用
        ButterKnife.bind(this,view);
        initView(view);
        initAdapter();
        return view;
    }

    @Override
    protected void initView(View view) {
//        cycleViewPager = (CycleViewPager) getActivity().getFragmentManager().findFragmentById(R.id.fragment_cycle_viewpager_content);
//        cycleViewPagerOn(cycleViewPager);
    }

    private void initAdapter() {
        //创建默认的线性LayoutManager
//        mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        recyclerView.setHasFixedSize(true);
        fragmentManager=getActivity().getFragmentManager();
        //创建并设置Adapter
        mAdapter = new ReCycleViewAdpater(getActivity(),fragmentManager,images);
        recyclerView.setAdapter(mAdapter);
    }


}
