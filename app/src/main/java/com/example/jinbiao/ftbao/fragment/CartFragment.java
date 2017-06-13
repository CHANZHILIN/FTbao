package com.example.jinbiao.ftbao.fragment;

import android.app.FragmentManager;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.jinbiao.ftbao.R;
import com.example.jinbiao.ftbao.adapter.CartReCycleViewAdapter;
import com.example.jinbiao.ftbao.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;


public class CartFragment extends BaseFragment {

    @BindView(R.id.recycler_view_cart)
    RecyclerView mRecyclerViewCart;
    private LinearLayoutManager mLinearLayoutManager;
    private FragmentManager mFragmentManager;

    @Override
    public CartFragment newInstance() {
        return new CartFragment();
    }

    @Override
    protected void initView(View view) {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, null);
        //先绑定ButterKnife 要不然不能用
        ButterKnife.bind(this, view);
        initView(view);
        initAdapter();
        return view;
    }
    public void initAdapter(){
        mLinearLayoutManager = new LinearLayoutManager(this.getActivity());
        mRecyclerViewCart.setLayoutManager(mLinearLayoutManager);
        //设置高度固定，提高性能
        mRecyclerViewCart.setHasFixedSize(true);
        mFragmentManager = this.getActivity().getFragmentManager();
        //设置适配器
        mRecyclerViewCart.setAdapter(new CartReCycleViewAdapter());

    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
