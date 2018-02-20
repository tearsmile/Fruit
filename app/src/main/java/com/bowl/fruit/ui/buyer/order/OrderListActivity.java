package com.bowl.fruit.ui.buyer.order;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bowl.fruit.R;
import com.bowl.fruit.network.entity.order.Order;
import com.bowl.fruit.ui.BaseActivity;
import com.bowl.fruit.ui.widget.XListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cathy on 2018/2/12.
 */

public class OrderListActivity extends BaseActivity {

    private XListView mOrderList;
    private OrderListAdapter mAdapter;

    private RelativeLayout mOrder, mDeliver, mFinish;
    private TextView mOrderText, mDeliverText, mFinishText;
    private View mOrderLine, mDeliverLine, mFinishLine;

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int cycleCondition = -1;
            if (v.getId() == R.id.rl_order){
//                cycleCondition = RankingConstants.CYCLE_WEEK;
                mOrderLine.setVisibility(View.VISIBLE);
                mDeliverLine.setVisibility(View.GONE);
                mFinishLine.setVisibility(View.GONE);

            } else if (v.getId() == R.id.rl_deliver){
//                cycleCondition = RankingConstants.CYCLE_MONTH;
                mOrderLine.setVisibility(View.GONE);
                mDeliverLine.setVisibility(View.VISIBLE);
                mFinishLine.setVisibility(View.GONE);
            } else if (v.getId() == R.id.rl_finish){
//                cycleCondition = RankingConstants.CYCLE_ALL;
                mOrderLine.setVisibility(View.GONE);
                mDeliverLine.setVisibility(View.GONE);
                mFinishLine.setVisibility(View.VISIBLE);
            }
//            if(cycleCondition == request.getCycle_cond()){
//                return;
//            }
//            request.setCycle_cond(cycleCondition);
//            refreshData(request);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);
        initViews();
    }

    private void initViews(){
        mOrder = findViewById(R.id.rl_order);
        mDeliver = findViewById(R.id.rl_deliver);
        mFinish = findViewById(R.id.rl_finish);

        mOrderText = findViewById(R.id.tv_order);
        mDeliverText = findViewById(R.id.tv_deliver);
        mFinishText = findViewById(R.id.tv_finish);

        mOrderLine = findViewById(R.id.line_order);
        mDeliverLine = findViewById(R.id.line_deliver);
        mFinishLine = findViewById(R.id.line_finish);

        mOrderList = findViewById(R.id.lv_order);
        mAdapter = new OrderListAdapter(this);
        mOrderList.setAdapter(mAdapter);

        mOrder.setOnClickListener(mOnClickListener);
        mDeliver.setOnClickListener(mOnClickListener);
        mFinish.setOnClickListener(mOnClickListener);

        List<Order> orders = new ArrayList<>();
        Order order = new Order();
        for (int i = 0; i < 5; i++) {
            orders.add(order);
        }

        mAdapter.update(orders);
    }
}
