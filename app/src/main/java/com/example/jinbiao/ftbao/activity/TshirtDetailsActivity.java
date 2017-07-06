package com.example.jinbiao.ftbao.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.jinbiao.ftbao.R;
import com.example.jinbiao.ftbao.adapter.DetailsAdpater;
import com.example.jinbiao.ftbao.base.BaseActivity;
import com.example.jinbiao.ftbao.bean.TshirtDetail;
import com.example.jinbiao.ftbao.bean.TshirtDetailData;
import com.example.jinbiao.ftbao.fragment.DetailsPageFragment;
import com.example.jinbiao.ftbao.interFace.ITshirtDetail;
import com.example.jinbiao.ftbao.utils.ActivityCollector;
import com.example.jinbiao.ftbao.utils.Constant;
import com.example.jinbiao.ftbao.utils.ToastUtils;
import com.example.jinbiao.ftbao.view.MyPopUpWindow;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TshirtDetailsActivity extends BaseActivity {
    @BindView(R.id.detail_back)
    TextView mDetailBack;
    @BindView(R.id.details_content)
    FrameLayout mDetailsContent;
    @BindView(R.id.details_content_Customerservice)
    ImageView mDetailsContentCustomerservice;
    @BindView(R.id.details_content_shop)
    ImageView mDetailsContentShop;
    @BindView(R.id.details_content_Collection)
    ImageView mDetailsContentCollection;
    @BindView(R.id.add_to_cart)
    Button mAddToCart;
    @BindView(R.id.buy_now)
    Button mBuyNow;
    private DetailsPageFragment detailsPageFragment;
    private FragmentManager fragmentManager = this.getSupportFragmentManager();
    private static final String TAG = "TshirtDetailsActivity";
    private List<TshirtDetail> mTshirtsDetails = new ArrayList<>();
    private static String tshirtHref;

    public static void setTshirtId(String href) {
        tshirtHref = href;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_details_page);
        ActivityCollector.addActivity(this);
        //绑定ButterKnife
        ButterKnife.bind(this);
        initView();
        mDetailBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TshirtDetailsActivity.this.finish();
            }
        });

        mAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopFormBottom(v);
            }
        });

    }

    private void initView() {
        getTshirtDetail(tshirtHref);
    }

    /**
     * 联网获取衣服详情数据
     *
     * @param href
     */
    public void getTshirtDetail(String href) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final ITshirtDetail tshirtDetail = retrofit.create(ITshirtDetail.class);
        Call<TshirtDetailData> call = tshirtDetail.getTshirtDetail(href);
        call.enqueue(new Callback<TshirtDetailData>() {
            @Override
            public void onResponse(Call<TshirtDetailData> call, Response<TshirtDetailData> response) {
                if (response.body().getMessage().equals("success")) {
                    mTshirtsDetails = response.body().getData();
                    //设置详情页面的轮播图图片
                    DetailsPageFragment.setUrl(mTshirtsDetails.get(0).getListImg());

                    DetailsAdpater.setTshirtsDetails(mTshirtsDetails);
                    //把detailsPageFragmetn视图加载进来
                    detailsPageFragment = new DetailsPageFragment();
                    fragmentManager.beginTransaction().add(R.id.details_content, detailsPageFragment).commit();

                } else {
                    ToastUtils.toastShort(getBaseContext(), "获取数据失败");
                }
            }

            @Override
            public void onFailure(Call<TshirtDetailData> call, Throwable t) {
                ToastUtils.toastShort(getBaseContext(), "联网失败，请检查网络状态");
            }
        });
    }

    public void showPopFormBottom(View view) {
        MyPopUpWindow myPopUpWindow = new MyPopUpWindow(this);
//        设置Popupwindow显示位置（从底部弹出）
        myPopUpWindow.showAtLocation(view, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        //当弹出Popupwindow时，背景变半透明
        params.alpha = 0.7f;
       getWindow().setAttributes(params);
        //设置Popupwindow关闭监听，当Popupwindow关闭，背景恢复1f
        myPopUpWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams params = getWindow().getAttributes();
                params.alpha = 1f;
                getWindow().setAttributes(params);
            }
        });
    }

}
