package com.bowl.fruit.ui.buyer.shopping;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bowl.fruit.R;
import com.bowl.fruit.network.entity.BaseResponse;
import com.bowl.fruit.network.entity.mine.Address;
import com.bowl.fruit.network.entity.order.Goods;
import com.bowl.fruit.repository.OrderRepository;
import com.bowl.fruit.ui.BaseActivity;
import com.bowl.fruit.ui.buyer.mine.AddressActivity;
import com.bowl.fruit.ui.buyer.order.RecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by cathy on 2018/2/13.
 */

public class ShoppingDetailActivity extends BaseActivity {

    private RecyclerView fruits;
    private RecyclerAdapter adapter;

    private RelativeLayout mAddress;
    private TextView mChoose, mName, mPhone, mAddressText;
    private TextView mSubmit;

    private List<Goods> goods;
    private double total, discount, pay;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_detail);
        initIntent();
        initTitle();
        initViews();
    }

    private void initIntent(){
        Bundle bundle = getIntent().getExtras();
        total = bundle.getDouble("total");
        discount = bundle.getDouble("discount");
        pay = total - discount;
        goods = (List<Goods>) bundle.getSerializable("goods");
        ((TextView)findViewById(R.id.tv_total)).setText("￥"+total);
        ((TextView)findViewById(R.id.tv_discount)).setText("￥"+discount);
        ((TextView)findViewById(R.id.tv_pay)).setText("￥"+pay);
        if(total - discount < 20){
            ((TextView)findViewById(R.id.tv_deliver)).setText("￥5");
            ((TextView)findViewById(R.id.tv_pay)).setText("￥"+(pay + 5));
            ((TextView)findViewById(R.id.tv_price)).setText("￥"+(pay + 5));
        } else {
            ((TextView)findViewById(R.id.tv_deliver)).setText("包邮");
            ((TextView)findViewById(R.id.tv_pay)).setText("￥"+pay);
            ((TextView)findViewById(R.id.tv_price)).setText("￥"+pay);
        }
    }

    private void initTitle(){
        ImageView mBack = findViewById(R.id.backBtn);
        TextView mTitle = findViewById(R.id.title);
        TextView mRight= findViewById(R.id.rightBtn);

        mBack.setVisibility(View.VISIBLE);
        mRight.setVisibility(View.GONE);

        mTitle.setText("立即下单");
        mRight.setText("添加");

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initViews(){
        mAddress = findViewById(R.id.rl_address);
        fruits = findViewById(R.id.rv_fruit);
        mChoose = findViewById(R.id.tv_choose_address);
        mName = findViewById(R.id.tv_name);
        mPhone = findViewById(R.id.tv_phone);
        mAddressText = findViewById(R.id.tv_address);
        mSubmit = findViewById(R.id.tv_submit);

        mChoose.setVisibility(View.VISIBLE);
        mName.setVisibility(View.GONE);
        mPhone.setVisibility(View.GONE);
        mAddressText.setVisibility(View.GONE);

        mAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShoppingDetailActivity.this, AddressActivity.class);
                intent.putExtra("choose", true);
                startActivityForResult(intent,1);
            }
        });

        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OrderRepository.instance().addOrder(goods,total,discount)
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
                                    Toast.makeText(ShoppingDetailActivity.this,"订单提交成功",Toast.LENGTH_LONG).show();
                                    finish();
                                } else {
                                    Toast.makeText(ShoppingDetailActivity.this,"订单提交失败",Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        fruits.setLayoutManager(mLayoutManager);
        adapter = new RecyclerAdapter(this);
        fruits.setAdapter(adapter);

        adapter.update(new ArrayList<String>());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data != null) {
            mChoose.setVisibility(View.GONE);
            mName.setVisibility(View.VISIBLE);
            mPhone.setVisibility(View.VISIBLE);
            mAddressText.setVisibility(View.VISIBLE);
            Address address = (Address) data.getSerializableExtra("address");
            mName.setText(address.getName());
            mPhone.setText(address.getPhone());
            mAddressText.setText(address.getCity() + address.getAddress());
        }
    }
}
