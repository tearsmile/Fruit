package com.bowl.fruit.ui.buyer.order;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.bowl.fruit.fruit.R;
import com.bowl.fruit.network.entity.Fruit;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cathy on 2018/2/12.
 */

public class OrderListAdapter extends BaseAdapter {
    private Context mContext;
    private List<Fruit> mData;

    public OrderListAdapter(Context context){
        mContext = context;
        mData = new ArrayList<>();
    }

    public void update(List<Fruit> fruits){
        mData.clear();
        mData.addAll(fruits);
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_order_list, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        viewHolder.mRecycler.setLayoutManager(mLayoutManager);
        RecyclerAdapter adapter = new RecyclerAdapter(mContext);
        viewHolder.mRecycler.setAdapter(adapter);
        adapter.update(new ArrayList<String>());
        return convertView;
    }

    class ViewHolder{
        RecyclerView mRecycler;
        ViewHolder(View view){
            mRecycler = view.findViewById(R.id.rv_fruit);
        }
    }
}
