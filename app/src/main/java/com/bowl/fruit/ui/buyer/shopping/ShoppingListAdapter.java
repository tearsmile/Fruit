package com.bowl.fruit.ui.buyer.shopping;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bowl.fruit.fruit.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cathy on 2018/2/13.
 */

public class ShoppingListAdapter extends BaseAdapter {
    private Context mContext;
    private List<ShoppingItem> mData;
    int selectSize = 0;

    public ShoppingListAdapter(Context context){
        mContext = context;
        mData = new ArrayList<>();
    }

    public void update(List<ShoppingItem> fruits){
        mData.clear();
        mData.addAll(fruits);
        notifyDataSetChanged();
    }

    public void add(List<ShoppingItem> fruits){
        mData.addAll(fruits);
        notifyDataSetChanged();
    }

    public void selectAll(){
        for (ShoppingItem item : mData) {
            item.setSelect(true);
        }
        selectSize = mData.size();
        notifyDataSetChanged();
    }

    public void unSelectAll(){
        for (ShoppingItem item : mData) {
            item.setSelect(false);
        }
        selectSize = 0;
        notifyDataSetChanged();
    }

    public boolean isSelectAll(){
        return selectSize == mData.size();
    }

    public List<ShoppingItem> getData(){
        return mData;
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
        final ViewHolder viewHolder;
        if(convertView==null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_shopping_list, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final ShoppingItem fruit = mData.get(position);
        viewHolder.mName.setText(fruit.getName());
        viewHolder.mDesc.setText(fruit.getDesc());
        viewHolder.mPrice.setText("￥"+fruit.getPrice());
        viewHolder.mNum.setText("x"+fruit.getNum());

        if(fruit.isSelect()){
            viewHolder.mSelect.setImageResource(R.drawable.icon_selected);
        }else {
            viewHolder.mSelect.setImageResource(R.drawable.icon_select);
        }

        viewHolder.mSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(fruit.isSelect()){
                    selectSize--;
                    fruit.setSelect(false);
                    viewHolder.mSelect.setImageResource(R.drawable.icon_select);
                }else {
                    selectSize++;
                    fruit.setSelect(true);
                    viewHolder.mSelect.setImageResource(R.drawable.icon_selected);
                }
            }
        });

        return convertView;
    }

    private class ViewHolder{
        ImageView mPic, mSelect;
        TextView mName, mPrice, mDesc, mNum;

        ViewHolder(View view){
            mSelect = view.findViewById(R.id.iv_select);
            mPic = view.findViewById(R.id.iv_fruit);
            mName = view.findViewById(R.id.tv_name);
            mPrice = view.findViewById(R.id.tv_price);
            mDesc = view.findViewById(R.id.tv_desc);
            mNum = view.findViewById(R.id.tv_num);
        }
    }
}
