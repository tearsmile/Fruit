package com.bowl.fruit.ui.seller.orders;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bowl.fruit.R;
import com.bowl.fruit.network.entity.order.Goods;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CJ on 2018/2/19.
 */

public class DetailListAdapter extends BaseAdapter {

    private Context context;
    private List<Goods> mData;

    public DetailListAdapter(Context context){
        this.context = context;
        mData = new ArrayList<>();
    }

    public void update(List<Goods> goods){
        mData.clear();
        mData.addAll(goods);

        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int i) {
        return mData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if(convertView==null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_detail_list, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Goods fruit = mData.get(i);
        viewHolder.mDesc.setText(fruit.getName() + " x" + fruit.getNum());
        return convertView;
    }

    private class ViewHolder{
        TextView mDesc;

        ViewHolder(View view){
            mDesc = view.findViewById(R.id.tv_desc);
        }
    }
}
