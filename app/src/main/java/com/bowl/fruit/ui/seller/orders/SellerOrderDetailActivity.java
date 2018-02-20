package com.bowl.fruit.ui.seller.orders;

import android.os.Bundle;
import android.widget.ListView;

import com.bowl.fruit.R;
import com.bowl.fruit.network.entity.order.Goods;
import com.bowl.fruit.ui.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CJ on 2018/2/19.
 */

public class SellerOrderDetailActivity extends BaseActivity {

    private ListView mListView;
    private DetailListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        initViews();
    }

    private void initViews(){
        mListView = findViewById(R.id.lv_detail);
        mAdapter = new DetailListAdapter(this);
        mListView.setAdapter(mAdapter);
        Goods goods = new Goods();
        goods.setName("智利蓝莓");
        goods.setNum(2);

        List<Goods> goodsList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            goodsList.add(goods);
        }

        mAdapter.update(goodsList);
    }
}
