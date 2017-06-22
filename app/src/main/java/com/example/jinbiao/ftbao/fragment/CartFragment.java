package com.example.jinbiao.ftbao.fragment;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jinbiao.ftbao.R;
import com.example.jinbiao.ftbao.adapter.CartReCycleViewAdapter;
import com.example.jinbiao.ftbao.base.BaseFragment;
import com.example.jinbiao.ftbao.bean.CartData;
import com.example.jinbiao.ftbao.eventbus.PriceEvent;
import com.example.jinbiao.ftbao.interFace.ICart;
import com.example.jinbiao.ftbao.utils.Constant;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class CartFragment extends BaseFragment {
    /**
     * 需要一个uid(用户id)
     */
    public int uid = 2;
    private double num;
    private boolean editIsCheck = false;

    private static final String TAG = "CartFragment";

    @BindView(R.id.recycler_view_cart)
    RecyclerView mRecyclerViewCart;
    @BindView(R.id.edit_all)
    Button mEditAll;
    @BindView(R.id.checkBox)
    CheckBox mCheckBox;
    @BindView(R.id.allnum)
    TextView mAllnum;
//    private LinearLayout mLinearLayout;
    private LinearLayoutManager mLinearLayoutManager;
    private FragmentManager mFragmentManager;
    private CartReCycleViewAdapter mCartReCycleViewAdapter;

    private boolean flag = false;
    private boolean isSelectedAll;

    public List<CartData.DataBean> cart = new ArrayList<>();


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

        //获取购物车数据
        getCartData();
        initAdapter();
        EventBus.getDefault().register(this);
//        mLinearLayout = (LinearLayout) this.getActivity().findViewById(R.id.ll_delete);


        //全选的CheckBox的监听事件
        mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                   /* calculator();   //计算总价格
                    mAllnum.setText("￥:"+ num);*/
                    mCartReCycleViewAdapter.setSelectedAll(true);

                }else {
                    num = 0.0;

                   // mAllnum.setText("￥:"+ num);
                    mCartReCycleViewAdapter.setSelectedAll(false);
                }
               // EventBus.getDefault().post(new PriceEvent(num));   //发送到事件总线
                mCartReCycleViewAdapter.notifyDataSetChanged();
            }
        });
        return view;
    }

    public void initAdapter() {
        mLinearLayoutManager = new LinearLayoutManager(this.getActivity());
        mRecyclerViewCart.setLayoutManager(mLinearLayoutManager);
        //设置高度固定，提高性能
        mRecyclerViewCart.setHasFixedSize(true);
        mFragmentManager = this.getActivity().getFragmentManager();

        mCartReCycleViewAdapter = new CartReCycleViewAdapter(getActivity(), mFragmentManager, cart);
        //设置适配器
        mRecyclerViewCart.setAdapter(mCartReCycleViewAdapter);
        mCartReCycleViewAdapter.notifyDataSetChanged();

        //设置recycleView的点击事件
        mCartReCycleViewAdapter.setOnItemClickListener(new CartReCycleViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getContext(),"点击了第" + (position+1) + "行",Toast.LENGTH_SHORT).show();

            }
        });




    }
    @Subscribe
    public void onEventMainThread(PriceEvent event){
        this.num = event.getPrice();
        /*this.isSelectedAll = event.getSelectedAll();
        if (!isSelectedAll){
            mCheckBox.setChecked(false);
        }*/
        /*num = Double.parseDouble(String.format("%.2f",num));*/
        Log.e(TAG, "onEventMainThread: "+ num + "元" );
       mAllnum.setText("￥:"+ num);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this); //反注册EventBus
    }

    public void getCartData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ICart carts = retrofit.create(ICart.class);
        Call<CartData> call = carts.getCartData(uid);   //根据uid（用户id）去查询购物车
        call.enqueue(new Callback<CartData>() {
            @Override
            public void onResponse(Call<CartData> call, Response<CartData> response) {

                //cartDatas.addAll(response.body().getData());
                cart = response.body().getData();
                mCartReCycleViewAdapter.setCartDatas(cart);
                mCartReCycleViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<CartData> call, Throwable t) {
                Toast.makeText(getContext(), "获取数据失败", Toast.LENGTH_SHORT).show();
            }
        });
    }


    /**
     * 点击事件
     * @param view
     */
    @OnClick({R.id.edit_all})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.edit_all:
                if (editIsCheck) {
                    mCartReCycleViewAdapter.setFlag(false);
                    editIsCheck = false;
                    mCartReCycleViewAdapter.notifyDataSetChanged();
                }else {
                    mCartReCycleViewAdapter.setFlag(true);
                    editIsCheck = true;
                    mCartReCycleViewAdapter.notifyDataSetChanged();
                }
                break;
        }
    }

    /**
     * 算出总价钱
     */
    public void calculator(){
        for (int i = 0; i < cart.size(); i++){
            String data = cart.get(i).getPrice();
            double price = Double.parseDouble(data.substring(1));
            int count = cart.get(i).getCount();
            num += price*count;
            num = Double.parseDouble(String.format("%.2f",num));
        }
    }


}
