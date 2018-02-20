package com.bowl.fruit.ui.buyer.shopping;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.bowl.fruit.R;
import com.bowl.fruit.ui.BaseActivity;
import com.bowl.fruit.ui.buyer.order.RecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cathy on 2018/2/13.
 */

public class ShoppingDetailActivity extends BaseActivity {

    private List<String> urls;
    private RecyclerView fruits;
    RecyclerAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_detail);
        initIntent();
        initViews();
    }

    private void initIntent(){
        Bundle bundle = getIntent().getExtras();
        ((TextView)findViewById(R.id.tv_total)).setText("￥"+bundle.getDouble("total"));
        ((TextView)findViewById(R.id.tv_discount)).setText("￥"+bundle.getDouble("discount"));
        ((TextView)findViewById(R.id.tv_pay)).setText("￥"+(bundle.getDouble("total") - bundle.getDouble("discount")));
        urls = bundle.getStringArrayList("urls");
    }

    private void initViews(){
        fruits = findViewById(R.id.rv_fruit);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        fruits.setLayoutManager(mLayoutManager);
        adapter = new RecyclerAdapter(this);
        fruits.setAdapter(adapter);

        adapter.update(new ArrayList<String>());
    }

}
