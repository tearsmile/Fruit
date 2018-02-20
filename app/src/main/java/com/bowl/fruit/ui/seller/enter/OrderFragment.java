package com.bowl.fruit.ui.seller.enter;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bowl.fruit.R;
import com.bowl.fruit.network.entity.order.SellerOrder;
import com.bowl.fruit.ui.seller.orders.SellerOrderDetailActivity;
import com.bowl.fruit.ui.seller.orders.SellerOrderListAdapter;
import com.bowl.fruit.ui.widget.XListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CJ on 2018/2/14.
 */

public class OrderFragment extends Fragment {

    private XListView mOrderList;
    private SellerOrderListAdapter mAdapter;

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_order,null);
        initViews(view);
        return view;
    }

    private void initViews(View view){
        mOrder = view.findViewById(R.id.rl_order);
        mDeliver = view.findViewById(R.id.rl_deliver);
        mFinish = view.findViewById(R.id.rl_finish);

        mOrderText = view.findViewById(R.id.tv_order);
        mDeliverText = view.findViewById(R.id.tv_deliver);
        mFinishText = view.findViewById(R.id.tv_finish);

        mOrderLine = view.findViewById(R.id.line_order);
        mDeliverLine = view.findViewById(R.id.line_deliver);
        mFinishLine = view.findViewById(R.id.line_finish);

        mOrderList = view.findViewById(R.id.lv_order);
        mOrderList.setPullLoadEnable(false);
        mAdapter = new SellerOrderListAdapter(getActivity());
        mOrderList.setAdapter(mAdapter);

        SellerOrder order = new SellerOrder();
        order.setTimeStamp(11111L);
        order.setOrderId("12343545");
        order.setDeliverId("北京市海淀区西土城路10号");
        List<SellerOrder> orders = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            orders.add(order);
        }
        mAdapter.update(orders);

        mOrderList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), SellerOrderDetailActivity.class);
                startActivity(intent);
            }
        });
    }
}
