package com.example.jinbiao.ftbao.fragment;

import android.app.FragmentManager;
import android.database.sqlite.SQLiteDatabase;
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
import com.example.jinbiao.ftbao.adapter.ReCycleViewAdpater;
import com.example.jinbiao.ftbao.base.BaseFragment;
import com.example.jinbiao.ftbao.bean.Tshirt;
import com.example.jinbiao.ftbao.bean.TshirtData;
import com.example.jinbiao.ftbao.interFace.ITshirt;
import com.example.jinbiao.ftbao.utils.Constant;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class HomeFragment extends BaseFragment {


private static final String TAG = "HomeFragment";
    private FragmentManager fragmentManager_banner;
    private android.support.v4.app.FragmentManager fragmentManager;
//    private RecyclerView.LayoutManager mLayoutManager;
    private LinearLayoutManager linearLayoutManager;
    private ReCycleViewAdpater mAdapter;
    @BindView(R.id.demo_swiperefreshlayout)
    SwipeRefreshLayout swiperefreshlayout;
    @BindView(R.id.my_recycler_view)
    RecyclerView recyclerView;
    List<List<Tshirt>> lists=new ArrayList<>();
    private Handler handler =new Handler(Looper.getMainLooper());
    private static int currentpage=1;
    private static int currentpageNDB=1;
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
    public HomeFragment newInstance(){
        return new HomeFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
        currentpageNDB=1;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_home,null);
        //先绑定ButterKnife 要不然不能用
        ButterKnife.bind(this,view);
        initView(view);
        getData();
        initAdapter();
        SQLiteDatabase db = Connector.getDatabase();
        return view;
    }

    private void getData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final ITshirt tshirts = retrofit.create(ITshirt.class);
        Call<TshirtData> call = tshirts.getTshirts(Constant.PAGE_SIZE,currentpage);
        call.enqueue(new Callback<TshirtData>(){
            @Override
            public void onResponse(Call<TshirtData> call, Response<TshirtData> response)
            {
                if(response.body().getData().size()>0){
                    //将数据保存到sqlite,并且从网络上拿数据
                    mAdapter.setTshirts(getTshirtList(saveData(response.body().getData())));
                    currentpage++;
                    refreshing=true;
                }else
                    Toast.makeText(getContext(),"暂无新的数据",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<TshirtData> call, Throwable t)
            {
                Log.e(TAG, "normalGet:" + t.getMessage()+ "");
                getDataFormDB();
                refreshing=true;
            }
        });
    }

    /**
     * 判断如果本地数据看已经存在该数据，就不会保存到本地
     * @param tshirtlist
     */
    private List<Tshirt> saveData(List<Tshirt> tshirtlist){
        for (int i = 0; i <tshirtlist.size() ; i++) {
            Tshirt tshirt =tshirtlist.get(i);
            if((DataSupport.where("details = ?", tshirt.getDetails()).find(Tshirt.class)).size()==0){
                tshirt.save();
            }
        }
        return tshirtlist;
    }

    /**
     * 从本地数据库拿数据
     */
    private void getDataFormDB(){
        mAdapter.setTshirts(getTshirtList(DataSupport.limit(Constant.PAGE_SIZE).offset(Constant.PAGE_SIZE * currentpageNDB - 1).find(Tshirt.class)));
        currentpageNDB++;
        mAdapter.notifyDataSetChanged();
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
        fragmentManager_banner=getActivity().getFragmentManager();
        fragmentManager=getActivity().getSupportFragmentManager();
        //创建并设置Adapter
        mAdapter = new ReCycleViewAdpater(getActivity(),fragmentManager_banner,fragmentManager,lists,bannerlist);
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
