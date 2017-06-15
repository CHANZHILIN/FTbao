package com.example.jinbiao.ftbao.fragment;

import android.app.FragmentManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.jinbiao.ftbao.R;
import com.example.jinbiao.ftbao.adapter.DetailsAdpater;
import com.example.jinbiao.ftbao.adapter.ReCycleViewAdpater;
import com.example.jinbiao.ftbao.base.BaseFragment;
import com.example.jinbiao.ftbao.bean.Tshirt;
import com.example.jinbiao.ftbao.bean.TshirtData;
import com.example.jinbiao.ftbao.interFace.ITshirt;
import com.example.jinbiao.ftbao.utils.Constant;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class DetailsPageFragment extends BaseFragment {


private static final String TAG = "DetailPageFragment";
    private FragmentManager fragmentManager;
//    private RecyclerView.LayoutManager mLayoutManager;
    private LinearLayoutManager linearLayoutManager;
    private DetailsAdpater mAdapter;
    @BindView(R.id.details_swiperefreshlayout)
    SwipeRefreshLayout swiperefreshlayout;
    @BindView(R.id.mydetails_recycler_view)
    RecyclerView recyclerView;
    List<List<Tshirt>> lists=new ArrayList<>();
    private Handler handler =new Handler(Looper.getMainLooper());
    private static int currentpage=1;
    private int lastVisibleItem;
    //如果当前是刷新状态就设置为false
    private static boolean refreshing=true;
    private String[] bannerlist = {
            "http://img.taodiantong.cn/v55183/infoimg/2013-07/130720115322ky.jpg",
            "http://pic30.nipic.com/20130626/8174275_085522448172_2.jpg",
            "http://pic18.nipic.com/20111215/577405_080531548148_2.jpg",
            "http://pic15.nipic.com/20110722/2912365_092519919000_2.jpg",
            "http://pic.58pic.com/58pic/12/64/27/55U58PICrdX.jpg"};

    @Override
    public DetailsPageFragment newInstance(){
        return new DetailsPageFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_detail,null);
        //先绑定ButterKnife 要不然不能用
        ButterKnife.bind(this,view);
        initView(view);
        getData();
        initAdapter();
        return view;
    }

    private void getData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ITshirt tshirts = retrofit.create(ITshirt.class);
        Call<TshirtData> call = tshirts.getTshirts(Constant.PAGE_SIZE,currentpage);
        call.enqueue(new Callback<TshirtData>(){
            @Override
            public void onResponse(Call<TshirtData> call, Response<TshirtData> response)
            {
                if(getTshirtList(response.body().getData()).size()>0){
                    mAdapter.setTshirts(getTshirtList(response.body().getData()));
                    mAdapter.notifyDataSetChanged();
                    currentpage++;
                    refreshing=true;
                }else
                    Toast.makeText(getContext(),"暂无新的数据",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<TshirtData> call, Throwable t)
            {
                Log.e(TAG, "normalGet:" + t.getMessage()+ "");
            }
        });
    }

    private List<List<Tshirt>> getTshirtList(List<Tshirt> tshirtlist){
        List<List<Tshirt>> lists=new ArrayList<List<Tshirt>>();
        List<Tshirt> list =new ArrayList<Tshirt>();
        for (int i = 0; i <tshirtlist.size() ; i++) {
            Tshirt tshirt =tshirtlist.get(i);
            list.add(tshirt);
            if((i+1)%2==0){
                lists.add(list);
                list =new ArrayList<Tshirt>();
            }
        }
        return lists;
    }

    @Override
    protected void initView(final View view) {
        swiperefreshlayout.setOnRefreshListener(new MyRefreshListener());
        recyclerView.setOnScrollListener(new MyScrollListener());
    }


    private void initAdapter() {
        //创建默认的线性LayoutManager
//        mLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        recyclerView.setHasFixedSize(true);
        fragmentManager=getActivity().getFragmentManager();
        //创建并设置Adapter
        mAdapter = new DetailsAdpater(getActivity(),fragmentManager,lists,bannerlist);
        recyclerView.setAdapter(mAdapter);
    }

    class MyScrollListener extends RecyclerView.OnScrollListener {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            lastVisibleItem=linearLayoutManager.findLastVisibleItemPosition();
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            //当不是在刷新状态、滑动停止（SCROLL_STATE_IDLE）和是最后一个Item的时候，才能上拉刷新
            if (refreshing&&newState ==RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 ==mAdapter.getItemCount()) {
                swiperefreshlayout.setRefreshing(true);
                refreshing=false;
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getData();
                        swiperefreshlayout.setRefreshing(false);
                    }
                },2000);
            }
        }
    }
    class MyRefreshListener implements SwipeRefreshLayout.OnRefreshListener{

        @Override
        public void onRefresh() {
           /*
            getData();*/
            swiperefreshlayout.setRefreshing(false);
        }
    }

}
