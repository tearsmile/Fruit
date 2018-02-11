package com.bowl.fruit.ui.buyer.enter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bowl.fruit.fruit.R;
import com.bowl.fruit.network.entity.Fruit;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cathy on 2018/2/11.
 */

public class FruitListAdapter extends BaseAdapter {
    private Context mContext;
    private List<Fruit> mData;

    public FruitListAdapter(Context context){
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_fruit_list, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Fruit fruit = mData.get(position);
        viewHolder.mName.setText(fruit.getName());
        viewHolder.mDesc.setText(fruit.getDesc());
        viewHolder.mPrice.setText("￥"+fruit.getPrice());
        return convertView;
    }

    private class ViewHolder{
        ImageView mPic;
        TextView mName, mPrice, mDesc;

        ViewHolder(View view){
            mPic = view.findViewById(R.id.iv_fruit);
            mName = view.findViewById(R.id.tv_name);
            mPrice = view.findViewById(R.id.tv_price);
            mDesc = view.findViewById(R.id.tv_desc);
        }
    }
}
