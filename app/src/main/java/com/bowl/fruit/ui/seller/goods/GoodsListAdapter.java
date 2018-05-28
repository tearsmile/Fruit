package com.bowl.fruit.ui.seller.goods;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bowl.fruit.R;
import com.bowl.fruit.network.FruitNetService;
import com.bowl.fruit.network.entity.fruit.Fruit;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CJ on 2018/2/14.
 */

public class GoodsListAdapter extends BaseAdapter {

    private Context mContext;
    private List<Fruit> mData;

    public GoodsListAdapter(Context context){
        mContext = context;
        mData = new ArrayList<>();
    }

    public void update(List<Fruit> fruits){
        mData.clear();
        mData.addAll(fruits);
        notifyDataSetChanged();
    }

    public void add(List<Fruit> fruits){
//        mData.clear();
        mData.addAll(fruits);
        notifyDataSetChanged();
    }

    public void remove(int position){
        mData.remove(position);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Fruit getItem(int position) {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_goods_list, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Fruit fruit = mData.get(position);
        viewHolder.mName.setText(fruit.getName());
        viewHolder.mDesc.setText(fruit.getDesc());
        viewHolder.mPrice.setText("￥"+fruit.getPrice());
        viewHolder.mStock.setText("库存：x"+fruit.getStock());
        viewHolder.mOriginPrice.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG);
        if(fruit.getDiscount() <= 0.1){
            viewHolder.mOriginPrice.setVisibility(View.GONE);
        } else {
            viewHolder.mOriginPrice.setVisibility(View.VISIBLE);
            viewHolder.mOriginPrice.setText("原价:￥" + (fruit.getPrice()+ fruit.getDiscount()));
        }
        ImageLoader.getInstance().displayImage(FruitNetService.BASE_URL+fruit.getPic().get(0),viewHolder.mPic);
        return convertView;
    }

    private class ViewHolder {
        ImageView mPic;
        TextView mName, mPrice, mDesc, mStock, mOriginPrice;

        ViewHolder(View view) {
            mPic = view.findViewById(R.id.iv_fruit);
            mName = view.findViewById(R.id.tv_name);
            mPrice = view.findViewById(R.id.tv_price);
            mOriginPrice = view.findViewById(R.id.tv_price_origin);
            mDesc = view.findViewById(R.id.tv_desc);
            mStock = view.findViewById(R.id.tv_stock);
        }
    }
}
