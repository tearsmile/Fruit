package com.bowl.fruit.ui.buyer.order;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.bowl.fruit.fruit.R;
import com.bowl.fruit.network.entity.fruit.Fruit;
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
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);
        initViews();
    }

    private void initViews(){
        mOrderList = findViewById(R.id.lv_order);
        mAdapter = new OrderListAdapter(this);
        mOrderList.setAdapter(mAdapter);

        List<Fruit> fruits = new ArrayList<>();
        Fruit f = new Fruit("智利蓝莓125g*1盒", "", 12.9, "这么好的蓝莓 都想留给你吃");
        for (int i = 0; i < 5; i++) {
            fruits.add(f);
        }

        mAdapter.update(fruits);
    }
}
