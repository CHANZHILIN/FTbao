package com.example.jinbiao.ftbao.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jinbiao.ftbao.R;

/**
 * Created by Zeng on 2017/6/12.
 */

public class DiscoveryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    String []images;
    private LayoutInflater mLayoutInflater;
    private Context mContext;

    public DiscoveryAdapter(Context context,String []images){
        this.images = images;
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = mLayoutInflater.inflate(R.layout.discovery_item,parent,false);
        DisViewHolder viewHolder = new DisViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    class DisViewHolder extends RecyclerView.ViewHolder{
        public DisViewHolder(View viewItem){
            super(viewItem);

        }

    }
}
