package com.bowl.fruit.ui.buyer.shopping;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.bowl.fruit.R;
import com.bowl.fruit.ui.BaseActivity;

/**
 * Created by cathy on 2018/2/13.
 */

public class ShoppingDetailActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_detail);
        initIntent();
    }

    private void initIntent(){
        Bundle bundle = getIntent().getExtras();
        ((TextView)findViewById(R.id.tv_total)).setText("￥"+bundle.getDouble("total"));
        ((TextView)findViewById(R.id.tv_discount)).setText("￥"+bundle.getDouble("discount"));
        ((TextView)findViewById(R.id.tv_pay)).setText("￥"+(bundle.getDouble("total") - bundle.getDouble("discount")));
    }
}
