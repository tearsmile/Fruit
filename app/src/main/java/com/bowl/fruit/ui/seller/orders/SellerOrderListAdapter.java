package com.bowl.fruit.ui.seller.orders;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bowl.fruit.R;
import com.bowl.fruit.network.entity.order.SellerOrder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CJ on 2018/2/19.
 */

public class SellerOrderListAdapter extends BaseAdapter {

    private Context context;
    private List<SellerOrder> data;

    public SellerOrderListAdapter(Context context){
        this.context = context;
        data = new ArrayList<>();
    }

    public void update(List<SellerOrder> orders){
        data.clear();
        data.addAll(orders);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if(convertView==null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_seller_order_list, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        SellerOrder order = data.get(i);
        viewHolder.mTime.setText("创建时间：" + "2018-10-12 10:33:23");
        viewHolder.mOrderId.setText("订单号：" + order.getOrderId());
        viewHolder.mDeliverId.setText("收货地址：" + order.getDeliverId());
        return convertView;
    }

    class ViewHolder{
        TextView mTime, mOrderId, mDeliverId;
        ViewHolder(View view){
            mTime = view.findViewById(R.id.tv_time);
            mOrderId = view.findViewById(R.id.tv_order_id);
            mDeliverId = view.findViewById(R.id.tv_deliver_id);
        }
    }
}
