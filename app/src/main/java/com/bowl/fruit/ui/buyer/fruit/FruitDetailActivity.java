package com.bowl.fruit.ui.buyer.fruit;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.bowl.fruit.fruit.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cathy on 2018/2/12.
 */

public class FruitDetailActivity extends Activity {

    private ViewPager mViewPager;
    private PictureAdapter mAdapter;

    private TextView mNum;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fruit_detail);
        initViews();
    }

    private void initViews(){
        mViewPager = findViewById(R.id.vp_pic);
        mNum = findViewById(R.id.tv_num);
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
    }
}
