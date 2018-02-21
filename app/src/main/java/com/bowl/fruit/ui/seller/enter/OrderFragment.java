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
import com.bowl.fruit.network.entity.order.Order;
import com.bowl.fruit.repository.OrderRepository;
import com.bowl.fruit.ui.seller.orders.SellerOrderDetailActivity;
import com.bowl.fruit.ui.seller.orders.SellerOrderListAdapter;
import com.bowl.fruit.ui.widget.XListView;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by CJ on 2018/2/14.
 */

public class OrderFragment extends Fragment {

    private XListView mOrderList;
    private SellerOrderListAdapter mAdapter;

    private RelativeLayout mOrder, mDeliver, mFinish;
    private TextView mOrderText, mDeliverText, mFinishText;
    private View mOrderLine, mDeliverLine, mFinishLine;

    private int page = 1;
    private int type = 0;
    private boolean hasNext = true;

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int cycleCondition = -1;
            if (v.getId() == R.id.rl_order){
                cycleCondition = 0;
                mOrderLine.setVisibility(View.VISIBLE);
                mDeliverLine.setVisibility(View.GONE);
                mFinishLine.setVisibility(View.GONE);

            } else if (v.getId() == R.id.rl_deliver){
                cycleCondition = 1;
                mOrderLine.setVisibility(View.GONE);
                mDeliverLine.setVisibility(View.VISIBLE);
                mFinishLine.setVisibility(View.GONE);
            } else if (v.getId() == R.id.rl_finish){
                cycleCondition = 2;
                mOrderLine.setVisibility(View.GONE);
                mDeliverLine.setVisibility(View.GONE);
                mFinishLine.setVisibility(View.VISIBLE);
            }
            if(cycleCondition == type){
                return;
            }
            type = cycleCondition;
//            request.setCycle_cond(cycleCondition);
            refresh(type);
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
        resetListViewState();

        mOrderList.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh() {
                refresh(type);
            }

            @Override
            public void onLoadMore() {
                loadMore();
            }
        });

        mOrder.setOnClickListener(mOnClickListener);
        mDeliver.setOnClickListener(mOnClickListener);
        mFinish.setOnClickListener(mOnClickListener);

        if(type == 0){
            mOrderLine.setVisibility(View.VISIBLE);
            mDeliverLine.setVisibility(View.GONE);
            mFinishLine.setVisibility(View.GONE);
        } else if (type == 1){
            mOrderLine.setVisibility(View.GONE);
            mDeliverLine.setVisibility(View.VISIBLE);
            mFinishLine.setVisibility(View.GONE);
        } else if (type == 2){
            mOrderLine.setVisibility(View.GONE);
            mDeliverLine.setVisibility(View.GONE);
            mFinishLine.setVisibility(View.VISIBLE);
        }

        mOrderList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), SellerOrderDetailActivity.class);
                Order order = mAdapter.getItem(i-1);
                intent.putExtra("order",order);
                startActivity(intent);
            }
        });
    }

    private void resetListViewState() {
        mOrderList.stopRefresh();
        mOrderList.stopLoadMore();
        mOrderList.setAutoLoadEnable(false);
        mOrderList.setPullRefreshEnable(true);
        if(!hasNext){
            mOrderList.setPullLoadEnable(false);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        refresh(type);
    }

    private void refresh(int type){
        page = 1;
        OrderRepository.instance().getOrderList(type, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Order>>(){
                    @Override
                    public void onCompleted() {
                        resetListViewState();
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        resetListViewState();
                    }

                    @Override
                    public void onNext(List<Order> orders) {
                        if(orders == null){
                            return;
                        }else {
                            if(orders.size() < 10){
                                hasNext = false;
                            }
                            mAdapter.update(orders);
                        }
                    }
                });
    }

    private void loadMore() {
        page++;
        OrderRepository.instance().getOrderList(type, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Order>>() {
                    @Override
                    public void onCompleted() {
                        resetListViewState();
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        resetListViewState();
                    }

                    @Override
                    public void onNext(List<Order> orders) {
                        if (orders == null) {
                            return;
                        } else {
                            if (orders.size() < 10) {
                                hasNext = false;
                            }
                            mAdapter.add(orders);
                        }
                    }
                });
    }
}
