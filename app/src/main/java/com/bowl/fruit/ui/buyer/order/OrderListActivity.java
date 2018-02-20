package com.bowl.fruit.ui.buyer.order;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bowl.fruit.R;
import com.bowl.fruit.network.entity.order.Order;
import com.bowl.fruit.repository.OrderRepository;
import com.bowl.fruit.ui.BaseActivity;
import com.bowl.fruit.ui.widget.XListView;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by cathy on 2018/2/12.
 */

public class OrderListActivity extends BaseActivity {

    private XListView mListView;
    private OrderListAdapter mAdapter;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);
        initIntent();
        initViews();
    }

    private void initIntent(){
        type = getIntent().getIntExtra("type",0);
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

        mListView = findViewById(R.id.lv_order);
        mAdapter = new OrderListAdapter(this);
        mListView.setAdapter(mAdapter);
        resetListViewState();

        mListView.setXListViewListener(new XListView.IXListViewListener() {
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
    }

    private void resetListViewState() {
        mListView.stopRefresh();
        mListView.stopLoadMore();
        mListView.setAutoLoadEnable(false);
        mListView.setPullRefreshEnable(true);
        if(!hasNext){
            mListView.setPullLoadEnable(false);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        refresh(type);
    }

    private void refresh(int type){
        page = 1;
        OrderRepository.instance().getOrderList(type, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSafeSubscriber<List<Order>>(){
                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                        resetListViewState();
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        super.onError(throwable);
                        resetListViewState();
                    }

                    @Override
                    public void onNext(List<Order> orders) {
                        super.onNext(orders);
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

    private void loadMore(){
        page++;
        OrderRepository.instance().getOrderList(type, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSafeSubscriber<List<Order>>(){
                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                        resetListViewState();
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        super.onError(throwable);
                        resetListViewState();
                    }

                    @Override
                    public void onNext(List<Order> orders) {
                        super.onNext(orders);
                        if(orders == null){
                            return;
                        }else {
                            if(orders.size() < 10){
                                hasNext = false;
                            }
                            mAdapter.add(orders);
                        }
                    }
                });
    }
}
