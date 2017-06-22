package com.example.jinbiao.ftbao.adapter;

import android.app.Dialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jinbiao.ftbao.R;
import com.example.jinbiao.ftbao.bean.CartData;
import com.example.jinbiao.ftbao.eventbus.PriceEvent;
import com.example.jinbiao.ftbao.fragment.CartFragment;
import com.example.jinbiao.ftbao.interFace.IDeleteCart;
import com.example.jinbiao.ftbao.utils.Constant;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by CHEN_ on 2017/6/12.
 */

public class CartReCycleViewAdapter extends RecyclerView.Adapter implements View.OnClickListener {


    private OnItemClickListener mOnItemClickListener = null;


    private List<CartData.DataBean> cartDatas;

    private Context context;
    private FragmentManager fragmentManager;
    private LayoutInflater mLayoutInflater;
    private boolean flag = false;
    private boolean isSelectedAll = false;
    private static double allCount;
    private static final String TAG = "CartReCycleViewAdapter";
    private CartFragment mCartFragment = new CartFragment();

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public void setSelectedAll(boolean isSelectedAll) {
        this.isSelectedAll = isSelectedAll;
    }

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
        View view = mLayoutInflater.inflate(R.layout.cycleview_cart, parent, false);
        cartViewHolder viewHolder = new cartViewHolder(view);
        //将创建的View注册点击事件
        view.setOnClickListener(this);
        return viewHolder;
       /* return new cartViewHolder(mLayoutInflater.inflate(R.layout.cycleview_cart, parent, false));*/
    }

    @Subscribe
    public void onEventMainThread(PriceEvent event) {
        allCount = event.getPrice();
        allCount = Double.parseDouble(String.format("%.2f", allCount));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        //将position保存在itemView的Tag中，便于点击时进行获取
        holder.itemView.setTag(position);
        Log.e(TAG, "总价: " + allCount + "是吧");

        if (flag) ((cartViewHolder) holder).mLlDelete.setVisibility(View.VISIBLE);
        else ((cartViewHolder) holder).mLlDelete.setVisibility(View.GONE);
        //是否为全选，若是则全部选中，否则
        if (isSelectedAll) ((cartViewHolder) holder).mCbStorename.setChecked(true);
        else ((cartViewHolder) holder).mCbStorename.setChecked(false);
        ((cartViewHolder) holder).mCbStorename.setText(cartDatas.get(position).getStorename());
        // imageUrl代表图片的URL地址，imageView代表承载图片的IMAGEVIEW控件
        ImageLoader.getInstance().displayImage(cartDatas.get(position).getImgurl(), ((cartViewHolder) holder).mIvGoodimg);
        ((cartViewHolder) holder).mTvGoodname.setText(cartDatas.get(position).getProinfo());
        ((cartViewHolder) holder).mTvGoodcount.setText("数量：x" + cartDatas.get(position).getCount());
        ((cartViewHolder) holder).mTvGoodprice.setText(cartDatas.get(position).getPrice());
        ((cartViewHolder) holder).mTvGoodkind.setText(cartDatas.get(position).getProact());

        //点击商品的CheckBox
        ((cartViewHolder) holder).mCbGood.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // calculate(allCount,Double.parseDouble(cartDatas.get(position).getPrice().substring(1)) * cartDatas.get(position).getCount());
                    //加上选中的价格
                    Log.e(TAG, "onCheckedChanged: 单价" + cartDatas.get(position).getPrice().substring(1));
                    Log.e(TAG, "onCheckedChanged: 数量" + cartDatas.get(position).getCount());
                    allCount += Double.parseDouble(cartDatas.get(position).getPrice().substring(1)) * cartDatas.get(position).getCount();
//                    allCount=Double.parseDouble(String.format("%.2f",allCount));
                    EventBus.getDefault().post(new PriceEvent(allCount));   //发送到事件总线
                    Log.e(TAG, "onCheckedChanged:总价 " + allCount + "选中");
                } else {
                    Log.e(TAG, "onCheckedChanged: 单价" + cartDatas.get(position).getPrice().substring(1));
                    Log.e(TAG, "onCheckedChanged: 数量" + cartDatas.get(position).getCount());
                    //减去选中的价格
                    allCount -= Double.parseDouble(cartDatas.get(position).getPrice().substring(1)) * cartDatas.get(position).getCount();
//                    allCount=Double.parseDouble(String.format("%.2f",allCount));
                    EventBus.getDefault().post(new PriceEvent(allCount));   //发送到事件总线
                    Log.e(TAG, "onCheckedChanged: 总价" + allCount + "未选中");
                }
            }
        });
        //选择店铺的CheckBox的点击事件
        ((cartViewHolder) holder).mCbStorename.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    ((cartViewHolder) holder).mCbGood.setChecked(true);

                } else {
//                    EventBus.getDefault().post(new PriceEvent(false));

                    ((cartViewHolder) holder).mCbGood.setChecked(false);

                }
            }
        });

        ((cartViewHolder) holder).mBtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(position);

            }
        });


    }

    /**
     * 删除购物车
     *
     * @param position
     */
    public void remove(int position) {
        cartDatas.remove(position);
        notifyItemRemoved(position);
        CartReCycleViewAdapter.this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return cartDatas.size();
    }

    /**
     * 点击事件转移给外面的调用者
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //用getTap方法获取position
            mOnItemClickListener.onItemClick(v, (int) v.getTag());
        }
    }

    /**
     * 将点击方法暴露给调用者
     *
     * @param listener
     */
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }


    public class cartViewHolder extends RecyclerView.ViewHolder {

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
        @BindView(R.id.ll_delete)
        LinearLayout mLlDelete;
        @BindView(R.id.cb_good)
        CheckBox mCbGood;
        @BindView(R.id.cb_storename)
        CheckBox mCbStorename;
        @BindView(R.id.btn_delete)
        Button mBtnDelete;

        public cartViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public static interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

/*    public double calculate(double allCount, double changeCount) {
        allCount = allCount + changeCount;
        return allCount;
    }*/

    /**
     * 弹出是否确认删除的对话框
     *
     * @param position
     */
    private void showDialog(final int position) {
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        final AlertDialog.Builder confirmDialog =
                new AlertDialog.Builder(context);
        confirmDialog.setIcon(R.drawable.tm_dialog_default_icon);
        confirmDialog.setTitle("确认删除");
        confirmDialog.setMessage("是否要删除选中的商品？");
        confirmDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //连接网络删数据
                        int cid = cartDatas.get(position).getCid();
                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(Constant.BASE_URL)
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();
                        IDeleteCart deleteCart = retrofit.create(IDeleteCart.class);
                        Call<CartData> call = deleteCart.deleteCartData(cid);   //根据cid去删除购物车
                        call.enqueue(new Callback<CartData>() {
                            @Override
                            public void onResponse(Call<CartData> call, Response<CartData> response) {
//                        mCartReCycleViewAdapter.notifyDataSetChanged();
                                String isDelete = response.body().getMessage().toString();
                                if (isDelete.equals("success")) {
                                    remove(position);    //如果删除成功，则移除这条购物车信息
                                } else {
                                    Toast.makeText(context, "删除失败", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<CartData> call, Throwable t) {
                                Toast.makeText(context, "连接网络失败", Toast.LENGTH_SHORT).show();

                            }
                        });
                    }
                });
        confirmDialog.setNegativeButton("关闭",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        // 显示
        confirmDialog.show();
    }

}
