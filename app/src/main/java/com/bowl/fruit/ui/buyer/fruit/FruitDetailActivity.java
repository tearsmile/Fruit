package com.bowl.fruit.ui.buyer.fruit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bowl.fruit.R;
import com.bowl.fruit.network.entity.BaseResponse;
import com.bowl.fruit.network.entity.fruit.Fruit;
import com.bowl.fruit.repository.ShoppingRepository;
import com.bowl.fruit.ui.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by cathy on 2018/2/12.
 */

public class FruitDetailActivity extends BaseActivity {

    private ViewPager mViewPager;
    private PictureAdapter mAdapter;

    private TextView mNum;
    private TextView mDesc,mName,mPrice,mStandard,mWeight,mLife,mStore,mDetail;
    private ImageView mPic;
    private TextView mBuy;
    private RelativeLayout mShopping;

    private Fruit fruit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fruit_detail);
        initIntent();
        initViews();
        initTitle();
        initData();
    }

    private void initIntent(){
        fruit = (Fruit) getIntent().getSerializableExtra("fruit");
    }

    private void initTitle(){
        ImageView mBack = findViewById(R.id.backBtn);
        TextView mTitle = findViewById(R.id.title);
        TextView mRight= findViewById(R.id.rightBtn);

        mBack.setVisibility(View.VISIBLE);
        mRight.setVisibility(View.GONE);

        mTitle.setText("商品详情");
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initViews(){
        mViewPager = findViewById(R.id.vp_pic);
        mNum = findViewById(R.id.tv_num);

        mDesc = findViewById(R.id.tv_desc);
        mName = findViewById(R.id.tv_name);
        mPrice = findViewById(R.id.tv_price);
        mStandard = findViewById(R.id.tv_standard);
        mWeight = findViewById(R.id.tv_weight);
        mLife = findViewById(R.id.tv_life);
        mStore = findViewById(R.id.tv_store);
        mDetail = findViewById(R.id.tv_detail_desc);
        mPic = findViewById(R.id.iv_fruit_detail);

        mShopping = findViewById(R.id.rl_shopping);
        mBuy = findViewById(R.id.tv_add_shopping);

        mAdapter = new PictureAdapter(this);
        List<Integer> pics = new ArrayList<>();
        pics.add(R.drawable.fruit);
        pics.add(R.mipmap.logo);
        mAdapter.update(pics);

        mViewPager.setAdapter(mAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mNum.setText((position+1)+"/"+mAdapter.getCount());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        mBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShoppingRepository.instance().addShopping(fruit)
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
                                    Toast.makeText(FruitDetailActivity.this,"加入购物车成功",Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(FruitDetailActivity.this,baseResponse.getMsg(),Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });
    }

    private void initData(){
        mDesc.setText(fruit.getDesc());
        mName.setText(fruit.getName());
        mPrice.setText("￥" + fruit.getPrice());
        mStandard.setText("规格："+fruit.getStandard());
        mWeight.setText("重量："+fruit.getWeight());
        mLife.setText("保质期："+fruit.getLife());
        mStore.setText("贮存方法："+ fruit.getStore());
        mDetail.setText(fruit.getDetailDesc());
    }
}
