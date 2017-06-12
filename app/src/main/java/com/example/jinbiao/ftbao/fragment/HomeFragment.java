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

import com.example.jinbiao.ftbao.R;
import com.example.jinbiao.ftbao.adapter.ReCycleViewAdpater;
import com.example.jinbiao.ftbao.base.BaseFragment;
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


public class HomeFragment extends BaseFragment {


private static final String TAG = "HomeFragment";
    private FragmentManager fragmentManager;
    private RecyclerView.LayoutManager mLayoutManager;
    private ReCycleViewAdpater mAdapter;
    @BindView(R.id.my_recycler_view)
    RecyclerView recyclerView;
    private String[] bannerlist = {"http://img.taodiantong.cn/v55183/infoimg/2013-07/130720115322ky.jpg",
            "http://pic30.nipic.com/20130626/8174275_085522448172_2.jpg",
            "http://pic18.nipic.com/20111215/577405_080531548148_2.jpg",
            "http://pic15.nipic.com/20110722/2912365_092519919000_2.jpg",
            "http://pic.58pic.com/58pic/12/64/27/55U58PICrdX.jpg"};
    private List<TshirtData.Tshirt> tshirtlist;
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
        getData();
        initAdapter();
        return view;
    }

    private void getData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final ITshirt tshirts = retrofit.create(ITshirt.class);
        Call<TshirtData> call = tshirts.getTshirts(10,1);
        call.enqueue(new Callback<TshirtData>()
        {
            @Override
            public void onResponse(Call<TshirtData> call, Response<TshirtData> response)
            {
                Log.d(TAG, "normalGet:"+response.body().getData()+"");
                tshirtlist=response.body().getData();
                List<List<TshirtData.Tshirt>> lists=new ArrayList<List<TshirtData.Tshirt>>();
                List<TshirtData.Tshirt> list =new ArrayList<TshirtData.Tshirt>();
                for (int i = 0; i <tshirtlist.size() ; i++) {
                    TshirtData.Tshirt tshirt =tshirtlist.get(i);
                    list.add(tshirt);
                   if((i+1)%2==0){
                       lists.add(list);
                       list =new ArrayList<TshirtData.Tshirt>();
                   }
                }
                //创建并设置Adapter
                mAdapter = new ReCycleViewAdpater(getActivity(),fragmentManager,lists,bannerlist);
                recyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<TshirtData> call, Throwable t)
            {
                Log.e(TAG, "normalGet:" + t.getMessage()+ "");
            }
        });
    }

    @Override
    protected void initView(View view) {

    }

    private void initAdapter() {
        //创建默认的线性LayoutManager
//        mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        recyclerView.setHasFixedSize(true);
        fragmentManager=getActivity().getFragmentManager();
    }
}
