package com.example.jinbiao.ftbao.adapter;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jinbiao.ftbao.R;
import com.example.jinbiao.ftbao.activity.TshirtDetailsActivity;
import com.example.jinbiao.ftbao.bean.ADInfo;
import com.example.jinbiao.ftbao.bean.Tshirt;
import com.example.jinbiao.ftbao.pager.cycleviewpager.CycleViewPager;
import com.example.jinbiao.ftbao.utils.ViewFactory;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReCycleViewAdpater extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ImageView> views = new ArrayList<ImageView>();
    private List<ADInfo> infos = new ArrayList<ADInfo>();
    private LayoutInflater mLayoutInflater;
    private Context context;
    private android.support.v4.app.FragmentManager fragmentManager;
    private FragmentManager fragmentManager_banner;
    private List<List<Tshirt>> tshirts;
    private String[] bannerlist;
    public void setTshirts(List<List<Tshirt>> tshirts){
        this.tshirts.addAll(tshirts);
    }
    //建立枚举 3个item 类型
    public enum ITEM_TYPE {
        ITEM1,
        ITEM2,
        ITEM3
    }

    public ReCycleViewAdpater(Context context, FragmentManager fragmentManager_banner,android.support.v4.app.FragmentManager fragmentManager, List<List<Tshirt>> tshirts, String[] bannerlist) {
        this.tshirts = tshirts;
        this.bannerlist = bannerlist;
        this.context = context;
        this.fragmentManager_banner=fragmentManager_banner;
        this.fragmentManager = fragmentManager;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//加载Item View的时候根据不同TYPE加载不同的布局
        if (viewType == ITEM_TYPE.ITEM1.ordinal()) {
            return new Item1ViewHolder(mLayoutInflater.inflate(R.layout.cycle_items_cvp, parent, false));
        } else if (viewType == ITEM_TYPE.ITEM2.ordinal()) {
            return new Item2ViewHolder(mLayoutInflater.inflate(R.layout.cycle_item_image, parent, false));
        } else {
            return new Item3ViewHolder(mLayoutInflater.inflate(R.layout.cycleview_items, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof Item1ViewHolder) {

        } else if (holder instanceof Item2ViewHolder) {
//            ((Item2ViewHolder) holder).mTextView.setText(titles[position]);

        } else if (holder instanceof Item3ViewHolder) {
            List<Tshirt>list=tshirts.get(position - 2);
            ImageLoader.getInstance().displayImage(list.get(0).getImgurl(), ((Item3ViewHolder) holder).imgLeft); // imageUrl代表图片的URL地址，imageView代表承载图片的IMAGEVIEW控件
           ((Item3ViewHolder) holder).imgLeft.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
//                   tshirtDetailsFragment = (TshirtDetailsFragment) FragmentUtils.loadFragment(tshirtDetailsFragment, TshirtDetailsFragment.class, fragmentManager, R.id.content_main);
                    context.startActivity(new Intent(context, TshirtDetailsActivity.class));
               }
           });
            ((Item3ViewHolder) holder).business.setText(list.get(0).getStorename());
            ((Item3ViewHolder) holder).introduce.setText(list.get(0).getProinfo());
            ((Item3ViewHolder) holder).sale.setText(list.get(0).getProact()==""?"暂无促销信息":list.get(0).getProact());
            ((Item3ViewHolder) holder).mesCount.setText(list.get(0).getCommentcount().substring(1,list.get(0).getCommentcount().length()));
            if(!TextUtils.isEmpty(list.get(0).getGoodcomment())){
                ((Item3ViewHolder) holder).digCount.setText(list.get(0).getGoodcomment().substring(1,list.get(0).getGoodcomment().length()));
            }else
                ((Item3ViewHolder) holder).digCount.setText("50%");
            ((Item3ViewHolder) holder).price.setText(list.get(0).getPrice());
            if(list.size()==2){
                ImageLoader.getInstance().displayImage(list.get(1).getImgurl(), ((Item3ViewHolder) holder).imgRight); // imageUrl代表图片的URL地址，imageView代表承载图片的IMAGEVIEW控件
                ((Item3ViewHolder) holder).imgRight.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        tshirtDetailsFragment = (TshirtDetailsFragment) FragmentUtils.loadFragment(tshirtDetailsFragment, TshirtDetailsFragment.class, fragmentManager, R.id.content_main);
                        context.startActivity(new Intent(context, TshirtDetailsActivity.class));
                    }
                });
                ((Item3ViewHolder) holder).businessOther.setText(list.get(1).getStorename());
                ((Item3ViewHolder) holder).introduceOther.setText(list.get(1).getProinfo());
                ((Item3ViewHolder) holder).saleOther.setText(list.get(1).getProact()==""?"暂无促销信息":list.get(1).getProact());
                ((Item3ViewHolder) holder).mesCountOther.setText(list.get(1).getCommentcount().substring(1,list.get(1).getCommentcount().length()));
                if(!TextUtils.isEmpty(list.get(1).getGoodcomment()))
                    ((Item3ViewHolder) holder).digCountOther.setText(list.get(1).getGoodcomment().substring(1,list.get(1).getGoodcomment().length()));
                else
                    ((Item3ViewHolder) holder).digCountOther.setText("50%");
                ((Item3ViewHolder) holder).priceOther.setText(list.get(1).getPrice());
            }
        }
    }

    //设置ITEM类型，可以自由发挥，这里设置item 第一个position显示banner 第二个显示圆形点击图像按钮，第三个就是一个列表
    @Override
    public int getItemViewType(int position) {
    //Enum类提供了一个ordinal()方法，返回枚举类型的序数，这里ITEM_TYPE.ITEM1.ordinal()代表0， ITEM_TYPE.ITEM2.ordinal()代表1
        if (position == 0) return ITEM_TYPE.ITEM1.ordinal();
        if (position == 1) return ITEM_TYPE.ITEM2.ordinal();
        return ITEM_TYPE.ITEM3.ordinal();
    }

    @Override
    public int getItemCount() {
        return tshirts == null ? 0 : tshirts.size() + 2;
    }

    //item1 的ViewHolder
    public class Item1ViewHolder extends RecyclerView.ViewHolder {
        CycleViewPager cycleViewPager;

        public Item1ViewHolder(View itemView) {
            super(itemView);
            cycleViewPager = (CycleViewPager) fragmentManager_banner.findFragmentById(R.id.fragment_cycle_viewpager_content);
            cycleViewPagerOn(cycleViewPager);
        }
    }

    //item2 的ViewHolder
    public static class Item2ViewHolder extends RecyclerView.ViewHolder {
        public Item2ViewHolder(View itemView) {
            super(itemView);
            //先与viewholder绑定才能找到view
            ButterKnife.bind(this, itemView);
        }
    }

    //item3 的ViewHolder
    public static class Item3ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_left)
        ImageView imgLeft;
        @BindView(R.id.business)
        TextView business;
        @BindView(R.id.business_logo)
        ImageView businessLogo;
        @BindView(R.id.introduce)
        TextView introduce;
        @BindView(R.id.sale_logo)
        ImageView saleLogo;
        @BindView(R.id.sale)
        TextView sale;
        @BindView(R.id.price)
        TextView price;
        @BindView(R.id.mess_logo)
        ImageView messLogo;
        @BindView(R.id.mes_count)
        TextView mesCount;
        @BindView(R.id.dig_logo)
        ImageView digLogo;
        @BindView(R.id.dig_count)
        TextView digCount;
        @BindView(R.id.img_right)
        ImageView imgRight;
        @BindView(R.id.business_other)
        TextView businessOther;
        @BindView(R.id.business_logo_other)
        ImageView businessLogoOther;
        @BindView(R.id.introduce_other)
        TextView introduceOther;
        @BindView(R.id.sale_logo_other)
        ImageView saleLogoOther;
        @BindView(R.id.sale_other)
        TextView saleOther;
        @BindView(R.id.price_other)
        TextView priceOther;
        @BindView(R.id.mess_logo_other)
        ImageView messLogoOther;
        @BindView(R.id.mes_count_other)
        TextView mesCountOther;
        @BindView(R.id.dig_logo_other)
        ImageView digLogoOther;
        @BindView(R.id.dig_count_other)
        TextView digCountOther;
        @BindView(R.id.cv_item_list)
        CardView cvItemList;

        public Item3ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private void cycleViewPagerOn(final CycleViewPager cycleViewPager) {
        for (int i = 0; i < bannerlist.length; i++) {
            ADInfo info = new ADInfo();
            info.setUrl(bannerlist[i]);
            info.setContent("图片-->" + i);
            infos.add(info);
        }

        // 将最后一个ImageView添加进来
        views.add(ViewFactory.getImageView(context, infos.get(infos.size() - 1).getUrl()));
        for (int i = 0; i < infos.size(); i++) {
            views.add(ViewFactory.getImageView(context, infos.get(i).getUrl()));
        }
        // 将第一个ImageView添加进来
        views.add(ViewFactory.getImageView(context, infos.get(0).getUrl()));

        // 设置循环，在调用setData方法前调用
        cycleViewPager.setCycle(true);

        // 在加载数据前设置是否循环
        cycleViewPager.setData(views, infos, new CycleViewPager.ImageCycleViewListener() {

            @Override
            public void onImageClick(ADInfo info, int position, View imageView) {
                if (cycleViewPager.isCycle()) {
                    position = position - 1;
                    Toast.makeText(context,
                            "position-->" + info.getContent(), Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });
        //设置轮播
        cycleViewPager.setWheel(true);
        // 设置轮播时间，默认5000ms
        cycleViewPager.setTime(3000);
        //设置圆点指示图标组居中显示，默认靠右
        cycleViewPager.setIndicatorCenter();
    }
}