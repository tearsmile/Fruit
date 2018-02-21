package com.bowl.fruit.ui.seller.orders;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bowl.fruit.R;
import com.bowl.fruit.network.FruitNetService;
import com.bowl.fruit.network.entity.BaseResponse;
import com.bowl.fruit.network.entity.order.Order;
import com.bowl.fruit.ui.BaseActivity;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by CJ on 2018/2/19.
 */

public class SellerOrderDetailActivity extends BaseActivity {

    private ListView mListView;
    private DetailListAdapter mAdapter;
    private TextView mTime, mOrderId, mPerson, mAddress, mDeliver;
    private EditText mEditDeliver;

    private Order order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        order = (Order) getIntent().getSerializableExtra("order");
        initTitle();
        initViews();
    }

    private void initTitle(){
        ImageView mBack = findViewById(R.id.backBtn);
        TextView mTitle = findViewById(R.id.title);
        TextView mRight= findViewById(R.id.rightBtn);

        mBack.setVisibility(View.GONE);
        mRight.setVisibility(View.VISIBLE);

        mTitle.setText("订单详情");
        mRight.setText("发货");
        mRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mEditDeliver.getText().toString().equals("")){
                    Toast.makeText(SellerOrderDetailActivity.this,"请输入快递单号",Toast.LENGTH_LONG).show();
                } else {
                    FruitNetService.getInstance().getFruitApi().changeOrderStatus(order.getOrderId(),1,mEditDeliver.getText().toString())
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new BaseSafeSubscriber<BaseResponse>(){
                                @Override
                                public void onCompleted() {
                                    super.onCompleted();
                                }

                                @Override
                                public void onError(Throwable throwable) {
                                    super.onError(throwable);
                                }

                                @Override
                                public void onNext(BaseResponse baseResponse) {
                                    super.onNext(baseResponse);
                                    if(baseResponse.getCode() == 0){
                                        Toast.makeText(SellerOrderDetailActivity.this,"发货成功",Toast.LENGTH_LONG).show();
                                        findViewById(R.id.rightBtn).setVisibility(View.GONE);
                                        mEditDeliver.setVisibility(View.GONE);
                                        mDeliver.setText("快递单号："+mEditDeliver.getText().toString());
                                    }
                                }
                            });
                }
            }
        });
    }

    private void initViews(){
        mTime = findViewById(R.id.tv_time);
        mOrderId = findViewById(R.id.tv_order_id);
        mPerson = findViewById(R.id.tv_person);
        mAddress = findViewById(R.id.tv_address);
        mDeliver = findViewById(R.id.tv_deliver);
        mEditDeliver = findViewById(R.id.et_deliver);
        mListView = findViewById(R.id.lv_detail);

        mPerson.setText("收货人："+order.getAddress().getName() + "  " + order.getAddress().getPhone());
        mOrderId.setText("订单号："+order.getOrderId());
        mAddress.setText("收货地址："+order.getAddress().getCity()+order.getAddress().getAddress());

        if(order.getStatus() > 0){
            mEditDeliver.setVisibility(View.GONE);
            mDeliver.setText("快递单号："+order.getDeliverId());
        } else {
            mDeliver.setText("快递单号：");
            mEditDeliver.setVisibility(View.VISIBLE);
        }
        mAdapter = new DetailListAdapter(this);
        mListView.setAdapter(mAdapter);
        mAdapter.update(order.getGoods());
    }
}
