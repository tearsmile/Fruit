package com.bowl.fruit.ui.buyer.shopping;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bowl.fruit.R;
import com.bowl.fruit.network.FruitNetService;
import com.bowl.fruit.network.entity.shopping.Shopping;
import com.bowl.fruit.util.FormatUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cathy on 2018/2/13.
 */

public class ShoppingListAdapter extends BaseAdapter {
    private Context mContext;
    private List<Shopping> mData;
    private int selectSize = 0;
    private double price = 0;
    private SelectChangeListener mListener;

    public interface SelectChangeListener{
        void onSelectChange();
    }

    public void setSelectChangeListener(SelectChangeListener listener){
        mListener = listener;
    }

    public ShoppingListAdapter(Context context){
        mContext = context;
        mData = new ArrayList<>();
    }

    public void update(List<Shopping> fruits){
        mData.clear();
        mData.addAll(fruits);
        notifyDataSetChanged();
    }

    public void add(List<Shopping> fruits){
        mData.addAll(fruits);
        notifyDataSetChanged();
    }

    public void remove(int position){
        mData.remove(position);
        notifyDataSetChanged();
    }

    public void selectAll(){
        price = 0;
        for (Shopping item : mData) {
            item.setSelect(true);
            price += item.getPrice()*item.getNum();
        }
        selectSize = mData.size();
        notifyDataSetChanged();
    }

    public void unSelectAll(){
        for (Shopping item : mData) {
            item.setSelect(false);
        }
        selectSize = 0;
        price = 0;
        notifyDataSetChanged();
    }

    public boolean isSelectAll(){
        return selectSize == mData.size();
    }

    public List<Shopping> getData(){
        return mData;
    }

    public double getPrice(){
        return Double.parseDouble(FormatUtil.formatDouble(price));
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Shopping getItem(int position) {
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

        final Shopping fruit = mData.get(position);
        viewHolder.mName.setText(fruit.getName());
        viewHolder.mDesc.setText(fruit.getDesc());
        viewHolder.mPrice.setText("￥"+fruit.getPrice());
        viewHolder.mNum.setText("x"+fruit.getNum());
        ImageLoader.getInstance().displayImage(FruitNetService.BASE_URL+fruit.getPic(),viewHolder.mPic);

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
                    price -= fruit.getPrice()*fruit.getNum();
                    fruit.setSelect(false);
                    viewHolder.mSelect.setImageResource(R.drawable.icon_select);
                }else {
                    selectSize++;
                    price += fruit.getPrice()*fruit.getNum();
                    fruit.setSelect(true);
                    viewHolder.mSelect.setImageResource(R.drawable.icon_selected);
                }
                if(mListener != null){
                    mListener.onSelectChange();
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
