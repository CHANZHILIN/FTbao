package com.example.jinbiao.ftbao.adapter;

import android.app.FragmentManager;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jinbiao.ftbao.R;
import com.example.jinbiao.ftbao.bean.CartData;
import com.nostra13.universalimageloader.core.ImageLoader;

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

    private static final String TAG = "CartReCycleViewAdapter";

    public void setCartDatas(List<CartData.DataBean> cartDatas) {
//        this.cartDatas.addAll(cartDatas);
        Log.d(TAG, "setCartDatas: " + cartDatas.size());
        this.cartDatas = cartDatas;
    }

    public CartReCycleViewAdapter(Context context, FragmentManager fragmentManager, List<CartData.DataBean> list) {
        this.context = context;
        cartDatas = list;
        this.fragmentManager = fragmentManager;
        this.mLayoutInflater = LayoutInflater.from(context);
        //Toast.makeText(context, ""+cartDatas.get(0).getStorename(),Toast.LENGTH_LONG).show();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        /*View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cycleview_cart, parent, false);
        cartViewHolder viewHolder = new cartViewHolder(view);
        return viewHolder;*/
        return new cartViewHolder(mLayoutInflater.inflate(R.layout.cycleview_cart, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((cartViewHolder) holder).mCbStorename.setText(cartDatas.get(position).getStorename());
        // imageUrl代表图片的URL地址，imageView代表承载图片的IMAGEVIEW控件
        ImageLoader.getInstance().displayImage(cartDatas.get(position).getImgurl(), ((cartViewHolder) holder).mIvGoodimg);
        ((cartViewHolder) holder).mTvGoodname.setText(cartDatas.get(position).getProinfo());
        ((cartViewHolder) holder).mTvGoodcount.setText("数量：x"+cartDatas.get(position).getCount());
        ((cartViewHolder) holder).mTvGoodprice.setText(cartDatas.get(position).getPrice());
        ((cartViewHolder) holder).mTvGoodkind.setText(cartDatas.get(position).getProact());
    }

    @Override
    public int getItemCount() {
        return cartDatas.size();
    }


    public class cartViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.cb_storename)
        CheckBox mCbStorename;
        @BindView(R.id.iv_goodimg)
        ImageView mIvGoodimg;
        @BindView(R.id.tv_goodname)
        TextView mTvGoodname;
        @BindView(R.id.tv_goodkind)
        TextView mTvGoodkind;
        @BindView(R.id.tv_goodprice)
        TextView mTvGoodprice;
        @BindView(R.id.tv_count)
        TextView mTvGoodcount;

        public cartViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
