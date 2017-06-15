package com.example.jinbiao.ftbao.adapter;

import android.app.FragmentManager;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jinbiao.ftbao.R;
import com.example.jinbiao.ftbao.bean.CartData;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by CHEN_ on 2017/6/12.
 */

public class CartReCycleViewAdapter extends RecyclerView.Adapter {



    private List<CartData.DataBean> cartDatas;

    private Context context;
    private FragmentManager fragmentManager;
    private LayoutInflater mLayoutInflater;


    /**
     * 添加数据
     */
    public void setCartDatas(List<CartData.DataBean> cartDatas) {
        this.cartDatas.addAll(cartDatas);
    }

    public CartReCycleViewAdapter(Context context, FragmentManager fragmentManager, List<CartData.DataBean> list) {
        this.context = context;
       /* cartDatas = list;*/
        this.fragmentManager = fragmentManager;
        this.mLayoutInflater = LayoutInflater.from(context);
        Toast.makeText(context, ""+cartDatas.get(0).getStorename(),Toast.LENGTH_LONG).show();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cycleview_cart, parent, false);
        cartViewHolder viewHolder = new cartViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((cartViewHolder)holder).mTvStorename.setText(cartDatas.get(position).getStorename());
    }

    @Override
    public int getItemCount() {
        return cartDatas==null ? 0 : cartDatas.size();
    }


    public static class cartViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.cb_storename)
        CheckBox mCbStorename;
        @BindView(R.id.cb_goods)
        CheckBox mCbGoods;
        @BindView(R.id.iv_goodsimg)
        ImageView mIvGoodsimg;
        @BindView(R.id.tv_storename)
        TextView mTvStorename;
        @BindView(R.id.tv_goodskind)
        TextView mTvGoodskind;
        @BindView(R.id.tv_goodsprice)
        TextView mTvGoodsprice;
        @BindView(R.id.tv_goodscount)
        TextView mTvGoodscount;

        public cartViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
