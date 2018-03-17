package com.bowl.fruit.ui.seller.orders;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bowl.fruit.R;
import com.bowl.fruit.network.entity.order.Order;
import com.bowl.fruit.util.TimeUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CJ on 2018/2/19.
 */

public class SellerOrderListAdapter extends BaseAdapter {

    private Context context;
    private List<Order> data;

    public SellerOrderListAdapter(Context context){
        this.context = context;
        data = new ArrayList<>();
    }

    public void update(List<Order> orders){
        data.clear();
        data.addAll(orders);
        notifyDataSetChanged();
    }

    public void add(List<Order> orders){
        data.addAll(orders);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Order getItem(int i) {
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
        Order order = data.get(i);
        viewHolder.mTime.setText("创建时间：" + TimeUtil.format(order.getTimeStamp()));
        viewHolder.mOrderId.setText("订单号：" + order.getOrderId());
        if(order.getAddress() != null) {
            String address = order.getAddress().getCity() + " " + order.getAddress().getAddress();
            viewHolder.mDeliverId.setText("收货地址：" + address);
        } else {
            viewHolder.mDeliverId.setText("收货地址：未知");
        }
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
