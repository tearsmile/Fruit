package com.bowl.fruit.ui.buyer.order;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.bowl.fruit.R;
import com.bowl.fruit.network.FruitNetService;
import com.bowl.fruit.network.entity.BaseResponse;
import com.bowl.fruit.network.entity.order.Goods;
import com.bowl.fruit.network.entity.order.Order;
import com.bowl.fruit.preference.PreferenceDao;
import com.bowl.fruit.util.FormatUtil;
import com.bowl.fruit.util.TimeUtil;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by cathy on 2018/2/12.
 */

public class OrderListAdapter extends BaseAdapter {
    private Context mContext;
    private List<Order> mData;

    public OrderListAdapter(Context context){
        mContext = context;
        mData = new ArrayList<>();
    }

    public void update(List<Order> orders){
        mData.clear();
        mData.addAll(orders);
        notifyDataSetChanged();
    }

    public void add(List<Order> orders){
        mData.addAll(orders);
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

        final Order order = mData.get(position);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        viewHolder.mRecycler.setLayoutManager(mLayoutManager);
        RecyclerAdapter adapter = new RecyclerAdapter(mContext);
        viewHolder.mRecycler.setAdapter(adapter);
        List<String> urls = new ArrayList<>();
        for (Goods goods : order.getGoods()) {
            urls.add(goods.getPic());
        }
        adapter.update(urls);

        if(order.getStatus() == 0){
            viewHolder.mHandle.setText("取消");
            viewHolder.mHandle.setTextColor(mContext.getResources().getColor(R.color.lightMainColor));
            viewHolder.mHandle.setBackgroundResource(R.drawable.bg_text_btn);
        } else if (order.getStatus() == 1){
            viewHolder.mHandle.setText("收货");
            viewHolder.mHandle.setTextColor(mContext.getResources().getColor(R.color.lightMainColor));
            viewHolder.mHandle.setBackgroundResource(R.drawable.bg_text_btn);
        } else if(order.getStatus() == 2){
            viewHolder.mHandle.setText("已完成");
            viewHolder.mHandle.setTextColor(mContext.getResources().getColor(R.color.halfBlack));
            viewHolder.mHandle.setBackground(null);
        }

        viewHolder.mHandle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int status = order.getStatus();
                switch (order.getStatus()){
                    case 0:
                        status = -1;
                        break;
                    default:
                        status++;
                        break;
                }
                FruitNetService.getInstance().getFruitApi().changeOrderStatus(PreferenceDao.getInstance().getString("key_login_user_id",""),order.getOrderId(),status,"")
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<BaseResponse>(){
                            @Override
                            public void onCompleted() {
                            }

                            @Override
                            public void onError(Throwable throwable) {
                            }

                            @Override
                            public void onNext(BaseResponse baseResponse) {
                                if(baseResponse.getCode() == 0){
                                    Toast.makeText(mContext,"操作成功",Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });

        viewHolder.mOrderId.setText("订单号:" + order.getOrderId());
        if(order.getStatus() == 0) {
            viewHolder.mDeliverId.setText("快递单号:"+"暂未发货");
        } else {
            viewHolder.mDeliverId.setText("快递单号:"+order.getDeliverId());
        }
        viewHolder.mPrice.setText("实付:￥" + FormatUtil.formatDouble(order.getPrice()));
        viewHolder.mDiscount.setText("优惠￥" + order.getDiscount());
        viewHolder.mTime.setText(TimeUtil.format(order.getTimeStamp()));
        return convertView;
    }

    class ViewHolder{
        RecyclerView mRecycler;
        TextView mHandle, mTime, mOrderId, mDeliverId, mPrice, mDiscount;
        ViewHolder(View view){
            mRecycler = view.findViewById(R.id.rv_fruit);
            mHandle = view.findViewById(R.id.tv_handle);
            mTime = view.findViewById(R.id.tv_time);
            mOrderId = view.findViewById(R.id.tv_order_id);
            mDeliverId = view.findViewById(R.id.tv_deliver_id);
            mPrice = view.findViewById(R.id.tv_price);
            mDiscount = view.findViewById(R.id.tv_price_down);
        }
    }
}
